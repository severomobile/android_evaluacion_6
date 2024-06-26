package com.example.evaluacion_6.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.evaluacion_6.R
import com.example.evaluacion_6.data.remote.api.ApiClient
import com.example.evaluacion_6.data.remote.apiRepository.RepositoryApiImplement
import com.example.evaluacion_6.databinding.FragmentLoginBinding
import com.example.evaluacion_6.domain.UseCaseUser
import com.example.evaluacion_6.domain.UserSesion
import com.example.evaluacion_6.presentation.model.Account
import com.example.evaluacion_6.presentation.model.Login
import com.example.evaluacion_6.presentation.viewModel.UserViewModel
import com.example.evaluacion_6.presentation.viewModel.ViewModelFactory
import java.util.Date

class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
            /**
             * Get the form data
             */
            btnLoginIniciarSesion.setOnClickListener {
                val email = eTLoginEmail.text.toString()
                val password = eTLoginPassword.text.toString()

                when {
                    email.isEmpty() && password.isEmpty() -> {
                        Toast.makeText(requireContext(),
                            "Todos los campos son obligatorios"
                            , Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        val createLogin = Login(
                            email = email,
                            password = password
                        )
                        userViewModel.newLogin(createLogin)
                        userViewModel.loginResponseLD.observe(viewLifecycleOwner){ response ->
                            response?.let {
                                Log.d("LoginFragment", "Respuesta de la API: $response")

                                val token = response.accessToken
                                UserSesion.tokenSesion = token

                                val userId = UserSesion.userIdSesion

                                userViewModel.fetchAccountMe("Bearer $token")
                                userViewModel.accountMeResponseLD.observe(viewLifecycleOwner) {
                                    response ->
                                    response?.let {
                                        if (response.isEmpty()) {
                                            val createAccount  = Account(
                                                creationDate = Date().toString(),
                                                money = 0,
                                                isBlocked = false,
                                                userId = userId
                                            )
                                            userViewModel.createNewAccount(createAccount, "Bearer $token")
                                                Toast.makeText(requireContext(),
                                                    "Login extitoso",
                                                    Toast.LENGTH_SHORT)
                                                    .show()
                                                navigateToAccountFragment()
                                        } else {
                                            Toast.makeText(requireContext(),
                                                "Login extitoso",
                                                Toast.LENGTH_SHORT)
                                                .show()
                                            navigateToAccountFragment()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToAccountFragment() {
        val accountFragment = AccountFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, accountFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}