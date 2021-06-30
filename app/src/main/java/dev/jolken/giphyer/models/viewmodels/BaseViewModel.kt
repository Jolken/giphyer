package dev.jolken.giphyer.models.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jolken.giphyer.models.responses.GiphyResponse
import dev.jolken.giphyer.network.GiphyApi
import dev.jolken.giphyer.network.NetworkService
import dev.jolken.giphyer.utils.Event
import dev.jolken.giphyer.utils.toKotlinError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    val API_KEY = "H1RfnF70GLlm0CL9RDi9RMM8K9sdvstY"

    var api: GiphyApi = NetworkService.retrofitService()

    // У нас будут две базовые функции requestWithLiveData и
    // requestWithCallback, в зависимости от ситуации мы будем
    // передавать в них лайвдату или колбек вместе с параметрами сетевого
    // запроса. Функция принимает в виде параметра ретрофитовский suspend запрос,
    // проверяет на наличие ошибок и сетит данные в виде ивента либо в
    // лайвдату либо в колбек. Про ивент будет написано ниже

    fun <T> requestWithLiveData(
        liveData: MutableLiveData<Event<T>>,
        request: suspend () -> Response<T>
    ) {

        // В начале запроса сразу отправляем ивент загрузки
        liveData.postValue(Event.loading())

        // Привязываемся к жизненному циклу ViewModel, используя viewModelScope.
        // После ее уничтожения все выполняющиеся длинные запросы
        // будут остановлены за ненадобностью.
        // Переходим в IO поток и стартуем запрос
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                if (response.isSuccessful) {
                    // Сетим в лайвдату командой postValue в IO потоке
                    liveData.postValue(Event.success(response.body()))
                } else {
                    liveData.postValue(Event.error(response.toKotlinError()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(Event.error(null))
            }
        }
    }
    fun <T> requestWithCallback(
        request: suspend () -> Response<T>,
        callback: (Event<T>) -> Unit) {

        callback(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                launch(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        callback(Event.success(response.body()))
                    } else {
                        callback(Event.error(response.toKotlinError()))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    callback(Event.error(null))
                }
            }
        }
    }

}
