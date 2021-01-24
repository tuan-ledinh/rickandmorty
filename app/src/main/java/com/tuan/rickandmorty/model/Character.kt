package com.tuan.rickandmorty.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@JsonClass(generateAdapter = true)
data class Character(
    val id: Long,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: Date
) : Parcelable