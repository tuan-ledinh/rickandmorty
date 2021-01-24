package com.tuan.rickandmorty.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PageInfo(
    val count: Long,
    val pages: Long,
    val next: String?,
    val prev: String?
) : Parcelable