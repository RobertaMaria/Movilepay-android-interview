package com.roberta.movilepay_android_interview.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.roberta.movilepay_android_interview.adapter.TransactionAdapter
import com.roberta.movilepay_android_interview.databinding.DetailsCardsAccountBinding
import com.roberta.movilepay_android_interview.extensions.hide
import com.roberta.movilepay_android_interview.extensions.show
import com.roberta.movilepay_android_interview.model.detailaccount.DetailsAccount
import com.roberta.movilepay_android_interview.repository.CardResponse
import com.roberta.movilepay_android_interview.ui.viewmodel.DetailsAccountViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsAccountFragment: Fragment() {

    lateinit var binding: DetailsCardsAccountBinding
    private val argumentos by navArgs<DetailsAccountFragmentArgs>()
    private val cardId by lazy {
        argumentos.cardId
    }
    private val viewModel: DetailsAccountViewModel by viewModel { parametersOf(cardId) }
    private val adapter: TransactionAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DetailsCardsAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        trySearchDetailsAccount()
        configureToolbar()
    }

    private fun trySearchDetailsAccount() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is CardResponse.Success<DetailsAccount> -> {
                    hideProgressBar()
                    binding.detailsCardsAccountContainer.show()
                    binding.detailsCardsAccountFailureMessage.hide()

                    it.data.let { detailsAccount ->
                        adapter.submitList(detailsAccount.transactions)
                        binding.detailsCardsAccountBalanceValue.text = detailsAccount.balance.value
                        binding.detailsCardsAccountTextBalance.text = detailsAccount.balance.label
                    }
                }
                is CardResponse.Failure -> {
                    hideProgressBar()
                    binding.detailsCardsAccountContainer.hide()
                    binding.detailsCardsAccountFailureMessage.apply {
                        text = it.error
                        show()
                    }

                }
                is CardResponse.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun configureRecyclerView() {
        binding.detailsCardsAccountRecyclerView.adapter = adapter
    }

    private fun showProgressBar(){
        binding.detailsCardsAccountProgressBar.show()
    }

    private fun hideProgressBar(){
        binding.detailsCardsAccountProgressBar.hide()
    }

    private fun configureToolbar() {
        val detailsCardsAccountToolbar = binding.detailsCardsAccountToolbar
        if (activity is AppCompatActivity?) {
            (activity as AppCompatActivity?)?.let {
                it.setSupportActionBar(detailsCardsAccountToolbar)
                it.supportActionBar?.let { actionBar ->
                    actionBar.setDisplayShowTitleEnabled(true)
                    actionBar.setDisplayHomeAsUpEnabled(true)
                    detailsCardsAccountToolbar.setNavigationOnClickListener {
                        activity?.onBackPressed()
                    }
                }
            }
        }
    }
}