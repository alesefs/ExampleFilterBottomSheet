package com.example.searchandbottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TagAdapter(
    private var tags: List<TagModel>,
    private val onClickListener: (tagClick: TagModel) -> Unit
) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    class ViewHolder(
        private val itemView: View,
        private val onClickListener: (tagClick: TagModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        var tagView: ConstraintLayout = itemView.findViewById(R.id.tagview_container)
        private var tagText: AppCompatTextView = itemView.findViewById(R.id.tagview_text)

        fun bindView(tag: TagModel) {
            tagText.text = tag.name

            tagView.isActivated = tag.active
            tagView.background = getTagDrawable()

            tagView.setOnClickListener {
                tagView.isActivated = !tagView.isActivated
                tag.active = tagView.isActivated
                tagView.background = getTagDrawable()
                onClickListener.invoke(tag)
            }
        }

        private fun getTagDrawable() = ContextCompat.getDrawable(itemView.context, R.drawable.background_tag_action)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag_view, parent, false)

        return ViewHolder(itemView, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(tags[position])
    }

    override fun getItemCount() = tags.size
}
