package com.app.kitaabattawheed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.app.kitaabattawheed.utils.startActivityNoAnimation

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
