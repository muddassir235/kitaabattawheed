package com.example.kitaab_at_tawheed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitaab_at_tawheed.adapters.KitaabAdapter
import com.example.kitaab_at_tawheed.utils.px
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = KitaabAdapter(loadDataSet())

        recyclerView = findViewById<RecyclerView>(R.id.kitaab_rv).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            var scrollY = 0f
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                var x = recyclerView.scrollX
                scrollY = scrollY + dy

                Log.d("MainActivity: scrollY", scrollY.toString())
                val headerScroll = 400
                if(scrollY < headerScroll) {
                    val height = (50 + 27.toFloat() * ((headerScroll - scrollY)/headerScroll)).toInt().px
                    val layoutparams = title_layout.layoutParams
                    layoutparams.height = height
                    title_layout.layoutParams = layoutparams

                    recyclerView.elevation = 2.px.toFloat() + (8.px * (headerScroll - scrollY)/headerScroll)
                    title_back.alpha       = 0.25f + 0.75f*((headerScroll - scrollY)/headerScroll).toFloat()
                    title_tv.textSize      = 20.toFloat() + 10.toFloat() * ((headerScroll - scrollY)/headerScroll)
                } else {
                    val height = 50.px
                    val layoutparams = title_layout.layoutParams
                    layoutparams.height = height
                    title_layout.layoutParams = layoutparams

                    recyclerView.elevation = 2.px.toFloat()
                    title_back.alpha  = 0.25f
                    title_tv.textSize = 20.toFloat()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

//        recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            Log.d("MainActivity: scrollY", scrollY.toString())
//            if(scrollY < 50) {
//                recyclerView.elevation = (12.px * (50 - scrollY)/50).toFloat()
//                title_back.alpha       = (0.05 * (50 - scrollY)/50).toFloat()
//                title_tv.textSize      = 20.toFloat() + 5.toFloat() * ((50 - scrollY)/50)
//                title_tv.typeface      = ResourcesCompat.getFont(this, R.font.tajawal_medium)
//            } else {
//                recyclerView.elevation = 0f
//                title_back.alpha  = 0f
//                title_tv.textSize = 20.toFloat()
//                title_tv.typeface = ResourcesCompat.getFont(this, R.font.tajawal_regular)
//            }
//        }
    }

    fun loadDataSet() : Array<Chapter> {
        val chapterNos = resources.getStringArray(R.array.chapter_nos)
        val chapterTitles = resources.getStringArray(R.array.chapter_titles)
        val chapterContents = resources.obtainTypedArray(R.array.chapter_contents)

        val chapters = Array<Chapter>(chapterTitles.size) { i ->
            val rows = resources.getStringArray(chapterContents.getResourceId(i, R.array.chapter1))

            Chapter(
                chapter_no = chapterNos[i],
                title = chapterTitles[i],
                content = rows
            )
        }

        return chapters
    }
}
