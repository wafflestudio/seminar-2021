package com.wafflestudio.seminar5.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.FloatRange
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import com.wafflestudio.seminar5.R
import com.wafflestudio.seminar5.databinding.ItemRatingStarsViewBinding
import com.wafflestudio.seminar5.utils.dp
import kotlin.math.roundToInt

class RatingStarsView : LinearLayout {

    private val binding = ItemRatingStarsViewBinding.inflate(LayoutInflater.from(context), this)
    private var mOnRatingChangeListener: OnRatingChangeListener? = null
    private var dragEnabled: Boolean = false
    private val stars: List<ImageView> = listOf(
        binding.star1,
        binding.star2,
        binding.star3,
        binding.star4,
        binding.star5,
    )

    var rating: Float = 5.0f
        set(value) {
            if (field == value) return
            stars.forEachIndexed { index, view ->
                val remain = rounds(value) - index * 2
                val starResource = ResourcesCompat.getDrawable(
                    resources,
                    when {
                        remain <= 0 -> R.drawable.ic_empty_star
                        remain == 1 -> R.drawable.ic_half_star
                        else -> R.drawable.ic_full_star
                    },
                    null
                )
                view.setImageDrawable(starResource)
            }
            mOnRatingChangeListener?.onChange(value)
            field = value
        }

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

    fun setOnRatingChangeListener(listener: OnRatingChangeListener) {
        mOnRatingChangeListener = listener
    }

    fun removeOnRatingChangeListener(listener: OnRatingChangeListener) {
        if (mOnRatingChangeListener == listener) mOnRatingChangeListener = null
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (dragEnabled) {
            event?.let { v ->
                val ind = stars.indexOfFirst { view ->
                    view.x + view.width >= v.x
                }
                rating = (if (ind == -1) 5 else ind + 1).toFloat()
            }
            val action = event?.action
            action?.let {
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        this.parent.requestDisallowInterceptTouchEvent(true)
                    }
                    MotionEvent.ACTION_UP -> {
                        this.parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }

            return true
        }
        return super.onTouchEvent(event)
    }

    private fun init(attr: AttributeSet?) {
        requestDisallowInterceptTouchEvent(true)
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.RatingStarsView,
            0,
            0
        ).apply {
            try {
                val (starWidth, gap) = when (getInteger(R.styleable.RatingStarsView_starSize, 1)) {
                    0 -> 18 to 4
                    1 -> 33 to 8
                    2 -> 48 to 12
                    else -> throw IllegalStateException("Rating star size enum invalid")
                }.let { Pair(context.dp(it.first), context.dp(it.second)) }

                stars.forEachIndexed { index, view ->
                    view.updateLayoutParams<LayoutParams> {
                        width = starWidth
                        height = starWidth
                        if (index != stars.size - 1) rightMargin = gap
                    }
                }

                dragEnabled = getBoolean(R.styleable.RatingStarsView_dragEnabled, false)
            } finally {
                recycle()
            }
        }
    }

    private fun rounds(
        @FloatRange(from = 0.0, to = 5.0) value: Float
    ): Int {
        return (value * 2).roundToInt()
    }
}

interface OnRatingChangeListener {
    fun onChange(rating: Float)
}
