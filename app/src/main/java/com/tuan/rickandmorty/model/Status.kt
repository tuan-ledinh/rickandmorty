package com.tuan.rickandmorty.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.tuan.rickandmorty.R

enum class Status(@StringRes val translation: Int, @ColorRes val color: Int) {
    Alive(R.string.status_alive, R.color.colorAlive),
    Dead(R.string.status_dead, R.color.colorDead),
    unknown(R.string.status_unknown, R.color.colorUnknown)
}