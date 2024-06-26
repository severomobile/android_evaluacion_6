package com.example.evaluacion_6.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evaluacion_6.domain.UseCaseUser

class ViewModelFactory(private val useCaseUser: UseCaseUser) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(useCaseUser) as T
            }
            // para otro viewmodel copiar la de arriba y adaptar
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}