package com.wafflestudio.seminar4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wafflestudio.seminar4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentFirst = FirstFragment()
    private val fragmentSecond = SecondFragment()

    private var isFirstFrag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, fragmentFirst).commit()

        binding.buttonChange.setOnClickListener {
            if (isFirstFrag) {
                supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, fragmentFirst).commit()
            } else {
                supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, fragmentSecond).commit()
            }
            isFirstFrag = !isFirstFrag
        }
    }
}