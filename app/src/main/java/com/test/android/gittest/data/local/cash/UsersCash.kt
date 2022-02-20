package com.test.android.gittest.data.local.cash

import com.test.android.gittest.domain.model.User

object UsersCash {

    var list: List<User> = emptyList()

    fun isNotEmpty(): Boolean = list.isNotEmpty()

}