package com.app.kitaabattawheed.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitaabattawheed.Chapter
import com.app.kitaabattawheed.R

class KitaabAdapter(private val dataSet: Array<Chapter>) :
    RecyclerView.Adapter<KitaabAdapter.BaseViewHolder<Chapter>>() {
    companion object {
        private const val TYPE_CHAPTER = 0
        private const val TYPE_END = 1
    }

    var contentsVisiblityArray: Array<Boolean>? = Array(65) { false }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    inner class ChapterHolder(itemView: View) : BaseViewHolder<Chapter> (itemView) {
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

        override fun bind(chapter: Chapter) {
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
                title_tv?.typeface = ResourcesCompat.getFont(itemView.context, R.font.tajawal_bold)
                share_iv?.visibility = View.VISIBLE
            } else {
                content_rv?.visibility = View.GONE
                title_tv?.typeface = ResourcesCompat.getFont(itemView.context,
                    R.font.tajawal_medium)
                share_iv?.visibility = View.GONE
            }

            share_iv?.setOnClickListener {
                var text = ""
                text = text +
                        "كتاب التوحيد الذي هو حق الله على العبيد صنفه الشيخ محمد بن عبد الوهاب رحمه الله" + "\n\n" +
                        chapter.chapter_no + " " + chapter.title + "\n"

                for(row in chapter.content!!) {
                    text = text + "\n" + row
                }

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                itemView.context.startActivity(shareIntent)
            }
        }
    }

    inner class EndHolder(itemView: View) : BaseViewHolder<Chapter>(itemView){
        override fun bind(chapter: Chapter) {
            // Nothing
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Chapter> {
        return when (viewType) {
            TYPE_CHAPTER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.chapter_layout, parent, false)
                ChapterHolder(view)
            }
            TYPE_END -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.end_row, parent, false)
                EndHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == dataSet.size) {
            return TYPE_END
        } else {
            return TYPE_CHAPTER
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size+1

    override fun onBindViewHolder(holder: BaseViewHolder<Chapter>, position: Int) {
        if(holder is ChapterHolder) {
            val chapter: Chapter = dataSet[position]
            holder.bind(chapter)
        }
    }

    fun resetRows() {
        contentsVisiblityArray = Array(65) {false}
        notifyDataSetChanged()
    }
}