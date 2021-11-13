package com.wafflestudio.seminar5

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import com.wafflestudio.seminar5.databinding.ActivityMainBinding
import com.wafflestudio.seminar5.view.OnRatingChangeListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var count = 0
    private var rating = 0F

    private val sharedPreferences by lazy {
        getSharedPreferences(BuildConfig.PREF_KEY, MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        count = sharedPreferences.getInt(COUNT_KEY, 0)
        binding.textCount.text = count.toString()

        rating = sharedPreferences.getFloat(RATING_KEY, 0F)
        binding.textRating.text = rating.toString()
        binding.ratingStar.rating = rating


        binding.buttonIncrease.setOnClickListener {
            count += 1
            sharedPreferences.edit {
                putInt(COUNT_KEY, count)
            }
            binding.textCount.text = count.toString()
        }

        binding.ratingStar.setOnRatingChangeListener(object: OnRatingChangeListener {
            override fun onChange(rating: Float) {
                sharedPreferences.edit {
                    putFloat(RATING_KEY, rating)
                }
                binding.textRating.text = rating.toString()
            }
        })
    }

    companion object {
        private const val COUNT_KEY = "count_key"
        private const val RATING_KEY = "rating_key"
    }
}