package com.example.kitaab_at_tawheed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kitaab_at_tawheed.utils.startActivityNoAnimation

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        Handler().postDelayed({
            startActivityNoAnimation<MainActivity>();
            finish()
        }, 3000)
    }
}
