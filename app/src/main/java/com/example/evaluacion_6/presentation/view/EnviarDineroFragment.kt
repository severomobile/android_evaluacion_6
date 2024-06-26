package com.example.evaluacion_6.presentation.view

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.evaluacion_6.data.remote.api.ApiClient
import com.example.evaluacion_6.data.remote.apiRepository.RepositoryApiImplement
import com.example.evaluacion_6.databinding.FragmentEnviarDineroBinding
import com.example.evaluacion_6.domain.UseCaseUser
import com.example.evaluacion_6.domain.UserSesion
import com.example.evaluacion_6.presentation.model.Topup
import com.example.evaluacion_6.presentation.viewModel.UserViewModel
import com.example.evaluacion_6.presentation.viewModel.ViewModelFactory

class EnviarDineroFragment : Fragment() {

    private var _binding: FragmentEnviarDineroBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: ArrayAdapter<String> // Adaptador del Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnviarDineroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiClient = ApiClient()
        val repositoryApi = RepositoryApiImplement(apiClient)
        val useCaseUser = UseCaseUser(repositoryApi)

        val viewModelFactory = ViewModelFactory(useCaseUser)

        userViewModel = ViewModelProvider(this, viewModelFactory)
            .get(UserViewModel::class.java)

        // Inicialización del ArrayAdapter vacío
        adapter = ArrayAdapter<String>(requireContext(),
            R.layout.simple_spinner_item, mutableListOf())

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerEnviarDineroUsuarios.adapter = adapter

        val token = UserSesion.tokenSesion
        userViewModel.fetchAccountMe("Bearer $token")
        userViewModel.authMeUser("Bearer $token")
        userViewModel.loadListAccount("Bearer $token")

        userViewModel.authMeResponseLD.observe(viewLifecycleOwner){ response ->
            response?.let {
                val userName = "${response.first_name} ${response.last_name}"
                val userEmail = response.email

                binding.apply {
                    txtViewEnviarDineroNombreUsuario.text = userName
                    txtViewEnviarDineroCorreo.text = userEmail
                }
            }
        }

        userViewModel.getAllAccountLD.observe(viewLifecycleOwner) { response ->
            response?.let {

                val listaDeCuentas = response.data

                val userListId = listaDeCuentas.map { it.userId.toLong() }

                val accountListId = listaDeCuentas.map { it.id.toLong() }

                UserSesion.userIdList.clear()
                UserSesion.userIdList.addAll(userListId)
                Log.d("EnviarDinero", "listaId: ${UserSesion.userIdList}")

                UserSesion.accountIdList.clear()
                UserSesion.accountIdList.addAll(accountListId)
                Log.d("EnviarDinero", "listaAccountId: ${UserSesion.accountIdList}")

                userViewModel.getUserById(UserSesion.userIdList)
            }
        }

        userViewModel.detailUserResponseLD.observe(viewLifecycleOwner) { userResponses ->
            userResponses?.let { responses ->
                val userNameList = mutableListOf<String>()

                responses.forEach { response ->
                    response?.let {
                        val nombreCompleto = "${it.first_name} ${it.last_name}"
                        userNameList.add(nombreCompleto)
                    }
                }

                // Actualizar el ArrayAdapter con los nombres completos
                adapter.clear()
                adapter.addAll(userNameList)
                adapter.notifyDataSetChanged()

                Log.d("EnviarDinero", "Lista de nombres actualizada: $userNameList")
            }
        }

        // Manejar la selección del usuario en el Spinner
        binding.spinnerEnviarDineroUsuarios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Obtener el ID de cuenta correspondiente al usuario seleccionado
                if (position >= 0 && position < UserSesion.accountIdList.size) {
                    val accountId = UserSesion.accountIdList[position]
                    // Guardar el ID de cuenta seleccionado en UserSesion
                    UserSesion.toAccountId = accountId
                    Log.d("EnviarDinero", "ID de cuenta seleccionado: $accountId")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada si no se selecciona nada
            }
        }

        // Configurar el clic del botón enviar dinero
        binding.btnEnviarDinero.setOnClickListener {
            val amount = binding.eTEnviarDineroMontoEnviar.text.toString().toInt()

            val accountId = UserSesion.toAccountId

            val newTopup = Topup(
                type = "payment",
                concept = "payment",
                amount = amount
            )

            userViewModel.topupAccount(accountId!!, newTopup, "Bearer $token")

            userViewModel.loadAllTransaccion("Bearer $token")

            navigateToAccountFragment()
        }
    }

    private fun navigateToAccountFragment() {
        val accountFragment = AccountFragment()
        parentFragmentManager.beginTransaction()
            .replace(com.example.evaluacion_6.R.id.fragment_container, accountFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



