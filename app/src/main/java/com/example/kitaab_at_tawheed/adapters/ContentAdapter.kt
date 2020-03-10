package com.example.kitaab_at_tawheed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kitaab_at_tawheed.R
import com.example.kitaab_at_tawheed.utils.PatternEditableBuilder
import java.util.regex.Pattern

class ContentAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<ContentAdapter.RowHolder>() {

    class RowHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.content_row_layout, parent, false)) {
        private var row_tv : TextView?      = null

        init {
            row_tv = itemView.findViewById(R.id.content_row_tv);
        }

        fun bind(row: String) {
            row_tv?.text = row

            PatternEditableBuilder()
                .addPattern(
                    Pattern.compile("(.*)"),
                    R.color.colorBlack,
                    R.font.tajawal_regular
                )
                .addPattern(
                    Pattern.compile("•"),
                    R.color.colorBlack,
                    R.font.tajawal_black
                )
                .addPattern(
                    Pattern.compile("«([^/»(*)«/]*)»"),
                    R.color.colorGreen,
                    R.font.tajawal_bold
                )
                .addPattern(
                    Pattern.compile("﴿([^/﴾(*)﴿/]*)﴾"),
                    R.color.colorBlue,
                    R.font.tajawal_bold
                )
                .addPattern(
                    Pattern.compile("رضي الله عنه"),
                    R.color.colorGrey,
                    R.font.tajawal_light
                )
                .addPattern(
                    Pattern.compile("رضي الله عنها"),
                    R.color.colorGrey,
                    R.font.tajawal_light
                )
                .addPattern(
                    Pattern.compile("رضي الله عنهما"),
                    R.color.colorGrey,
                    R.font.tajawal_light
                ).put(row).into(row_tv!!)
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ContentAdapter.RowHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RowHolder(inflater, parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val row: String = dataSet[position]
        holder.bind(row)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}