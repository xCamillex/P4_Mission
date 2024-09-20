package com.aura.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aura.R
import com.aura.databinding.FragmentLoginBinding
import com.aura.ui.home.HomeFragment
import com.aura.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
  * Ce fragment gère l'interface de connexion de l'application.
  * Il utilise un LoginViewModel pour gérer l'état de la connexion (nom d'utilisateur, mot de passe, bouton de connexion activé/désactivé).
  */
@AndroidEntryPoint
class LoginFragment : Fragment()
{

  // Binding de la vue
  private var _binding: FragmentLoginBinding? = null

  //Getter accédant à la propriété _binding de manière non nulle (utilisé pour plus de concision).
  private val binding get() = _binding!!

  // Initialise le ViewModel
  private val loginViewModel: LoginViewModel by viewModels()

  //Initialise le binding de l'interface.
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentLoginBinding.inflate(inflater, container, false)
    return binding.root
  }

  // Configure les éléments de l'interface et lie les données du ViewModel à l'UI.
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val login = binding.login
    val loading = binding.loading
    val usernameEditText = binding.identifier
    val passwordEditText = binding.password

    // Observe les changements d'état du bouton de connexion
    loginViewModel.isLoginButtonEnabled.onEach { isEnabled ->
      login.isEnabled = isEnabled
    }.launchIn(viewLifecycleOwner.lifecycleScope)

    // Observe les changements d'état du chargement
    loginViewModel.isLoading.onEach { isLoading ->
      loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }.launchIn(viewLifecycleOwner.lifecycleScope)

    // Observe les changements dans les champs de texte
    val textWatcher = object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        // Appelle les méthodes du ViewModel pour mettre à jour les valeurs
        when (s){
          usernameEditText.text -> loginViewModel.onUsernameChanged(s.toString())
          passwordEditText.text -> loginViewModel.onPasswordChanged(s.toString())
        }
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    // Ajout du TextWatcher aux champs de texte
    usernameEditText.addTextChangedListener(textWatcher)
    passwordEditText.addTextChangedListener(textWatcher)

    // Vérifie si les champs d'identification sont valides
    login.setOnClickListener {
      //Appelle la fonction navigateToHome de loginViewModel
    loginViewModel.onLoginClicked()
    //loginViewModel.navigateToHome()
    }
    
    loginViewModel.navigateToHomeEvent.onEach {
      // Replace the current fragment with HomeFragment
      requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, HomeFragment())
        .addToBackStack(null)
        .commit()
    }.launchIn(viewLifecycleOwner.lifecycleScope)

    // Observe les erreurs de connexion et les affiche
    loginViewModel.loginError.onEach { errorMessage ->
      errorMessage?.let {
        // Affiche le message d'erreur à l'utilisateur
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
      }
  }.launchIn(viewLifecycleOwner.lifecycleScope)
  }


  // Libère les ressources
  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
