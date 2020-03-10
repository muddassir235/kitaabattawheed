package com.example.kitaab_at_tawheed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitaab_at_tawheed.Chapter
import com.example.kitaab_at_tawheed.R
import com.example.kitaab_at_tawheed.utils.PatternEditableBuilder
import java.util.regex.Pattern

class KitaabAdapter(private val dataSet: Array<Chapter>) :
    RecyclerView.Adapter<KitaabAdapter.ChapterHolder>() {

    var contentsVisiblityArray: Array<Boolean>? = Array(65) { false }

    inner class ChapterHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.chapter_layout, parent, false)) {
        private var title_ll     : LinearLayout?  = null
        private var chapter_no_tv: TextView?      = null
        private var title_tv     : TextView?      = null
        private var share_iv     : ImageView?     = null
        private var content_rv   : RecyclerView?      = null

        init {
            title_ll      = itemView.findViewById(R.id.title_ll)
            chapter_no_tv = itemView.findViewById(R.id.chapter_no_tv)
            title_tv      = itemView.findViewById(R.id.title_tv)
            share_iv      = itemView.findViewById(R.id.share_iv)
        }

        fun bind(chapter: Chapter) {
            title_ll?.setOnClickListener {
                contentsVisiblityArray?.set(adapterPosition,
                    !(contentsVisiblityArray?.get(adapterPosition)?:false))
                notifyDataSetChanged()
            }
            chapter_no_tv?.text = chapter.chapter_no
            title_tv?.text = chapter.title

            if(contentsVisiblityArray?.get(adapterPosition)?:false) {
                val viewManager = LinearLayoutManager(itemView.context)
                val viewAdapter = ContentAdapter(chapter.content!!)

                content_rv = itemView.findViewById<RecyclerView>(R.id.content_rv)
                    .apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }

                content_rv?.visibility = View.VISIBLE
                title_tv?.typeface = ResourcesCompat.getFont(itemView.context, R.font.tajawal_medium)
            } else {
                content_rv?.visibility = View.GONE
                title_tv?.typeface = ResourcesCompat.getFont(itemView.context,
                    R.font.tajawal_regular)
            }
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): KitaabAdapter.ChapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ChapterHolder(inflater, parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val chapter: Chapter = dataSet[position]
        holder.bind(chapter)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}