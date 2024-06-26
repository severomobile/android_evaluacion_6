package com.example.evaluacion_6.data.remote.api

import com.example.evaluacion_6.data.remote.response.AccountMe.AccountMeResponse
import com.example.evaluacion_6.data.remote.response.AccountMe.AccountResponse
import com.example.evaluacion_6.data.remote.response.Accounts.AccountListResponse
import com.example.evaluacion_6.data.remote.response.User.DetailUserResponse
import com.example.evaluacion_6.data.remote.response.LoginResponse
import com.example.evaluacion_6.data.remote.response.Transaccion.AllTransaccionResponse
import com.example.evaluacion_6.data.remote.response.Transaccion.TransaccionResponse
import com.example.evaluacion_6.data.remote.response.User.UserAuthMeResponse
import com.example.evaluacion_6.presentation.model.User
import com.example.evaluacion_6.data.remote.response.User.UserResponse
import com.example.evaluacion_6.data.remote.response.topup.TopupResponse
import com.example.evaluacion_6.presentation.model.Account
import com.example.evaluacion_6.presentation.model.Login
import com.example.evaluacion_6.presentation.model.Topup
import com.example.evaluacion_6.presentation.model.Transaccion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @Headers("Accept: application/json")

    @POST("users")
    suspend fun createNewUser(@Body newUser: User): Response<UserResponse>

    @GET("auth/me")
    suspend fun authMeUser(@Header("Authorization") token: String): Response<UserAuthMeResponse>

    @GET("users/{userId}")
    suspend fun getUserById(@Path("userId") userId: Long): Response<DetailUserResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body login: Login): Response<LoginResponse>

    @GET("accounts/me")
    suspend fun accountMe(@Header("Authorization") token: String): Response<AccountMeResponse>

    @POST("accounts")
    suspend fun createNewAccount(
        @Body newAccount: Account,
        @Header("Authorization") token: String): Response<AccountResponse>

    @POST("accounts/{accountId}")
    suspend fun depositAccount(
        @Path("accountId") accountId: Long,
        @Body topup: Topup,
        @Header("Authorization") token: String
    ): Response<TopupResponse>

    @GET("accounts")
    suspend fun getAllAccount(@Header("Authorization") token: String): Response<AccountListResponse>

    @POST("transactions")
    suspend fun createTransaccion(@Body newTransaccion: Transaccion): Response<TransaccionResponse>

    @GET("transactions")
    suspend fun getAllTransaccion(@Header("Authorization") token: String):
            Response<AllTransaccionResponse>
}