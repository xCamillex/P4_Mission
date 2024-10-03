package com.aura.ui.home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aura.R
import com.aura.databinding.ActivityHomeBinding
import com.aura.ui.login.LoginActivity
import com.aura.ui.transfer.TransferActivity
import com.aura.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Activity pour afficher la page d'accueil de l'application.
 * Cette activité affiche le solde total des comptes de l'utilisateur et permet de transférer de l'argent.
 * Elle utilise le ViewModel `HomeViewModel` pour récupérer et gérer l'état des comptes de l'utilisateur.
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: ActivityHomeBinding

    // Enregistre le résultat de l'activité de transfert
    private val startTransferActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val homeIntent = Intent(this@HomeActivity, HomeActivity::class.java)
                startActivity(homeIntent)
                // Si le transfert est réussi, rechargez les comptes
                getAccounts()
            } else {
                // Affiche un message d'erreur si le transfert échoue
                Toast.makeText(this@HomeActivity, "Erreur lors du transfert", Toast.LENGTH_LONG)
                    .show()
            }
        }

    // Fonction pour récupérer les comptes de l'utilisateur
    private fun getAccounts() {
        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val login = sharedPreferences.getString("identifier", "") ?: ""

        viewModel.getAccounts(login)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate le layout et configure la vue
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val balance = binding.balance
        val transfer = binding.transfer
        val loading = binding.loading
        val retry = binding.retryButton

        balance.text = "indéfini"

        // Récupère les comptes de l'utilisateur
        getAccounts()

        // Gère le clic sur le bouton de réessai
        retry.setOnClickListener {
            viewModel.resetError()
            viewModel.resetRetry()
            getAccounts()
        }

        // Collecte l'état de l'interface utilisateur
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                loading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
                retry.visibility = if (uiState.showRetryButton) View.VISIBLE else View.GONE

                // Affiche les messages d'erreur le cas échéant
                if (uiState.error.isNotEmpty()) {
                    Toast.makeText(this@HomeActivity, uiState.error, Toast.LENGTH_LONG).show()
                    viewModel.resetError()
                }
            }
        }

        // Observe les comptes de l'utilisateur et calcule le solde total
        viewModel.accounts.observe(this) { accounts ->
            val balanceSum = accounts.map { it.balance }.sum()
            balance.text = balanceSum.toString() + " €"
        }

        // Démarre l'activité de transfert lors d'un clic sur le bouton de transfert
        transfer.setOnClickListener {
            startTransferActivityForResult.launch(
                Intent(
                    this@HomeActivity,
                    TransferActivity::class.java
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.disconnect -> {
                // Déconnecte l'utilisateur et redirige vers la page de connexion
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}