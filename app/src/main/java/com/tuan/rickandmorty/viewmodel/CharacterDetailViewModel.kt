package com.tuan.rickandmorty.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuan.rickandmorty.ext.readOnly
import com.tuan.rickandmorty.model.Character

class CharacterDetailViewModel @ViewModelInject constructor() : ViewModel() {

    private val _character = MutableLiveData<Character>()
    val character = _character.readOnly()

    private var initialized = false

    fun initialize(character: Character) {
        initialized = if (!initialized) true else return

        _character.postValue(character)
    }
}