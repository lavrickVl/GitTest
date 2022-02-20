package com.test.android.gittest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val urlAvatar: String,
    val url: String,
    val repos: String,
    val gists: String,
    val followers: String,
) : Parcelable
