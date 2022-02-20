package com.test.android.gittest.util.interfaces

import android.widget.ImageView
import com.test.android.gittest.domain.model.User
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


interface INavigationListener {
    fun navigateToDetails(user: User, imageView: ImageView)
}