package com.aura.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.aura.databinding.ActivityLoginBinding
import com.aura.ui.home.HomeActivity
import com.aura.viewmodel.login.LoginViewModel
import com.aura.viewmodel.login.NavigationEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Activity pour gérer l'authentification des utilisateurs.
 * Cette activité permet aux utilisateurs de se connecter en fournissant un identifiant et un mot de passe.
 * Elle utilise le ViewModel `LoginViewModel` pour gérer l'état de l'interface utilisateur et les opérations de connexion.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate le layout et configure la vue
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Liaison des vues
        val login = binding.login
        val loading = binding.loading
        val identifier = binding.identifier
        val password = binding.password
        val errorText = binding.errorText
        val retryButton = binding.retryButton

        // Collecte de l'état de l'interface utilisateur
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                loading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
                login.isEnabled = uiState.isLoginButtonEnabled

                // Affichage des messages d'erreur et gestion de la visibilité du bouton de réessai
                if (uiState.showErrorMessage) {
                    errorText.visibility = View.VISIBLE
                    errorText.text = uiState.error
                    retryButton.visibility = View.VISIBLE
                } else {
                    errorText.visibility = View.GONE
                    retryButton.visibility = View.GONE
                }
            }
        }

        // Ajoute des écouteurs de texte pour mettre à jour le ViewModel
        identifier.addTextChangedListener { viewModel.onIdentifierChanged(it.toString()) }
        password.addTextChangedListener { viewModel.onPasswordChanged(it.toString()) }

        // Gestion de l'événement de clic sur le bouton de connexion
        login.setOnClickListener {
            viewModel.onLoginClicked()

            // Sauvegarde de l'identifiant dans SharedPreferences
            val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("identifier", identifier.text.toString())
            editor.commit()
        }

        // Gestion de l'événement de clic sur le bouton de réessai
        retryButton.setOnClickListener { viewModel.onRetryClicked() }

        // Collecte des événements de navigation pour gérer les redirections
        lifecycleScope.launch {
            viewModel.navigationEvent.collect { event ->
                when (event) {
                    is NavigationEvent.ShowSnackbar -> {
                        showSnackbarAtTop(event.message)
                    }

                    is NavigationEvent.NavigateToHome -> {
                        delay(1000)  // Attendre 1 seconde avant la navigation
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }

                    is NavigationEvent.ShowSuccessAndNavigate -> {
                        showSnackbarAtTop(event.message)
                        delay(1500)  // Afficher le Snackbar pendant 1.5 seconde avant la navigation
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    /**
     * Affiche un Snackbar en haut de l'écran avec le message spécifié.
     */
    private fun showSnackbarAtTop(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        val view = snackbar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        params.setMargins(0, 0, 0, 0)
        view.layoutParams = params
        snackbar.show()
    }
}