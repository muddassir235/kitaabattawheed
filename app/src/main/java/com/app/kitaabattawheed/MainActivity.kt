package com.app.kitaabattawheed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitaabattawheed.adapters.KitaabAdapter
import com.app.kitaabattawheed.databinding.ActivityMainBinding
import com.app.kitaabattawheed.utils.px

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: KitaabAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
                scrollY += dy

                Log.d("MainActivity: scrollY", scrollY.toString())
                val headerScroll = 400
                if(scrollY < headerScroll) {
                    var scaleFactor = (headerScroll.toFloat() - scrollY)/headerScroll.toFloat()

                    if(scaleFactor>1) {
                        scaleFactor = 1.0f
                    } else if (scaleFactor < 0) {
                        scaleFactor = 0.0f
                    }

                    val height = (50 + 27 * scaleFactor).toInt().px
                    val layoutParams = binding.titleLayout.layoutParams
                    layoutParams.height = height
                    binding.titleLayout.layoutParams = layoutParams

                    recyclerView.elevation = 2.px.toFloat() + (8.px * scaleFactor)
                    binding.titleBack.alpha       = 0.25f + 0.75f * scaleFactor
                    binding.titleTv.textSize      = 20.0f + 10 * scaleFactor
                } else {
                    val height = 50.px
                    val layoutParams = binding.titleLayout.layoutParams
                    layoutParams.height = height
                    binding.titleLayout.layoutParams = layoutParams

                    recyclerView.elevation = 2.px.toFloat()
                    binding.titleBack.alpha  = 0.25f
                    binding.titleTv.textSize = 20.0f
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.titleLayout.setOnClickListener {
            viewAdapter.resetRows()
            recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun loadDataSet() : Array<Chapter> {
        val chapterNos = resources.getStringArray(R.array.chapter_nos)
        val chapterTitles = resources.getStringArray(R.array.chapter_titles)
        val chapterContents = resources.obtainTypedArray(R.array.chapter_contents)

        val chapters = Array(chapterTitles.size) { i ->
            val rows = resources.getStringArray(chapterContents.getResourceId(i, R.array.chapter1))

            Chapter(
                chapterNo = chapterNos[i],
                title = chapterTitles[i],
                content = rows
            )
        }

        return chapters
    }
}
