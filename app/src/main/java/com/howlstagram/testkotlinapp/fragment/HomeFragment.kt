package com.howlstagram.testkotlinapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.howlstagram.testkotlinapp.ItemListAdapter
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.ViewPageAdapter
import com.howlstagram.testkotlinapp.databinding.FragmentHomeBinding
import com.howlstagram.testkotlinapp.ItemActivity
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

        // apply 객체의 초기화 블록을 간결하게 표현할 수 있는 함수
        // 객체의 메소드를 호출하고 객체 자체를 반환
        // binding.viewPage를 반복해서 쓰지 않고 여러 메소드를 호출할 수 있어 코드를 간결하게 만들 수 있음
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

        binding.itemBtn.setOnClickListener {
            startActivity(Intent(activity, ItemActivity::class.java))
        }


        binding.recyclerLayout.adapter = ItemListAdapter()
        binding.recyclerLayout.layoutManager = GridLayoutManager(activity, 3)

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