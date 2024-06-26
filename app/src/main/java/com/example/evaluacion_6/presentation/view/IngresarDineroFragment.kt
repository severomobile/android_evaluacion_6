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
import com.example.evaluacion_6.databinding.FragmentIngresarDineroBinding
import com.example.evaluacion_6.domain.UseCaseUser
import com.example.evaluacion_6.domain.UserSesion
import com.example.evaluacion_6.presentation.model.Topup
import com.example.evaluacion_6.presentation.model.Transaccion
import com.example.evaluacion_6.presentation.viewModel.UserViewModel
import com.example.evaluacion_6.presentation.viewModel.ViewModelFactory
import java.util.Date

class IngresarDineroFragment: Fragment() {

    private var _binding: FragmentIngresarDineroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngresarDineroBinding.inflate(inflater, container, false)
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

        val token = UserSesion.tokenSesion

        userViewModel.fetchAccountMe("Bearer $token")
        userViewModel.authMeUser("Bearer $token")

        userViewModel.authMeResponseLD.observe(viewLifecycleOwner){ response ->
            response?.let {
                val userName = "${response.first_name} ${response.last_name}"
                val userEmail = response.email

                binding.apply {
                    txtViewIngresarDineroNombre.text = userName
                    txtViewIngresarDineroCorreo.text = userEmail
                }
            }
        }

        userViewModel.accountMeResponseLD.observe(viewLifecycleOwner){
            response -> response?.let {
                response.forEach { item ->
                    val accountId = item.id.toLong()
                    UserSesion.accountIdSesion = accountId
                }
            }
        }

        binding.apply {
            btnIngresarDineroIngresarDinero.setOnClickListener {

                val amount = eTViewIngresarDineroMontoIngresar.text.toString().toInt()
                val userId = UserSesion.userIdSesion
                val accountId = UserSesion.accountIdSesion

                val newTopup = Topup(
                    type = "topup",
                    concept = "Recarga",
                    amount = amount
                )

                userViewModel.topupAccount(accountId!!, newTopup, "Bearer $token")

                userViewModel.loadAllTransaccion("Bearer $token")

                navigateToAccountFragment()
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