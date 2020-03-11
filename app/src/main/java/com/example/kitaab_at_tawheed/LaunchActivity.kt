package com.example.kitaab_at_tawheed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kitaab_at_tawheed.utils.startActivityNoAnimation

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        startActivityNoAnimation<MainActivity>();
//        finish()
    }
}
