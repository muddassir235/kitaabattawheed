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

    var contentsVisibilityArray: Array<Boolean>? = Array(dataSet.size) { false }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    inner class ChapterHolder(itemView: View) : BaseViewHolder<Chapter> (itemView) {
        private var titleLl     : LinearLayout?  = null
        private var chapterNoTv: TextView?      = null
        private var titleTv     : TextView?      = null
        private var shareIv     : ImageView?     = null
        private var contentRv   : RecyclerView?      = null

        init {
            titleLl      = itemView.findViewById(R.id.title_ll)
            chapterNoTv = itemView.findViewById(R.id.chapter_no_tv)
            titleTv      = itemView.findViewById(R.id.title_tv)
            shareIv      = itemView.findViewById(R.id.share_iv)
        }

        override fun bind(chapter: Chapter) {
            titleLl?.setOnClickListener {
                contentsVisibilityArray?.set(bindingAdapterPosition,
                    !(contentsVisibilityArray?.get(bindingAdapterPosition)?:false))
                notifyDataSetChanged()
            }
            chapterNoTv?.text = chapter.chapterNo
            titleTv?.text = chapter.title

            if(contentsVisibilityArray?.get(bindingAdapterPosition) == true) {
                val viewManager = LinearLayoutManager(itemView.context)
                val viewAdapter = ContentAdapter(chapter.content!!)

                contentRv = itemView.findViewById<RecyclerView>(R.id.content_rv)
                    .apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }

                contentRv?.visibility = View.VISIBLE
                titleTv?.typeface = ResourcesCompat.getFont(itemView.context, R.font.tajawal_bold)
                shareIv?.visibility = View.VISIBLE
            } else {
                contentRv?.visibility = View.GONE
                titleTv?.typeface = ResourcesCompat.getFont(itemView.context,
                    R.font.tajawal_medium)
                shareIv?.visibility = View.GONE
            }

            shareIv?.setOnClickListener {
                var text = ""
                text = text +
                        "كتاب التوحيد الذي هو حق الله على العبيد صنفه الشيخ محمد بن عبد الوهاب رحمه الله" + "\n\n" +
                        chapter.chapterNo + " " + chapter.title + "\n"

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
        return if(position == dataSet.size) {
            TYPE_END
        } else {
            TYPE_CHAPTER
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
        contentsVisibilityArray = Array(dataSet.size) {false}
        notifyDataSetChanged()
    }
}