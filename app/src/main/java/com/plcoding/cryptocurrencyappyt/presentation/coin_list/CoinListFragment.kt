package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.plcoding.cryptocurrencyappyt.R
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.databinding.FragmentCoinListBinding
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * A fragment representing a list of Items.

 */
@AndroidEntryPoint
class CoinListFragment : Fragment(), OnItemClickListener {
    private val viewModel: CoinListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCoinListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        // Set the adapter
        val adapter = CoinsItemRecyclerViewAdapter(this)
        binding.recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { result ->
                    if (result.coins.isNotEmpty()) {
                        adapter.submitList(result.coins)
                    }

                }
            }
        }
        return binding.root
    }

    override fun onItemClick(item: Coin) {
        val args = Bundle().apply {
            putString(Constants.PARAM_COIN_ID, item.id)
        }
        findNavController().navigate(R.id.action_coinListItemFragment_to_coinDetailsFragment, args)

    }
}