package dev.jolken.giphyer.models.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jolken.giphyer.models.giphy.Gif
import dev.jolken.giphyer.models.requests.SearchRequest
import dev.jolken.giphyer.models.requests.TrendingRequest
import dev.jolken.giphyer.utils.Event
import dev.jolken.giphyer.utils.SingleLiveEvent
import dev.jolken.giphyer.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel : BaseViewModel() {
    private var query = ""
    private var isTrending = true
    val gifsList = MutableLiveData<Event<MutableList<Gif>>>()
    val selectedGif = SingleLiveEvent<Gif>()
    val isLoadingGifs = MutableLiveData<Boolean>(false)
    val clickedGifPosition = MutableLiveData<Int>(0)


    fun loadGifs(inputQuery: String? = null) {
        if (inputQuery != null) {
            if (query != inputQuery) gifsList.value?.data?.clear()
            query = inputQuery
            isTrending = false
        }
        requestWithCallback({

            isLoadingGifs.postValue(true)

            if (isTrending) {
                api.getTrending(
                    TrendingRequest(
                        API_KEY,
                        25,
                        gifsList.value?.data?.size ?: 0
                    ).toMap()
                )
            } else {
                api.getSearch(
                    SearchRequest(
                        API_KEY,
                        query,
                        25,
                        gifsList.value?.data?.size ?: 0
                    ).toMap()
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
                        } ?: it.data?.data?.toMutableList() ?: mutableListOf<Gif>()
                        , null
                    )
                }
            }
            isLoadingGifs.postValue(false)
        }
    }

    fun loadFakeGifs() {
        isLoadingGifs.postValue(true)
        this.viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                delay(1000)

                gifsList.postValue(Event(
                        Status.SUCCESS,
                        gifsList.value?.data?.apply {
                            this.addAll((0..20).map { Gif.GetFakeGif() }) //добавляем в список гифки
                        } ?: (0..20).map { Gif.GetFakeGif() }.toMutableList(), null
                    )
                )

                isLoadingGifs.postValue(false)
            }
        }
    }

    fun setSelectedGif(gif: Gif) {
        this.selectedGif.postValue(gif)
    }

    fun setIsTrending(b: Boolean) {
        this.isTrending = b;
        if (isTrending) {
            gifsList.value?.data?.clear()
            loadGifs()
        }
    }
    fun setClickedGifPosition(position: Int) {
        this.clickedGifPosition.postValue(position)
    }


}