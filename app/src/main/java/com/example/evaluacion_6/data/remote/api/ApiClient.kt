package com.example.evaluacion_6.data.remote.api

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
import com.example.evaluacion_6.data.remote.retrofit.RetrofitHelper
import com.example.evaluacion_6.presentation.model.Account
import com.example.evaluacion_6.presentation.model.Login
import com.example.evaluacion_6.presentation.model.Topup
import com.example.evaluacion_6.presentation.model.Transaccion
import retrofit2.Response


class ApiClient: ApiService  {

    private val retrofit = RetrofitHelper.getRetroFit()
    private val service = retrofit.create(ApiService::class.java)

    override suspend fun createNewUser(newUser: User): Response<UserResponse> {
        return service.createNewUser(newUser)
    }

    override suspend fun authMeUser(token: String): Response<UserAuthMeResponse> {
        return service.authMeUser(token)
    }

    override suspend fun getUserById(userId: Long): Response<DetailUserResponse> {
        return service.getUserById(userId)
    }

    override suspend fun loginUser(login: Login): Response<LoginResponse> {
        return service.loginUser(login)
    }

    override suspend fun accountMe(token: String): Response<AccountMeResponse> {
        return  service.accountMe(token)
    }

    override suspend fun createNewAccount(newAccount: Account, token: String)
    : Response<AccountResponse> {
        return service.createNewAccount(newAccount, token)
    }

    override suspend fun depositAccount(
        accountId: Long,
        topup: Topup,
        token: String
    ): Response<TopupResponse> {
        return service.depositAccount(accountId, topup, token)
    }

    override suspend fun getAllAccount(token: String): Response<AccountListResponse> {
        return service.getAllAccount(token)
    }


    override suspend fun createTransaccion(newTransaccion: Transaccion)
    : Response<TransaccionResponse> {
        return service.createTransaccion(newTransaccion)
    }

    override suspend fun getAllTransaccion(token: String): Response<AllTransaccionResponse> {
        return service.getAllTransaccion(token)
    }
}