package com.roberta.movilepay_android_interview.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.roberta.movilepay_android_interview.R
import com.roberta.movilepay_android_interview.databinding.DetailsCardBinding
import com.roberta.movilepay_android_interview.extensions.hide
import com.roberta.movilepay_android_interview.extensions.show
import com.roberta.movilepay_android_interview.model.detailscard.DetailsCard
import com.roberta.movilepay_android_interview.repository.CardResponse
import com.roberta.movilepay_android_interview.ui.viewmodel.DetailsCardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsCardFragment : Fragment() {

    lateinit var binding: DetailsCardBinding
    private val argumentos by navArgs<DetailsCardFragmentArgs>()
    private val cardId by lazy {
        argumentos.cardId
    }
    private val viewModel: DetailsCardViewModel by viewModel { parametersOf(cardId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DetailsCardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trySearchDetailsCard()
    }

    private fun trySearchDetailsCard() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is CardResponse.Success<DetailsCard> -> {
                    binding.detailsCardContainer.show()
                    binding.detailsCardsFailureMessage.hide()
                    hideProgressBar()
                    binding.detailsCardNumber.text = it.data.cardNumber
                    binding.detailsCardName.text = it.data.cardName
                    binding.detailsCardExpiration.text = it.data.expirationDate
                    binding.detailsCardTextLimit.text =
                        getString(R.string.availableLimit, it.data.availableLimit)
                    binding.detailsCardTextLimitTotal.text =
                        getString(R.string.totalLimit, it.data.totalLimit)
                }
                is CardResponse.Failure -> {
                    hideProgressBar()
                    binding.detailsCardContainer.hide()
                    binding.detailsCardsFailureMessage.apply {
                        show()
                        text = it.error
                    }
                }
                is CardResponse.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar(){
        binding.detailsCardProgressBar.show()
    }

    private fun hideProgressBar(){
        binding.detailsCardProgressBar.hide()
    }
}