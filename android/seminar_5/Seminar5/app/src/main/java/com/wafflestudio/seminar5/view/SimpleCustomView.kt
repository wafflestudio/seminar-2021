package com.wafflestudio.seminar5.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toDrawable
import com.wafflestudio.seminar5.R
import com.wafflestudio.seminar5.databinding.ItemSimpleCustomViewBinding
import com.wafflestudio.seminar5.utils.showToast
import kotlin.math.absoluteValue

class SimpleCustomView : LinearLayout {

    private val binding = ItemSimpleCustomViewBinding.inflate(LayoutInflater.from(context), this)

    private val map = arrayOf(
        arrayOf(binding.view00, binding.view01, binding.view02),
        arrayOf(binding.view10, binding.view11, binding.view12),
        arrayOf(binding.view20, binding.view21, binding.view22)
    )

    private val colorMap = arrayOf(
        arrayOf(WHITE, WHITE, WHITE),
        arrayOf(WHITE, WHITE, WHITE),
        arrayOf(WHITE, WHITE, WHITE)
    )
    
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        init(attributeSet)
    }

    private fun init(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SimpleCustomView,
            0,
            0
        ).apply {
            try {
                val startColorInt = getInteger(R.styleable.SimpleCustomView_startColor, 0)
                var startColor = resources.getColor(R.color.white, null)
                when (startColorInt) {
                    0 -> startColor = resources.getColor(R.color.white, null)
                    1 -> startColor = resources.getColor(R.color.black, null)
                }

                context.showToast("startColor $startColorInt")

                map.forEach { line ->
                    line.forEach { view ->
                        view.background = startColor.toDrawable()
                    }
                }
                for (i in 0..2) {
                    for (j in 0..2) {
                        colorMap[i][j] = startColorInt
                    }
                }

            } finally {
                recycle()
            }
        }

        map.forEachIndexed { i, line ->
            line.forEachIndexed { j, view ->
                view.setOnClickListener {
                    for (a in -1..1) {
                        for (b in -1..1) {
                            val x = i+a
                            val y = j+b
                            if (a.absoluteValue + b.absoluteValue >= 2) continue
                            if (x > 2 || x < 0 || y > 2 || y < 0) continue

                            flipColor(x, y)
                        }
                    }
                }
            }
        }
    }

    private fun flipColor(x: Int, y: Int) {
        val whiteDrawable = resources.getColor(R.color.white, null).toDrawable()
        val blackDrawable = resources.getColor(R.color.black, null).toDrawable()
        when (colorMap[x][y]) {
            WHITE -> {
                map[x][y].background = blackDrawable
                colorMap[x][y] = BLACK
            }
            BLACK -> {
                map[x][y].background = whiteDrawable
                colorMap[x][y] = WHITE
            }
        }
    }

    companion object {
        private const val WHITE = 0
        private const val BLACK = 1
    }

}