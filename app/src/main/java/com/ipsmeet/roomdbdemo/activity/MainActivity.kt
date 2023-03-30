package com.ipsmeet.roomdbdemo.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.graphics.toColor
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ipsmeet.roomdbdemo.R
import com.ipsmeet.roomdbdemo.adapter.TabAdapter
import com.ipsmeet.roomdbdemo.fragment.FirstFragment
import com.ipsmeet.roomdbdemo.fragment.SecondFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "RoomDB demo"
        getColor(R.color.wooden)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.viewPager, FirstFragment()).commit()
        }

        val myAdapter = TabAdapter(supportFragmentManager)
        myAdapter.addFragment(FirstFragment(), "All Users")
        myAdapter.addFragment(SecondFragment(), "Aged Users")

        viewPager.adapter = myAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

}