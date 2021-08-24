package com.example.myyoutubeapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.widget.NestedScrollView

class IntroActivity : AppCompatActivity() {
    private var isGateringMotionAnimating: Boolean = false
   lateinit var france : ImageView
   lateinit var motion : MotionLayout
   lateinit var button : Button
    private var isCurationMotionAnimating: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        var scroll  : NestedScrollView = findViewById(R.id.scroll)
        france = findViewById(R.id.france)
        motion = findViewById(R.id.motionIntro)
        button = findViewById(R.id.startButton)
        button.setOnClickListener {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        initScrollViewListeners(scroll)
        makeStatusBarTransparent()
    }



    fun Activity.makeStatusBarTransparent() {
        with(window) {
            decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }
    }


    private fun initScrollViewListeners(scrollView : NestedScrollView) {
       scrollView.smoothScrollTo(0, 0)

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrolledValue =scrollView.scrollY

            if (scrolledValue > 150f.dpToPx(this).toInt()) {
                if (isGateringMotionAnimating.not()) {
                    motion.transitionToEnd()

                }
            } else {
                if (isGateringMotionAnimating.not()) {
                    motion.transitionToStart()

                }
            }

            if (scrolledValue > scrollView.height) {
                if (isCurationMotionAnimating.not()) {
                    isCurationMotionAnimating = true
                    motion.transitionToEnd()
                }
            }
        }
    }

    fun Float.dpToPx(context: Context): Float =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)
}