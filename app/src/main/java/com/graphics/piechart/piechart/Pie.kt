package com.graphics.piechart.piechart

import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import com.graphics.piechart.piechart.ColorCollector.clearUsedColors

/**
 * @author zhangnan
 * @date 2018/5/9
 */
class Pie {
    private var pieParts: MutableList<PiePart> = arrayListOf()
    private var weight: Float = 0F
    private var size: Int = 0

    constructor() : this(arrayListOf())

    constructor(pieParts: MutableList<PiePart>) {
        clearUsedColors()
        this.pieParts = pieParts
        calculateAllPiePart()
    }

    class PiePart(var lot: Float,
                  @ColorInt var color: Int = ColorCollector.randomNoRepeatIntColor()) {
        @FloatRange(from = 0.0, to = 360.0)
        internal var angle: Float = 0F
        @FloatRange(from = 0.0, to = 100.0)
        internal var percent: Float = 0F
    }

    fun addPiePart(part: PiePart) {
        weight += part.lot
        pieParts.add(part)
        calculateAngle()
        size++
    }

    fun addPiePart(index: Int, part: PiePart) {
        weight += part.lot
        pieParts.add(index, part)
        calculateAngle()
        size++
    }

    fun removePiePart(part: PiePart) {
        weight -= part.lot
        pieParts.remove(part)
        calculateAngle()
        size--
    }

    fun removePiePart(index: Int) {
        weight -= pieParts.removeAt(index).lot
        calculateAngle()
        size--
    }

    fun calculateAllPiePart() {
        pieParts.forEach({
            weight += it.lot
            size++
        })
        calculateAngle()
    }

    fun clear() {
        pieParts.clear()
        size = 0
        weight = 0F
        clearUsedColors()
    }

    fun seekPie(index: Int) = pieParts[index]

    fun allPies() = pieParts

    fun size() = size

    fun weight() = weight

    fun notifyDataChange() {
        size = 0
        weight = 0F
        calculateAllPiePart()
    }

    private fun calculateAngle() {
        pieParts.forEach({
            it.percent = it.lot / weight
            it.angle = it.percent * 360
        })
    }
}
