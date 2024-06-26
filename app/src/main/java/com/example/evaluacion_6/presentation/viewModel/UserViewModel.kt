package com.example.evaluacion_6.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.evaluacion_6.domain.UseCaseUser
import com.example.evaluacion_6.presentation.model.Account
import com.example.evaluacion_6.presentation.model.Login
import com.example.evaluacion_6.presentation.model.Topup
import com.example.evaluacion_6.presentation.model.Transaccion
import kotlinx.coroutines.launch


class UserViewModel(private val useCaseUser : UseCaseUser) : ViewModel() {

    private val _userResponseLiveData = MutableLiveData<UserResponse?>()
    val userResponseLD: LiveData<UserResponse?>
        get() = _userResponseLiveData

    fun createNewUser(newUser: User) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.createNewUser(newUser)
                if (response.isSuccessful && response.code() == 201) {
                    // La solicitud se completó correctamente (código de estado 201 para "Created")
                    val userResponse = response.body()
                    // Manejar el userResponse según sea necesario
                    _userResponseLiveData.postValue(userResponse)
                    Log.d("viewmodel", "Usuario creado")
                } else {
                    // La solicitud falló o el código de estado no es 201, manejar el error según sea necesario
                    Log.d("viewmodel", "Usuario fallido")
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción que pueda ocurrir durante la solicitud
                Log.d("viewmodel", "No se pudo crear un usuario: ${e.message}")
            }
        }
    }

    private val _autMeUserResponseLiveData = MutableLiveData<UserAuthMeResponse?>()
    val authMeResponseLD: LiveData<UserAuthMeResponse?>
        get() = _autMeUserResponseLiveData

    fun authMeUser(token: String) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.authMeUser(token)
                if (response.isSuccessful && response.code() == 200) {
                    val authMeResponse = response.body()
                    _autMeUserResponseLiveData.postValue(authMeResponse)
                    Log.d("viewmodel", "AuthMe User Exitoso")
                } else {
                    Log.d("viewmodel",
                        "Auth Me User Fail. Código de estado: ${response.code()}")
                }
            }catch (e: Exception) {
                Log.d("viewmodel", "No se pudo AuthMe: ${e.message}")
            }
        }
    }

    private val _detailUserResponseLiveData = MutableLiveData<List<DetailUserResponse?>>()
    val detailUserResponseLD: MutableLiveData<List<DetailUserResponse?>>
        get() = _detailUserResponseLiveData

    fun getUserById(userIds: List<Long>) {
        viewModelScope.launch {
            try {
                val responses = userIds.map { userId ->
                    val response = useCaseUser.getUserById(userId)
                    if (response.isSuccessful && response.code() == 200) {
                        response.body()
                    } else {
                        null
                    }
                }
                _detailUserResponseLiveData.postValue(responses)
                Log.d("viewmodel", "Detail User Exitoso")
            } catch (e: Exception) {
                Log.d("viewmodel", "No se pudo obtener los DetailUser: ${e.message}")
            }
        }
    }


    private val _loginResponseLiveData = MutableLiveData<LoginResponse?>()
    val loginResponseLD: LiveData<LoginResponse?>
        get() = _loginResponseLiveData

    fun newLogin(login: Login) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.loginUser(login)
                if (response.isSuccessful && response.code() == 200) {
                    val loginResponse = response.body()
                    _loginResponseLiveData.postValue(loginResponse)

                    Log.d("viewmodel", "Login Exitoso")
                } else {
                    Log.d("viewmodel",
                        "Login Fail. Código de estado: ${response.code()}")
                }

            }catch (e: Exception){
                Log.d("viewmodel", "No se pudo Login: ${e.message}")
            }
        }
    }

    private val _accountMeLiveData = MutableLiveData<AccountMeResponse?>()
    val accountMeResponseLD: LiveData<AccountMeResponse?>
        get() = _accountMeLiveData

    fun fetchAccountMe(token: String) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.accountMe(token)
                if (response.isSuccessful && response.code() == 200) {
                    // Procesar la respuesta exitosa
                    val accountMeResponse = response.body()
                    _accountMeLiveData.postValue(accountMeResponse)
                    Log.d("viewmodel", "AccountMe Exito")
                } else {
                    // Registrar el código de estado HTTP en caso de fallo
                    Log.d("viewmodel", "AccountMe Fail. Código de estado: ${response.code()}")
                }
            }catch (e: Exception){
                Log.d("viewmodel", "No se pudo Accountme: ${e.message}")
            }
        }
    }

    private val _accountResponseLiveData = MutableLiveData<AccountResponse?>()
    val accountResponseLD: LiveData<AccountResponse?>
        get() = _accountResponseLiveData

    fun createNewAccount(newAccount: Account, token: String) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.createNewAccount(newAccount, token)
                if (response.isSuccessful && response.code() == 201) {
                    val accountResponse = response.body()
                    _accountResponseLiveData.postValue(accountResponse)
                    Log.d("viewmodel", "Cuenta creada")
                }else {
                    Log.d("viewmodel", "Cuenta fallida")
                }
            }catch (e: Exception){
                // Manejar cualquier excepción que pueda ocurrir durante la solicitud
                Log.d("viewmodel", "No se pudo crear una cuenta: ${e.message}")
            }
        }
    }

    private val _getAllAccountLiveData = MutableLiveData<AccountListResponse?>()
    val getAllAccountLD: LiveData<AccountListResponse?>
        get() = _getAllAccountLiveData

    fun loadListAccount(token: String) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.getAllAccount(token)
                if (response.isSuccessful && response.code() == 200) {
                    val listResponse = response.body()
                    _getAllAccountLiveData.postValue(listResponse)
                    Log.d("viewmodel", "Lista de cuentas cargadas")
                } else {
                    Log.d("viewmodel", "Lista de cuentas no cargadas")
                }
            } catch (e: Exception) {
                Log.d("viewmodel", "No se pudo cargar lista de cuentas: ${e.message}")
            }
        }
    }

    private val _transaccionResponseLiveData = MutableLiveData<TransaccionResponse?>()
    val transaccionResponseLD: LiveData<TransaccionResponse?>
        get() = _transaccionResponseLiveData

    fun createNewTransaccion(newTransaccion: Transaccion) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.createNewTransaccion(newTransaccion)
                if (response.isSuccessful && response.code() == 201) {
                    val transaccionResponse = response.body()
                    _transaccionResponseLiveData.postValue(transaccionResponse)
                    Log.d("viewmodel", "Transaccion creada")
                } else  {
                    Log.d("viewmodel", "Transaccion Fallida")
                }
            }catch (e: Exception) {
                Log.d("viewmodel", "No se pudo crear una transaccion: ${e.message}")
            }
        }
    }

    private val _depositAccountLiveData = MutableLiveData<TopupResponse?>()
    val depositAccountLD: LiveData<TopupResponse?>
        get() = _depositAccountLiveData

    fun topupAccount(accountId: Long, topup: Topup, token: String) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.depositAccount(accountId, topup, token)
                if (response.isSuccessful && response.code() == 200) {
                    val topupAccountResponse = response.body()
                    _depositAccountLiveData.postValue(topupAccountResponse)
                    Log.d("viewmodel", "recarga cargada")
                } else {
                    Log.d("viewmodel", "recarga no fue cargada")
                }
            }catch (e: Exception) {
                Log.d("viewmodel", "No se pudo cargar recarga: ${e.message}")
            }
        }
    }

    private val _getAllTransaccionLiveData = MutableLiveData<AllTransaccionResponse?>()
    val getAllTransaccionLD: LiveData<AllTransaccionResponse?>
        get() = _getAllTransaccionLiveData

    fun loadAllTransaccion(token: String) {
        viewModelScope.launch {
            try {
                val response = useCaseUser.getAllTransaccion(token)
                if (response.isSuccessful && response.code() == 200) {
                    val allTransaccionResponse = response.body()
                    _getAllTransaccionLiveData.postValue(allTransaccionResponse)
                    Log.d("viewmodel", "Transacciones cargadas")
                } else {
                    Log.d("viewmodel", "Transacciones no fueron cargadas")
                }

            }catch (e: Exception) {
                Log.d("viewmodel", "No se pudo cargar transacciones: ${e.message}")
            }
        }
    }
}