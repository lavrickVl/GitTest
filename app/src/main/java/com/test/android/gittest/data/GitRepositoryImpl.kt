package com.test.android.gittest.data

import com.test.android.gittest.data.api.ApiGit
import com.test.android.gittest.domain.data.GitRepository
import com.test.android.gittest.domain.model.User
import com.test.android.gittest.domain.model.UserDetails
import com.test.android.gittest.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.UnknownHostException

class GitRepositoryImpl(private val api: ApiGit): GitRepository {

    override suspend fun getUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())

        try {
            val users = api.getUsers().map {
                User(
                    name =  it.login,
                    urlAvatar =  it.avatar_url,
                    url =  it.html_url,
                    repos =  it.repos_url,
                    gists =  it.gists_url,
                    followers =  it.followers_url
                )
            }

            emit(Resource.Success(users))



        } catch (ex: HttpException) {
            emit(Resource.Error(data = emptyList<User>(), message = ex.message.toString()))
        } catch (ex: UnknownHostException) {
            emit(Resource.Error(data = emptyList<User>(), message = "Check your internet connection."))
        }
        catch (ex: Exception) {
            emit(Resource.Error(data = emptyList<User>(), message = ex.message.toString()))
        }
    }

    override suspend fun getUserDetails(user: User): Flow<Resource<UserDetails>> = flow {
        emit(Resource.Loading())
        try {
        val details = api.getUserDetail(user.name).let {
            UserDetails(
                it.login,
                it.avatar_url,
                it.url,
                it.public_repos.toString(),
                it.public_gists.toString(),
                it.followers.toString(),
            )
        }

            emit(Resource.Success(details))

        } catch (ex: HttpException) {
            emit(Resource.Error<UserDetails>(message = ex.message.toString()))
        } catch (ex: UnknownHostException) {
            emit(Resource.Error<UserDetails>(message = "Check your internet connection."))
        }
        catch (ex: Exception) {
            emit(Resource.Error<UserDetails>(message = ex.message.toString()))
        }
    }


}