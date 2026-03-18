package com.techypie.myyoutubeapp

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.techypie.myyoutubeapp.DatabasaHelper
import com.techypie.myyoutubeapp.Fragments.FragmentHome
import com.techypie.myyoutubeapp.Fragments.FragmentVideo
import com.techypie.myyoutubeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Global Variable
    lateinit var binding : ActivityMainBinding
    lateinit var fragementList : MutableList<Fragment>
    lateinit var databasaHelper: DatabasaHelper
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        intializeEveryThing()
    }
    fun intializeEveryThing()
    {
        databasaHelper = DatabasaHelper(this@MainActivity)
        setupViewPager()
        setupTabLayout()

        addedFirstTimeData()

    }

    fun addedFirstTimeData(){

        sharedPreferences = getSharedPreferences("MYDATA",MODE_PRIVATE)
        var isFirstrun = sharedPreferences.getBoolean("ISFIRSTRUN",true)
        if (isFirstrun){

            databasaHelper.insertVideo("Complete Git and GitHub Tutorial","Ez8F0nW6S-w","Education")
            databasaHelper.insertVideo("Grand Parents","HiuO-gUBMAM","Entertainment")
            databasaHelper.insertVideo("India vs Pakistan","ROfsF5vAApI","Sports")
            databasaHelper.insertVideo("Pubg Mobile","jO3dLSJ6YbU","Gaming")
            databasaHelper.insertVideo("Israel rains missiles on Lebanon","u-mXBUfLY3E","News")

            isFirstrun = false
            editor = sharedPreferences.edit()
            editor.putBoolean("ISFIRSTRUN",isFirstrun)
            editor.commit()
        }

    }

    fun setupViewPager() {
        fragementList = ArrayList()

        var homeFragment = FragmentHome()
        var videoFragment = FragmentVideo()

        fragementList.add(homeFragment)
        fragementList.add(videoFragment)

        var adapterFragment = FragmentAdapter(supportFragmentManager,lifecycle,fragementList)
        binding.viewPager.adapter = adapterFragment

       binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
           override fun onPageSelected(position: Int) {
               super.onPageSelected(position)
               binding.tabLayout.getTabAt(position)?.select()
           }
       })
    }
    fun setupTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.home).setText("Home"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.ic_video).setText("Video"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab) {
              binding.viewPager.currentItem = p0.position
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

        })
    }
    class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,fragmentList : MutableList<Fragment>) : FragmentStateAdapter(fragmentManager,lifecycle){

        var fragmentList : MutableList<Fragment>

        init {
            this.fragmentList = fragmentList
        }

        override fun createFragment(p0: Int): Fragment {
            return fragmentList[p0]
        }

        override fun getItemCount(): Int {
           return fragmentList.size
        }

    }

}
