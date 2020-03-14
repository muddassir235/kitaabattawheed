package com.example.kitaab_at_tawheed.utils

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.kitaab_at_tawheed.R
import java.util.*
import java.util.regex.Pattern


/*
     Create clickable spans within a TextView
     made easy with pattern matching!

     Created by: Nathan Esquenazi

     Usage 1: Apply spannable strings to a TextView based on pattern

        new PatternEditableBuilder().
           addPattern(Pattern.compile("\\@(\\w+)")).
           into(textView);

     Usage 2: Apply clickable spans to a TextView

         new PatternEditableBuilder().
             addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
             new PatternEditableBuilder.SpannableClickedListener() {
                @Override
                public void onSpanClicked(String text) {
                    // Do something here
                }
             }).into(textView);

     See README for more details.
 */

/*
     Create clickable spans within a TextView
     made easy with pattern matching!

     Created by: Nathan Esquenazi

     Usage 1: Apply spannable strings to a TextView based on pattern

        new PatternEditableBuilder().
           addPattern(Pattern.compile("\\@(\\w+)")).
           into(textView);

     Usage 2: Apply clickable spans to a TextView

         new PatternEditableBuilder().
             addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
             new PatternEditableBuilder.SpannableClickedListener() {
                @Override
                public void onSpanClicked(String text) {
                    // Do something here
                }
             }).into(textView);

     See README for more details.
 */
class PatternEditableBuilder {
    // Records the pattern spans to apply to a TextView
    var patterns: ArrayList<SpannablePatternItem>
    var textView: TextView? = null
    var text: String? = null

    /* This stores a particular pattern item
       complete with pattern, span styles, and click listener */
    inner class SpannablePatternItem(
        var pattern: Pattern,
        var styles: SpannableStyleListener?,
        var listener: SpannableClickedListener?
    )

    /* This stores the style listener for a pattern item
       Used to style a particular category of spans */
    abstract class SpannableStyleListener {
        var spanTextColor = 0
        var spanFont = 0

        constructor() {}
        constructor(spanTextColor: Int, spanFont: Int) {
            this.spanTextColor = spanTextColor
            this.spanFont      = spanFont
        }

        abstract fun onSpanStyled(ds: TextPaint?)
    }

    /* This stores the click listener for a pattern item
       Used to handle clicks to a particular category of spans */
    interface SpannableClickedListener {
        fun onSpanClicked(text: String?)
    }

    /* This is the custom clickable span class used
       to handle user clicks to our pattern spans
       applying the styles and invoking click listener.
     */
    inner class StyledClickableSpan(var item: SpannablePatternItem) :
        ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            if (item.styles != null) {
                item.styles!!.onSpanStyled(ds)
            }
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            if (item.listener != null) {
                val tv = widget as TextView
                val span = tv.text as Spanned
                val start = span.getSpanStart(this)
                val end = span.getSpanEnd(this)
                val text = span.subSequence(start, end)
                item.listener!!.onSpanClicked(text.toString())
            }
            widget.invalidate()
        }

    }

    /* These are the `addPattern` overloaded signatures */ // Each allows us to add a span pattern with different arguments
    fun addPattern(
        pattern: Pattern,
        spanStyles: SpannableStyleListener?,
        listener: SpannableClickedListener?
    ): PatternEditableBuilder {
        patterns.add(SpannablePatternItem(pattern, spanStyles, listener))
        return this
    }

    fun addPattern(
        pattern: Pattern,
        spanStyles: SpannableStyleListener?
    ): PatternEditableBuilder {
        addPattern(pattern, spanStyles, null)
        return this
    }

    fun addPattern(pattern: Pattern): PatternEditableBuilder {
        addPattern(pattern, null, null)
        return this
    }

    fun addPattern(pattern: Pattern, textColor: Int): PatternEditableBuilder {
        addPattern(pattern, textColor, R.font.tajawal_regular, null)
        return this
    }

    fun addPattern(pattern: Pattern, textColor: Int, textFont: Int): PatternEditableBuilder {
        addPattern(pattern, textColor, textFont, null)
        return this
    }

    fun addPattern(
        pattern: Pattern,
        textColor: Int,
        textFont: Int,
        listener: SpannableClickedListener?
    ): PatternEditableBuilder {
        val styles: SpannableStyleListener = object : SpannableStyleListener(textColor, textFont) {
            override fun onSpanStyled(ds: TextPaint?) {
                ds?.linkColor = textView?.resources?.getColor(spanTextColor)
//                ds?.setTypeface(ResourcesCompat.getFont(textView?.context!!, spanFont))
            }
        }
        addPattern(pattern, styles, listener)
        return this
    }

    fun addPattern(
        pattern: Pattern,
        listener: SpannableClickedListener?
    ): PatternEditableBuilder {
        addPattern(pattern, null, listener)
        return this
    }

    fun put(text:String): PatternEditableBuilder {
        this.text = text
        return this
    }

    /* BUILDER METHODS */ // This builds the pattern span and applies to a TextView
    fun into(textView: TextView) {
        this.textView = textView
        val result = build(text)
        textView.text = result
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    // This builds the pattern span into a `SpannableStringBuilder`
// Requires a CharSequence to be passed in to be applied to
    fun build(editable: CharSequence?): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(editable)
        for (item in patterns) {
            val matcher = item.pattern.matcher(ssb)
            while (matcher.find()) {
                val start = matcher.start()
                val end = matcher.end()
                val url = StyledClickableSpan(item)
                ssb.setSpan(url, start, end, 0)
            }
        }
        return ssb
    }

    /* ----- Constructors ------- */
    init {
        patterns = ArrayList()
    }
}