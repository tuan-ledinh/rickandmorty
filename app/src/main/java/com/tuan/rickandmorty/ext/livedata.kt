package com.tuan.rickandmorty.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.readOnly(): LiveData<T> = this