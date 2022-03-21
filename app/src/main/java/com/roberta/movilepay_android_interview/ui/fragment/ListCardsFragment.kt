package com.roberta.movilepay_android_interview.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.roberta.movilepay_android_interview.adapter.CardsAdapter
import com.roberta.movilepay_android_interview.databinding.CardsListBinding
import com.roberta.movilepay_android_interview.extensions.hide
import com.roberta.movilepay_android_interview.extensions.show
import com.roberta.movilepay_android_interview.model.cardlist.Cards
import com.roberta.movilepay_android_interview.model.cardlist.CardsAction
import com.roberta.movilepay_android_interview.repository.CardResponse
import com.roberta.movilepay_android_interview.ui.viewmodel.ListCardsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CARD_SCREEN = "CARD_SCREEN"

class ListCardsFragment : Fragment() {

    lateinit var binding: CardsListBinding
    private val adapter: CardsAdapter by inject()
    private val viewModel: ListCardsViewModel by viewModel()
    private val controller by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CardsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCards()
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        binding.cardsListRecyclerview.adapter = adapter
        adapter.onItemClickListener = { it ->

            if (it.identifier == CARD_SCREEN){
                goForDetailsCard(it)

            }else{
                goForDetailsAccount(it)
            }
        }
    }

    private fun goForDetailsAccount(it: CardsAction) {
        val cardId = it.cardContent?.accountId
        cardId?.let {
            controller.navigate(ListCardsFragmentDirections.actionCardListToDetailsAccount(it))
        }
    }

    private fun goForDetailsCard(it: CardsAction) {
        val cardId = it.cardContent?.cardId
        cardId?.let {
            controller.navigate(ListCardsFragmentDirections.actionCardListToDetailsCard(it))
        }
    }

    private fun getCards() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is CardResponse.Success<Cards> -> {
                    hideProgressBar()
                    binding.cardsListRecyclerview.show()
                    binding.cardsListFailureMessage.hide()
                    adapter.submitList(it.data.cards)
                }
                is CardResponse.Failure -> {
                    hideProgressBar()
                    binding.cardsListFailureMessage.apply {
                        text = it.error
                        show()
                    }
                    binding.cardsListRecyclerview.hide()

                }
                is CardResponse.Loading -> {showProgressBar()}
            }
        }
    }

    private fun showProgressBar(){
        binding.cardsListProgressBar.show()
    }

    private fun hideProgressBar(){
        binding.cardsListProgressBar.hide()
    }
}