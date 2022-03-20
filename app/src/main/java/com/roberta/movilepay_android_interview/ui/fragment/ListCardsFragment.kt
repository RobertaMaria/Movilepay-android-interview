package com.roberta.movilepay_android_interview.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.roberta.movilepay_android_interview.adapter.CardsAdapter
import com.roberta.movilepay_android_interview.databinding.CardsListBinding
import com.roberta.movilepay_android_interview.repository.CardResponse
import com.roberta.movilepay_android_interview.ui.viewmodel.ListCardsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCardsFragment : Fragment() {

    lateinit var binding: CardsListBinding
    private val adapter: CardsAdapter by inject()
    private val viewModel: ListCardsViewModel by viewModel()
    private val controller by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CardsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCards()

        binding.cardsListRecyclerview.adapter = adapter
        adapter.onItemClickListener = {
            if (it.identifier == "CARD_SCREEN"){
                val direction = ListCardsFragmentDirections.actionCardListToDetailscard()
                controller.navigate(direction)
            }else{
                controller.navigate(ListCardsFragmentDirections.actionCardListToDetailsaccount())
            }

        }

    }

    fun getCards() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is CardResponse.Success -> {
                    Log.i("ListCardsFragment", "getCards: ${it.cards.cards.toString()}")
                    adapter.differ.submitList(it.cards.cards)
                }
                is CardResponse.Failure -> {}
                is CardResponse.Loading -> {}
            }
        }
    }
}