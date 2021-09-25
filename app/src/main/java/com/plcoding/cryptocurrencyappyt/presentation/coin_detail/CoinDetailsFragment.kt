package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.plcoding.cryptocurrencyappyt.databinding.FragmentCoinDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinDetailsFragment : Fragment() {
    private val coinDetailViewModel: CoinDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentCoinDetailsBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                coinDetailViewModel.state.collect { coinDetail ->
                    coinDetail.coinDetail.let {
                        binding.coinDetailText.text = coinDetail.coinDetail?.description
                    }
                }
            }
        }

        return binding.root
    }

}