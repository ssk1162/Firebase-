package com.howlstagram.testkotlinapp.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.ViewPageAdapter
import com.howlstagram.testkotlinapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Runnable

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    val sliderHandler: Handler = Handler(Looper.getMainLooper())

    val sliderRunnable =
        Runnable { binding.viewPage.currentItem = binding.viewPage.currentItem + 1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = this

        binding.viewPage.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewPage.apply {
            adapter = ViewPageAdapter(getItemList(), binding.viewPage)
            offscreenPageLimit = 1
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 3000)
                }
            })
        }

        return binding.root
    }

    // home이 호출되면 1초 뒤에 슬라이드 재시작
    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 1000)
    }

    // home에서 사라질 경우 정지
    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    private fun getItemList(): ArrayList<Int> {

        return arrayListOf<Int>(
            R.drawable.image,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6
        )
    }

}