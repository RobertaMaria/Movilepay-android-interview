package com.roberta.movilepay_android_interview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roberta.movilepay_android_interview.CardsEnum
import com.roberta.movilepay_android_interview.R
import com.roberta.movilepay_android_interview.model.cardlist.CardsAction
import com.roberta.movilepay_android_interview.model.cardlist.CardsButton
import com.roberta.movilepay_android_interview.model.cardlist.CardsIdentifier
import com.roberta.movilepay_android_interview.model.cardlist.CardsContent

class CardsAdapter : ListAdapter<CardsIdentifier, RecyclerView.ViewHolder>(differCallback) {

    var onItemClickListener: (CardsAction) -> Unit = {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val card = getItem(position)

        when (card?.identifier) {
            CardsEnum.HOME_CARD_WIDGET.name -> {
                (holder as CardViewHolder).bind(card)
            }
            CardsEnum.HOME_HEADER_WIDGET.name -> {
                (holder as CardsHeaderViewHolder).bind(card)
            }
            CardsEnum.HOME_STATEMENT_WIDGET.name -> {
                (holder as CardsStatementViewHolder).bind(card)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return when (viewType) {
            CardsEnum.HOME_CARD_WIDGET.ordinal -> {
                val inflate = inflater.inflate(R.layout.home_cards_item, parent, false)
                CardViewHolder(inflate)
            }
            CardsEnum.HOME_HEADER_WIDGET.ordinal -> {
                val inflate = inflater.inflate(R.layout.home_cards_header_item, parent, false)
                CardsHeaderViewHolder(inflate)
            }
            CardsEnum.HOME_STATEMENT_WIDGET.ordinal -> {
                val inflate = inflater.inflate(R.layout.home_cards_statement_item, parent, false)
                CardsStatementViewHolder(inflate)
            }

            else -> {
                val inflate = inflater.inflate(R.layout.home_cards_no_mapped_item, parent, false)
                CardsNoMappedViewHolder(inflate)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.identifier?.let {
            return CardsEnum.valueOf(it).ordinal
        }
        return super.getItemViewType(position)
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var action : CardsAction
        private val cardItemButton by lazy { itemView.findViewById<Button>(R.id.home_card_item_button) }

        init {
            cardItemButton.setOnClickListener {
                if(::action.isInitialized){
                    onItemClickListener(action)
                }

            }
        }

        fun bind(card: CardsIdentifier) {
            action = card.content?.button?.action!!
            configureFields(card.content)

        }

        private fun configureFields(content: CardsContent) {
            val fieldTitle = itemView.findViewById<TextView>(R.id.home_card_item_title)
            fieldTitle.text = content.title
            val fieldNumberCard = itemView.findViewById<TextView>(R.id.home_card_item_number_card)
            fieldNumberCard.text = content.cardNumber
            val fieldButton = itemView.findViewById<Button>(R.id.home_card_item_button)
            fieldButton.text = content.button?.text
        }
    }

   inner class CardsHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(card: CardsIdentifier) {
            val fieldTitle = itemView.findViewById<TextView>(R.id.home_header_title)
            fieldTitle.text = card.content?.title
        }
    }

   inner class CardsStatementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private lateinit var action : CardsAction
       private val cardItemButton by lazy { itemView.findViewById<Button>(R.id.home_card_statement_item_button) }

       init {
           cardItemButton.setOnClickListener {
               if(::action.isInitialized){
                   onItemClickListener(action)
               }
           }
       }

        fun bind(card: CardsIdentifier) {
            action = card.content?.button?.action!!
            configureFields(card.content, card.content.button)
        }

       private fun configureFields(
           content: CardsContent,
           button: CardsButton
       ) {
           val fieldTitle = itemView.findViewById<TextView>(R.id.home_cards_statement_item_title)
           fieldTitle.text = content.title
           val fieldBalanceAvailable =
               itemView.findViewById<TextView>(R.id.home_cards_statement_item_balance_available)
           fieldBalanceAvailable.text = content.balance?.label
           val fieldBalance =
               itemView.findViewById<TextView>(R.id.home_cards_statement_item_balance)
           fieldBalance.text = content.balance?.value
           val fieldButton = itemView.findViewById<Button>(R.id.home_card_statement_item_button)
           fieldButton.text = button.text
       }
   }

    class CardsNoMappedViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)
}

val differCallback = object : DiffUtil.ItemCallback<CardsIdentifier>() {
    override fun areItemsTheSame(
        oldItem: CardsIdentifier,
        newItem: CardsIdentifier
    ): Boolean {
        return oldItem.content?.cardNumber == newItem.content?.cardNumber
    }

    override fun areContentsTheSame(
        oldItem: CardsIdentifier,
        newItem: CardsIdentifier
    ): Boolean {
        return oldItem == (newItem)
    }
}

