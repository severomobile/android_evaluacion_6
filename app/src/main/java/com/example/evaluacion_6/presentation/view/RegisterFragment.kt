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
import com.example.evaluacion_6.databinding.FragmentRegisterBinding
import com.example.evaluacion_6.domain.UseCaseUser
import com.example.evaluacion_6.domain.UserSesion
import com.example.evaluacion_6.presentation.model.User
import com.example.evaluacion_6.presentation.viewModel.UserViewModel
import com.example.evaluacion_6.presentation.viewModel.ViewModelFactory


class RegisterFragment: Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
            btnRegisterCrearCuenta.setOnClickListener(){
                val firstName = eTRegisterFirstName.text.toString()
                val lastName = eTRegisterLastName.text.toString()
                val email = eTRegisterEmail.text.toString()
                val password = eTRegisterPassword.text.toString()
                val confirmPassword = eTRegisterConfirmPassword.text.toString()

                val validation = arrayOf(firstName, lastName, email, password, confirmPassword)
                when {
                    validation.any { it.isEmpty() } -> {
                        Toast.makeText(requireContext(),
                            "Todos los campos son obligatorios"
                            , Toast.LENGTH_SHORT)
                            .show()
                    }
                    password != confirmPassword -> {
                        Toast.makeText(requireContext(),
                            "Las contraseñas no coinciden"
                            , Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        val createUser = User(
                            first_name = firstName,
                            last_name = lastName,
                            email = email,
                            password = password
                        )
                        userViewModel.createNewUser(createUser)
                        userViewModel.userResponseLD.observe(viewLifecycleOwner){ response ->
                            response?.let {
                                Log.d("RegisterFragment", "Respuesta de la API: $response")
                                Toast.makeText(requireContext(),
                                    "Registro extitoso",
                                    Toast.LENGTH_SHORT)
                                    .show()

                                val userId = response.id.toLong()

                                UserSesion.userIdSesion = userId

                                navigateToLoginFragment()
                            }
                        }
                    }
                }
            }
        }
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