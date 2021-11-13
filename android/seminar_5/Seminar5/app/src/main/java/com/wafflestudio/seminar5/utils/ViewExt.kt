package com.wafflestudio.seminar5.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.visibleOrGone(flag: Boolean) {
    visibility = if (flag) View.VISIBLE else View.GONE
}

fun View.getInflater(): LayoutInflater {
    return LayoutInflater.from(context)
}

fun Context.dp(dp: Int): Int {
    return dp * resources.displayMetrics.density.toInt()
}

fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.requireContext(), message, duration).show()
}
