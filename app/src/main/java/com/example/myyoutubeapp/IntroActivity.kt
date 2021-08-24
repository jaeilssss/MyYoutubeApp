package com.example.myyoutubeapp

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ScrollView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.widget.NestedScrollView

class IntroActivity : AppCompatActivity() {
    private var isGateringMotionAnimating: Boolean = false
   lateinit var france : ImageView
   lateinit var motion : MotionLayout
    private var isCurationMotionAnimating: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        var scroll  : NestedScrollView = findViewById(R.id.scroll)
        france = findViewById(R.id.france)
        motion = findViewById(R.id.motionIntro)
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
//                    binding.gatheringDigitalThingsBackgroundMotionLayout.transitionToEnd()
//                    binding.gatheringDigitalThingsMotionLayout.transitionToEnd()
//                    binding.buttonShownMotionLayout.transitionToEnd()
                    motion.transitionToEnd()

                }
            } else {
                if (isGateringMotionAnimating.not()) {
//                    binding.gatheringDigitalThingsBackgroundMotionLayout.transitionToStart()
//                    binding.gatheringDigitalThingsMotionLayout.transitionToStart()
//                    binding.buttonShownMotionLayout.transitionToStart()
                    motion.transitionToStart()

                }
            }

            if (scrolledValue > scrollView.height) {
                if (isCurationMotionAnimating.not()) {
//                    binding.curationAnimationMotionLayout.setTransition(R.id.curation_animation_start1, R.id.curation_animation_end1)
//                    binding.curationAnimationMotionLayout.transitionToEnd()
                    isCurationMotionAnimating = true
                    motion.transitionToEnd()
                }
            }
        }
    }

    fun Float.dpToPx(context: Context): Float =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)
}