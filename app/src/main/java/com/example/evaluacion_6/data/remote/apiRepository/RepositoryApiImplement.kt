package com.example.evaluacion_6.data.remote.apiRepository

import com.example.evaluacion_6.data.remote.api.ApiClient
import com.example.evaluacion_6.data.remote.response.AccountMe.AccountMeResponse
import com.example.evaluacion_6.data.remote.response.AccountMe.AccountResponse
import com.example.evaluacion_6.data.remote.response.Accounts.AccountListResponse
import com.example.evaluacion_6.data.remote.response.LoginResponse
import com.example.evaluacion_6.data.remote.response.Transaccion.AllTransaccionResponse
import com.example.evaluacion_6.data.remote.response.Transaccion.TransaccionResponse
import com.example.evaluacion_6.data.remote.response.User.DetailUserResponse
import com.example.evaluacion_6.data.remote.response.User.UserAuthMeResponse
import com.example.evaluacion_6.presentation.model.User
import com.example.evaluacion_6.data.remote.response.User.UserResponse
import com.example.evaluacion_6.data.remote.response.topup.TopupResponse
import com.example.evaluacion_6.presentation.model.Account
import com.example.evaluacion_6.presentation.model.Login
import com.example.evaluacion_6.presentation.model.Topup
import com.example.evaluacion_6.presentation.model.Transaccion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class RepositoryApiImplement(private val apiClient: ApiClient) : RepositoryApi {

    override suspend fun createNewUser(newUser: User): Response<UserResponse> {
        return withContext(Dispatchers.IO){
            apiClient.createNewUser(newUser)
        }
    }

    override suspend fun authMeUser(token: String): Response<UserAuthMeResponse> {
        return withContext(Dispatchers.IO){
            apiClient.authMeUser(token)
        }
    }

    override suspend fun getUserById(userId: Long): Response<DetailUserResponse> {
        return withContext(Dispatchers.IO){
            apiClient.getUserById(userId)
        }
    }

    override suspend fun loginUser(login: Login): Response<LoginResponse> {
        return withContext(Dispatchers.IO){
            apiClient.loginUser(login)
        }
    }

    override suspend fun accountMe(token: String): Response<AccountMeResponse> {
        return withContext(Dispatchers.IO){
            apiClient.accountMe(token)
        }
    }

    override suspend fun createNewAccount(
        newAccount: Account,
        token: String
    ): Response<AccountResponse> {
        return withContext(Dispatchers.IO){
            apiClient.createNewAccount(newAccount, token)
        }
    }

    override suspend fun getAllAccount(token: String): Response<AccountListResponse> {
        return withContext(Dispatchers.IO){
            apiClient.getAllAccount(token)
        }
    }

    override suspend fun depositAccount(
        accountId: Long,
        topup: Topup,
        token: String
    ): Response<TopupResponse> {
        return withContext(Dispatchers.IO){
            apiClient.depositAccount(accountId, topup, token)
        }
    }


    override suspend fun createTransaccion(newTransaccion: Transaccion)
    : Response<TransaccionResponse> {
        return withContext(Dispatchers.IO){
            apiClient.createTransaccion(newTransaccion)
        }
    }

    override suspend fun getAllTransaccion(token: String): Response<AllTransaccionResponse> {
        return withContext(Dispatchers.IO){
            apiClient.getAllTransaccion(token)
        }
    }
}