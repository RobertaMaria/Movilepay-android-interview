package com.roberta.movilepay_android_interview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roberta.movilepay_android_interview.CardsEnum
import com.roberta.movilepay_android_interview.R
import com.roberta.movilepay_android_interview.model.CardsAction
import com.roberta.movilepay_android_interview.model.CardsIdentifier

class CardsAdapter : PagingDataAdapter<CardsIdentifier, RecyclerView.ViewHolder>(differCallback) {

    var onItemClickListener: (CardsAction) -> Unit = {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val card = differ.currentList[position]

        when (card?.identifier) {
            CardsEnum.HOME_CARDS_ITEM.name -> {
                (holder as CartoesCardViewHolder).bind(card)
            }
            CardsEnum.HOME_CARDS_HEADER_ITEM.name -> {
                (holder as CardsHeaderViewHolder).bind(card)
            }
            CardsEnum.HOME_CARDS_STATEMENT_ITEM.name -> {
                (holder as CardsStatementViewHolder).bind(card)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return when (viewType) {
            CardsEnum.HOME_CARDS_ITEM.ordinal -> {
                val inflate = inflater.inflate(R.layout.home_cards_item, parent, false)
                CartoesCardViewHolder(inflate)
            }
            CardsEnum.HOME_CARDS_HEADER_ITEM.ordinal -> {
                val inflate = inflater.inflate(R.layout.home_cards_header_item, parent, false)
                CardsHeaderViewHolder(inflate)
            }
            CardsEnum.HOME_CARDS_STATEMENT_ITEM.ordinal -> {
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

    val differ = AsyncListDiffer(this, differCallback)

    inner class CartoesCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
            val fieldTitle = itemView.findViewById<TextView>(R.id.home_card_item_title)
            fieldTitle.text = card.content.title
            val fieldNumberCard = itemView.findViewById<TextView>(R.id.home_card_item_number_card)
            fieldNumberCard.text = card.content.cardNumber

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
            val fieldTitle = itemView.findViewById<TextView>(R.id.home_cards_statement_item_title)
            fieldTitle.text = card.content.title
            //itemView.findViewById<TextView>(R.id.home_s)
        }
    }

    class CardsNoMappedViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(card: CardsIdentifier) {

        }
    }
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
        return oldItem.equals(newItem)
    }

}

