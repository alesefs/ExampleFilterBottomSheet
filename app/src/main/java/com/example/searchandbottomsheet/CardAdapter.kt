package com.example.searchandbottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(
    private var cards: List<CardModel>,
    private val onClickListener: (cardClick: CardModel) -> Unit
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    class ViewHolder(
        private val itemView: View,
        private val onClickListener: (cardClick: CardModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var cardView: CardView = itemView.findViewById(R.id.cardview_container)
        private var cardTag: AppCompatTextView = itemView.findViewById(R.id.cardview_tag)
        private var cardImage: AppCompatImageView = itemView.findViewById(R.id.cardview_image)
        private var cardTitle: AppCompatTextView = itemView.findViewById(R.id.cardview_text_title)
        private var cardBody: AppCompatTextView = itemView.findViewById(R.id.cardview_text_body)
        private var cardAction: AppCompatTextView = itemView.findViewById(R.id.cardview_text_action)

        fun bindView(card: CardModel) {
            cardTag.text = card.tag
            cardTitle.text = card.title
            cardBody.text = card.body
            cardAction.text = card.action
            cardImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, card.image))
            cardView.setOnClickListener {
                onClickListener.invoke(card)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_view, parent, false)

        return ViewHolder(itemView, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(cards[position])
    }

    override fun getItemCount() = cards.size

    fun setFilteredList(cardFilteredList: MutableList<CardModel>) {
        this.cards = cardFilteredList
        notifyDataSetChanged()
    }

}
