package com.aura.ui.transfer

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.aura.databinding.ActivityTransferBinding
import com.aura.viewmodel.transfer.NavigationEvent
import com.aura.viewmodel.transfer.TransferViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Activity pour gérer le transfert de fonds.
 * Cette activité permet à l'utilisateur de spécifier un destinataire et un montant, puis d'initier un transfert.
 * Elle utilise le ViewModel `TransferViewModel` pour gérer la logique de transfert et l'état de l'interface utilisateur.
 */
@AndroidEntryPoint
class TransferActivity : AppCompatActivity() {

    /**
     * La liaison pour la mise en page de l'activité de transfert.
     */
    private lateinit var binding: ActivityTransferBinding
    private val viewModel: TransferViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate le layout et configure la vue
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupère l'identifiant de l'utilisateur depuis SharedPreferences
        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val sender = sharedPreferences.getString("identifier", "") ?: ""

        // Liaison des vues
        val recipient = binding.recipient
        val amount = binding.amount
        val transfer = binding.transfer
        val loading = binding.loading

        // Gestion de l'événement de clic sur le bouton de transfert
        transfer.setOnClickListener {
            viewModel.onTransferClicked(
                sender = sender,
                recipient.text.toString(),
                amount.text.toString().toDouble()
            )
        }

        // Collecte des événements de navigation pour gérer la redirection
        lifecycleScope.launch {
            viewModel.navigationEvents.collect { event ->
                when (event) {
                    is NavigationEvent.NavigateToHome -> {
                        delay(1000)  // Attendre 1 seconde avant la navigation
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
            }
        }

        // Ajoute des écouteurs de texte pour mettre à jour le ViewModel
        recipient.addTextChangedListener { viewModel.onRecipientChanged(it.toString()) }
        amount.addTextChangedListener { viewModel.onAmountChanged(it.toString().toDouble()) }

        // Collecte de l'état de l'interface utilisateur
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                loading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE

                // Affiche un message d'erreur si nécessaire
                if (uiState.error.isNotEmpty()) {
                    Toast.makeText(this@TransferActivity, uiState.error, Toast.LENGTH_LONG).show()
                    viewModel.resetError()
                }
            }
        }
    }
}