package com.example.evaluacion_6.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.evaluacion_6.R
import com.example.evaluacion_6.data.remote.api.ApiClient
import com.example.evaluacion_6.data.remote.apiRepository.RepositoryApiImplement
import com.example.evaluacion_6.databinding.FragmentAccountBinding
import com.example.evaluacion_6.domain.UseCaseUser
import com.example.evaluacion_6.domain.UserSesion
import com.example.evaluacion_6.presentation.view.adpter.TransaccionAdapter
import com.example.evaluacion_6.presentation.viewModel.UserViewModel
import com.example.evaluacion_6.presentation.viewModel.ViewModelFactory

class AccountFragment: Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var transaccionAdapter: TransaccionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
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
        Log.d("AccountFragment", "Respuesta de userviewmodel token: $token")

        userViewModel.authMeUser("Bearer $token")
        userViewModel.authMeResponseLD.observe(viewLifecycleOwner) {
            response ->
            response?.let {
                val userName = response.first_name

                binding.apply {
                    txtViewAccountNombre.text = "Hello, $userName"
                }
            }
        }

        userViewModel.fetchAccountMe("Bearer $token")
        userViewModel.accountMeResponseLD.observe(viewLifecycleOwner) {
            response ->
            response?.let {
                response.forEach {
                    itemResponse ->
                    val userMoney = itemResponse.money

                    binding.apply {
                        txtViewAccountSaldo.text = "Tu saldo es: $userMoney"
                    }
                }
            }
        }

        // Configurar el RecyclerView y el adaptador
        setupRecyclerView()
        userViewModel.loadAllTransaccion("Bearer $token")
        userViewModel.getAllTransaccionLD.observe(viewLifecycleOwner){
            response -> response?.let {
                val lista = response.data
                transaccionAdapter.submitList(lista)
            }
        }

        binding.apply {
            btnAccountDepositMoney.setOnClickListener {
                navigateToIngresarDineroFragment()
            }

            btnAccountSendMoney.setOnClickListener {
                navigateToEnviarDineroFragment()
            }
        }
    }

    private fun setupRecyclerView() {
        transaccionAdapter = TransaccionAdapter()
        binding.recyclerViewTransacciones.apply {
            adapter = transaccionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun navigateToIngresarDineroFragment() {
        val ingresarDineroFragment = IngresarDineroFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ingresarDineroFragment)
            .addToBackStack("AccountFragment")
            .commit()
    }

    private fun navigateToEnviarDineroFragment() {
        val enviarDineroFragment = EnviarDineroFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, enviarDineroFragment)
            .addToBackStack("AccountFragment")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}