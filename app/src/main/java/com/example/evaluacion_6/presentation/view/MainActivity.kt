package com.example.evaluacion_6.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.evaluacion_6.R
import com.example.evaluacion_6.data.remote.api.ApiClient
import com.example.evaluacion_6.data.remote.apiRepository.RepositoryApiImplement
import com.example.evaluacion_6.domain.UseCaseUser
import com.example.evaluacion_6.presentation.viewModel.UserViewModel
import com.example.evaluacion_6.presentation.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginSignupFragment())
            .commit()
    }
}