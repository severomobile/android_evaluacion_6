package com.example.evaluacion_6.data.remote.apiRepository

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

interface RepositoryApi {

    suspend fun createNewUser(newUser: User): Response<UserResponse>

    suspend fun authMeUser(token: String): Response<UserAuthMeResponse>

    suspend fun getUserById(userId: Long): Response<DetailUserResponse>

    suspend fun loginUser(login: Login): Response<LoginResponse>

    suspend fun accountMe(token: String): Response<AccountMeResponse>

    suspend fun createNewAccount(newAccount: Account, token: String): Response<AccountResponse>

    suspend fun getAllAccount(token: String): Response<AccountListResponse>

    suspend fun depositAccount(
        accountId: Long,
        topup: Topup,
        token: String
    ): Response<TopupResponse>

    suspend fun createTransaccion(newTransaccion: Transaccion): Response<TransaccionResponse>

    suspend fun getAllTransaccion(token: String): Response<AllTransaccionResponse>
}