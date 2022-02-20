package com.test.android.gittest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserDetails(
    val name: String,
    val urlAvatar: String,
    val url: String,
    val repos: String,
    val gists: String,
    val followers: String,
)  
