package com.jdcastro.jairovideogames.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideogameResult(
    val videogames: ArrayList<Videogame>
) : Parcelable
