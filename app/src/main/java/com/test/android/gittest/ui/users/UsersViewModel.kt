package com.test.android.gittest.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.android.gittest.data.local.cash.UsersCash
import com.test.android.gittest.domain.data.GitRepository
import com.test.android.gittest.domain.model.User
import com.test.android.gittest.util.Constants
import com.test.android.gittest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repo: GitRepository) : ViewModel() {

    private val _users = MutableSharedFlow<List<User>>()
    val users = _users.asSharedFlow()

    val loading = MutableStateFlow(false)

    val eventFlow = MutableSharedFlow<String>()

    init {
        getUsers()
    }


    fun getUsers() = viewModelScope.launch(Dispatchers.IO) {
        repo.getUsers().collect {
            when (it) {
                is Resource.Loading -> loading.emit(true)

                is Resource.Success -> {
                    _users.emit(it.data ?: emptyList())
                    UsersCash.list = it.data ?: emptyList()
//                    eventFlow.emit(it)
                }

                is Resource.Error -> eventFlow.emit(it.message ?: Constants.UNKNOWN_ERROR_TAG)
            }
        }
    }

    fun refreshUsers() = viewModelScope.launch(Dispatchers.IO) {
        _users.emit(emptyList())
        getUsers()
    }


}