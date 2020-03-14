package com.example.kitaab_at_tawheed.adapters

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
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
//            row_tv?.text = row

            var patterns = arrayOf(
                Pattern.compile("«([^/»(*)«/]*)»"),
                Pattern.compile("﴿([^/﴾(*)﴿/]*)﴾"),
                Pattern.compile("رضي الله عنه"),
                Pattern.compile("رضي الله عنها"),
                Pattern.compile("رضي الله عنهما"),
                Pattern.compile("رضي الله عنهما"),
                Pattern.compile("✦")
            )

            var colors = arrayOf(
                itemView.resources.getColor(R.color.colorGreen),
                itemView.resources.getColor(R.color.colorBlue),
                itemView.resources.getColor(R.color.colorGrey),
                itemView.resources.getColor(R.color.colorGrey),
                itemView.resources.getColor(R.color.colorGrey),
                itemView.resources.getColor(R.color.colorGrey),
                itemView.resources.getColor(R.color.colorLightGrey)
            )

            row_tv?.text = buildText(row_tv!!, row, patterns = patterns, colors = colors)

//            PatternEditableBuilder()
//                .addPattern(
//                    Pattern.compile("•"),
//                    R.color.colorBlack,
//                    R.font.tajawal_regular
//                )
//                .addPattern(
//                    Pattern.compile("«([^/»(*)«/]*)»"),
//                    R.color.colorGreen,
//                    R.font.tajawal_regular
//                )
//                .addPattern(
//                    Pattern.compile("﴿([^/﴾(*)﴿/]*)﴾"),
//                    R.color.colorBlue,
//                    R.font.tajawal_regular
//                )
//                .addPattern(
//                    Pattern.compile("رضي الله عنه"),
//                    R.color.colorGrey,
//                    R.font.tajawal_regular
//                )
//                .addPattern(
//                    Pattern.compile("رضي الله عنها"),
//                    R.color.colorGrey,
//                    R.font.tajawal_regular
//                )
//                .addPattern(
//                    Pattern.compile("رضي الله عنهما"),
//                    R.color.colorGrey,
//                    R.font.tajawal_regular
//                ).put(row).into(row_tv!!)
        }

        fun buildText(textView: TextView, editable: CharSequence, patterns: Array<Pattern>, colors: Array<Int>):
                SpannableStringBuilder {
            val ssb = SpannableStringBuilder(editable)
            for (i in 0 until patterns.size) {
                val matcher = patterns.get(i).matcher(ssb)
                while (matcher.find()) {
                    val start = matcher.start()
                    val end = matcher.end()
                    ssb.setSpan(ForegroundColorSpan(colors.get(i)),
                        start, end, 0)
                }
            }
            return ssb
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