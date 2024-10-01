package com.aura.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aura.R
import com.aura.databinding.FragmentHomeBinding
import com.aura.model.home.AccountsState
import com.aura.ui.MainActivity
import com.aura.ui.transfer.TransferFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * The home activity for the app. L'activité principale de l'application.
 */
@AndroidEntryPoint
class HomeFragment (val userID: String): Fragment() {

    /**
     * The binding for the home layout.
     */
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    /**
     * A callback for the result of starting the TransferActivity.
     * Un rappel pour le résultat du démarrage de TransferActivity.
     */

    private val startTransferActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            //if (result.resultCode == Activity.RESULT_OK) getUserAccounts()
        }

    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val balance = binding.balance
        val transfer = binding.transfer

        balance.text = "indéfini"

        homeViewModel.getUserAccounts(userID)

        lifecycleScope.launch {
            homeViewModel.accountsState.collect { state ->
                when (state) {
                    AccountsState.Loading -> {
                        binding.balance.text = "Loading..."
                        binding.loading.visibility = View.VISIBLE
                        binding.retryButton.visibility = View.GONE
                    }
                    is AccountsState.Success -> {
                        binding.loading.visibility = View.GONE
                        val balance = state.userAccount.balance
                        binding.balance.text = String.format("%.2f €", balance)
                    }
                    is AccountsState.Error -> {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(requireContext(), "Erreur: problème réseau", Toast.LENGTH_SHORT).show()
                        binding.balance.text = "Error"

                        if (state.showRetryButton) {
                            binding.retryButton.visibility = View.VISIBLE
                        } else {
                            binding.retryButton.visibility = View.GONE
                        }
                    }
                }
            }
        }
        binding.retryButton.setOnClickListener {
            homeViewModel.getUserAccounts(userID)
        }

        transfer.setOnClickListener {
            // Replace the current fragment with TransferFragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TransferFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.disconnect -> {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}