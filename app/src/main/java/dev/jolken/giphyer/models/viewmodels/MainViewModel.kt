package dev.jolken.giphyer.models.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jolken.giphyer.models.giphy.Gif
import dev.jolken.giphyer.models.requests.SearchRequest
import dev.jolken.giphyer.models.requests.TrendingRequest
import dev.jolken.giphyer.utils.Event
import dev.jolken.giphyer.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel : BaseViewModel() {
    val gifsList = MutableLiveData<Event<MutableList<Gif>>>()
    val selectedGif = MutableLiveData<Gif>()
    val searchInputText = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val requestGetGifsOffset = MutableLiveData<Int>()

    fun loadGifs() {
        requestWithCallback({
            if (searchInputText.value != null && searchInputText.value?.length == 0) {
                api.getTrending(
                    TrendingRequest(
                        API_KEY,
                        20,
                        requestGetGifsOffset.value ?: 0
                    )
                )
            } else {
                api.getSearch(
                    SearchRequest(
                        API_KEY,
                        searchInputText.value ?: "",
                        20,
                        requestGetGifsOffset.value ?: 0
                    )
                )
            }
        })
        {
            when (it.status) {
                Status.ERROR -> {
                    gifsList.value = Event(it.status, gifsList.value?.data, it.error)
                }
                Status.SUCCESS -> {
                    gifsList.value = Event(
                        it.status,
                        gifsList.value?.data?.apply {
                        if (it.data?.data != null) this.addAll(it.data.data) //добавляем в список гифки
                    }, null)
                }
            }
        }
    }

    fun loadFakeGifs() {
        this.viewModelScope.launch(Dispatchers.IO){
            runBlocking {
                delay(1000)

                gifsList.postValue(Event(
                    Status.SUCCESS,
                    gifsList.value?.data?.apply {
                        this.addAll((0..20).map { Gif.GetFakeGif() } ) //добавляем в список гифки
                    } ?: (0..20).map { Gif.GetFakeGif() }.toMutableList()

                    , null))
            }
            }
        }


}