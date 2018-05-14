package com.graphics.piechart.piechart

import android.graphics.Color

/**
 * @author zhangnan
 * @date 2018/5/9
 */
object ColorCollector {

    fun randomInColor(scope: Int): Int {
        val a: Int = (scope - Math.random() * scope).toInt()
        val r: Int = (scope - Math.random() * scope).toInt()
        val g: Int = (scope - Math.random() * scope).toInt()
        val b: Int = (scope - Math.random() * scope).toInt()
        return Color.argb(a, r, g, b)
    }

    fun randomAllIntColor(): Int {
        return randomInColor(255)
    }

    fun randomDeepIntColor(): Int {
        return randomInColor(240)
    }

    private var usedColors: MutableList<Int> = arrayListOf()
    fun randomNoRepeatIntColor(): Int {
        val newIntColor = randomDeepIntColor()
        return if (!usedColors.contains(newIntColor)) {
            usedColors.add(newIntColor)
            newIntColor
        } else {
            randomNoRepeatIntColor()
        }
    }

    fun clearUsedColors() {
        usedColors.clear()
    }
}