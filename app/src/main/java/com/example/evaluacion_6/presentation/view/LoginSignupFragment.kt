package com.example.evaluacion_6.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.evaluacion_6.R
import com.example.evaluacion_6.data.remote.api.ApiClient
import com.example.evaluacion_6.data.remote.apiRepository.RepositoryApiImplement
import com.example.evaluacion_6.databinding.FragmentLoginSignupBinding
import com.example.evaluacion_6.domain.UseCaseUser
import com.example.evaluacion_6.presentation.viewModel.UserViewModel
import com.example.evaluacion_6.presentation.viewModel.ViewModelFactory

class LoginSignupFragment: Fragment() {

    private var _binding: FragmentLoginSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiClient = ApiClient()
        val repositoryApi = RepositoryApiImplement(apiClient)
        val useCaseUser = UseCaseUser(repositoryApi)

        val viewModelFactory = ViewModelFactory(useCaseUser)

        val userViewModel = ViewModelProvider(this, viewModelFactory)
            .get(UserViewModel::class.java)

        binding.apply {

            btnLoginSignupCrearCuenta.setOnClickListener {
                navigateToRegisterFragment()
            }

            btnLoginsignupIniciarSesion.setOnClickListener {
                navigateToLoginFragment()
            }
        }
    }

    private fun navigateToRegisterFragment() {
        val registerFragment = RegisterFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, registerFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToLoginFragment() {
        val loginFragment = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}