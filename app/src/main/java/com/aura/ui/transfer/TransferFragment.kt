package com.aura.ui.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aura.R
import com.aura.databinding.FragmentTransferBinding
import com.aura.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * The transfer activity for the app. L'activité de transfert pour l'application.
 */
@AndroidEntryPoint
class TransferFragment : Fragment() {

  /**
   * The binding for the transfer layout.
   */
  private var _binding: FragmentTransferBinding? = null
  private val binding get() = _binding!!

  private val transferViewModel: TransferViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentTransferBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val recipient = binding.recipient
    val amount = binding.amount
    val transfer = binding.transfer
    val loading = binding.loading

    binding.recipient.setOnEditorActionListener { _, _, _ ->
      transferViewModel.updateRecipient(binding.recipient.text.toString())
      true
    }
    binding.amount.setOnEditorActionListener { _, _, _ ->
      transferViewModel.updateAmount(binding.amount.text.toString())
      true
    }

// Observer l'état de cliquabilité du bouton de transfert
    lifecycleScope.launch {
      transferViewModel.isTransferEnabled.collectLatest { isEnabled ->
        binding.transfer.isEnabled = isEnabled
      }
    }

    transfer.setOnClickListener {
      loading.visibility = View.VISIBLE

      // Remplace le fragment actuel par HomeFragment
      requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, HomeFragment(userID = "1234"))
        .addToBackStack(null)
        .commit()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
