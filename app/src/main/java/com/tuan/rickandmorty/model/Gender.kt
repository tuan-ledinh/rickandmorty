package com.tuan.rickandmorty.model

import androidx.annotation.StringRes
import com.tuan.rickandmorty.R

enum class Gender(@StringRes val translation: Int) {
    Female(R.string.gender_female),
    Male(R.string.gender_male),
    Genderless(R.string.gender_genderless),
    unknown(R.string.gender_unknown)
}