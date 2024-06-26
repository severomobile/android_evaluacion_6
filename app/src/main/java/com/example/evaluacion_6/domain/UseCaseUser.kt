package com.example.evaluacion_6.domain

import com.example.evaluacion_6.data.remote.apiRepository.RepositoryApiImplement
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
import retrofit2.Response


class UseCaseUser(private val repositoryApi : RepositoryApiImplement) {

    suspend fun createNewUser(newUser: User): Response<UserResponse> {
        return repositoryApi.createNewUser(newUser)
    }

    suspend fun authMeUser(token: String): Response<UserAuthMeResponse> {
        return repositoryApi.authMeUser(token)
    }

    suspend fun getUserById(userId: Long): Response<DetailUserResponse> {
        return repositoryApi.getUserById(userId)
    }

    suspend fun loginUser(login: Login): Response<LoginResponse> {
        return repositoryApi.loginUser(login)
    }

    suspend fun accountMe(token: String): Response<AccountMeResponse> {
        return repositoryApi.accountMe(token)
    }

    suspend fun createNewAccount(newAccount: Account, token: String)
    : Response<AccountResponse> {
        return repositoryApi.createNewAccount(newAccount, token)
    }

    suspend fun getAllAccount(token: String): Response<AccountListResponse> {
        return repositoryApi.getAllAccount(token)
    }

    suspend fun depositAccount(
        accountId: Long,
        topup: Topup,
        token: String
    ): Response<TopupResponse> {
        return repositoryApi.depositAccount(accountId, topup, token)
    }

    suspend fun createNewTransaccion(newTransaccion: Transaccion)
    : Response<TransaccionResponse> {
        return repositoryApi.createTransaccion(newTransaccion)
    }

    suspend fun getAllTransaccion(token: String): Response<AllTransaccionResponse> {
        return repositoryApi.getAllTransaccion(token)
    }
}