package com.test.android.gittest.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.android.gittest.domain.data.GitRepository
import com.test.android.gittest.domain.model.User
import com.test.android.gittest.domain.model.UserDetails
import com.test.android.gittest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: GitRepository): ViewModel() {

    lateinit var user: User

    val userDetails: SharedFlow<Resource<UserDetails>> = flow {
        if (::user.isInitialized) {
            emitAll(repo.getUserDetails(user))
        }
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed())
}