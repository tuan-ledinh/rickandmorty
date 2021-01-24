package com.tuan.rickandmorty.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PaginatedList<T : Parcelable>(
    val info: PageInfo,
    val results: List<T>
) : Parcelable