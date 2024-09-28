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
import com.aura.model.login.ConnectionState
import com.aura.databinding.FragmentLoginBinding
import com.aura.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
  * Ce fragment gère l'interface de connexion de l'application.
  * Il utilise un com.aura.ui.login.LoginViewModel pour gérer l'état de la connexion (nom d'utilisateur, mot de passe, bouton de connexion activé/désactivé).
  */
@AndroidEntryPoint
class LoginFragment : Fragment() {

  // Binding de la vue
  private lateinit var binding: FragmentLoginBinding

  // Initialise le ViewModel
  private val loginViewModel: LoginViewModel by viewModels()

  private val textWatcher = object : TextWatcher {
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
      updateLoginButtonState() // call  to update the button state
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {}
  }

  //Initialise le binding de l'interface.
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentLoginBinding.inflate(inflater, container, false)
    return binding.root
  }

  // Configure les éléments de l'interface et lie les données du ViewModel à l'UI.
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    //Setup all listeners
    setupViewListeners()
    //Collect current Login State from the Viewmodel
    updateCurrentLoginState()
  }

  /**
   * Method to setup all the used views listener in the activity.
   */
  private fun setupViewListeners() {
    //Set the button login listener.
    binding.login.setOnClickListener {
      loginViewModel.Login(
        binding.identifier.text.toString().trim(),
        binding.password.text.toString().trim()
      )
    }
    //add text watcher to our views
    binding.identifier.addTextChangedListener(textWatcher)
    binding.password.addTextChangedListener(textWatcher)
  }

  /**
   * Method to collect the current login state and update all views in consequences.
   * @param loginViewModel the ViewModel to use to collect the state flow
   */
  private fun updateCurrentLoginState() {
    //on collect notre StateFlow d'etat d'ecran actuel depuis le viewmodel
    viewLifecycleOwner.lifecycleScope.launch {
      // Collect du flow de l'état depuis le ViewModel
      loginViewModel.state.collect { connectionState ->
        var message: String? = null
        // Mettre à jour l'interface utilisateur en fonction de l'état de connexion
        when (connectionState) {
          ConnectionState.INITIAL -> {
            // Ne rien faire, l'utilisateur n'a pas encore saisi ses informations
            binding.login.isEnabled = false
            binding.loading.visibility = View.GONE
            }

          ConnectionState.FIELDS_FILLED -> {
            binding.login.isEnabled = true
            }

          ConnectionState.ERROR -> {
            // Afficher un message d'erreur
            message = ERROR_NETWORK
            showToast(message)
            binding.loading.visibility = View.GONE
            binding.login.isEnabled = false
            binding.identifier.text.clear()
              binding.password.text.clear()
            }

          ConnectionState.CONNECTING -> {
            message = "Connexion en cours ..."
            showToast(message)
              // Afficher un indicateur de chargement
              binding.loading.visibility = View.VISIBLE
              binding.login.isEnabled = false
            binding.identifier.clearFocus()
            binding.password.clearFocus()
            }

          ConnectionState.FAILED -> {
            message = ERROR_FAILED
            showToast(message)
              // Afficher un indicateur de chargement
              binding.loading.visibility = View.GONE
              binding.login.isEnabled = false
            binding.identifier.text.clear()
            binding.password.text.clear()
            }


          ConnectionState.CONNECTED -> {
            // Afficher un message de succès
            message = SUCCESS_LOGIN
            // Naviguer vers HomeFragment
           // Cacher l'indicateur de chargement
              binding.loading.visibility = View.GONE
            showToast(message)
              requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment(binding.identifier.text.toString().trim()))
                .addToBackStack(null)
                .commit()
            }
          }
        }
      }
    }

    /**
     * Method to update the state of the login button
     * this method call the viewmodel methode to verify if all condition to login is ok
     * to update the current screen state (and unlock the button)
     */
    private fun updateLoginButtonState() {
      loginViewModel.checkFields(
        binding.identifier.text.toString().trim(),
        binding.password.text.toString().trim()
      )
    }
  private fun showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
  }
  companion object {
    const val ERROR_NETWORK = "Une erreur réseau est survenue lors de la connexion."
    const val ERROR_FAILED = "Echec de la connexion. Mauvais identifiant ou mot de passe."
    const val SUCCESS_LOGIN = "Connexion réussie !"
  }
}