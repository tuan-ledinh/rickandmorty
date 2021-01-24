package com.tuan.rickandmorty.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.tuan.rickandmorty.ext.readOnly
import com.tuan.rickandmorty.model.Character
import com.tuan.rickandmorty.network.Api

class CharacterListViewModel @ViewModelInject constructor(
    private val api: Api
) : ViewModel() {

    companion object {
        private const val START_PAGE = 1L
    }

    private val _loading = MutableLiveData<Boolean>(false)
    val loading = _loading.readOnly()

    private val _error = MutableLiveData<Throwable?>(null)
    val error = _error.readOnly()

    private val previousPage = MutableLiveData<Long>()

    private val canLoadMore = MutableLiveData(true)

    private val _characters = MutableLiveData<List<Character>>()
    val characters = _characters.readOnly()

    fun loadNextPage() {
        if (_loading.value == true || canLoadMore.value == false) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)

                val page = previousPage.value?.let { it + 1 } ?: START_PAGE
                val res = api.getAllCharacters(page)

                val list = mutableListOf<Character>()
                _characters.value?.let { list.addAll(it) }
                list.addAll(res.results)
                _characters.postValue(list)

                previousPage.postValue(page)
                canLoadMore.postValue(page < res.info.pages)

            } catch (error: Throwable) {
                canLoadMore.postValue(false)
                _error.postValue(error)
            } finally {
                _loading.postValue(false)
            }
        }
    }

    private var initialized = false

    fun initialize() {
        initialized = if (!initialized) true else return

        loadNextPage()
    }
}