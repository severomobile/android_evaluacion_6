package com.example.evaluacion_6.domain

object UserSesion {
    var tokenSesion: String? = null
    var userIdSesion: Long? = null
    var accountIdSesion: Long? = null
    var toAccountId: Long? = null

    var userIdList: MutableList<Long> = mutableListOf()
    var accountIdList: MutableList<Long> = mutableListOf()
}

