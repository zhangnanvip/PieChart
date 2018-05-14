package com.graphics.piechart.piechart

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import java.text.DecimalFormat

/**
 * @author zhangnan
 * @date 2018/5/14
 */
class PiePercentPainter : PieChartView.OnDrawSomethingByPiePartHelper {

    // 绘制扇形角度百分比文字的工具
    private val textPaint: Paint by lazy { Paint() }
    private val textRect: Rect by lazy { Rect() }

    private val percentFormat = DecimalFormat("#.00")

    override fun initDrawTools() {
        textPaint.isAntiAlias = true
        textPaint.color = Color.BLACK
        textPaint.style = Paint.Style.STROKE
        textPaint.textSize = 30F
    }

    override fun drawSomething(canvas: Canvas, piePart: Pie.PiePart, radius: Float, startAngle: Float, endAngle: Float) {
        // 计算绘制文字坐标
        val radian = (piePart.angle.toDouble() + startAngle * 2) / 2 * Math.PI / 180
        val xFromCenter = Math.cos(radian) * radius / 2
        val yFromCenter = Math.sin(radian) * radius / 2
        var realX = 0.0
        var realY = 0.0
        val referenceAngle = obtainReferenceAngle(startAngle, endAngle)
        when (referenceAngle) {
            in 0..89 -> {
                realX = radius + Math.abs(xFromCenter)
                realY = radius + Math.abs(yFromCenter)
            }
            in 90..179 -> {
                realX = radius - Math.abs(xFromCenter)
                realY = radius + Math.abs(yFromCenter)
            }
            in 180..269 -> {
                realX = radius - Math.abs(xFromCenter)
                realY = radius - Math.abs(yFromCenter)
            }
            in 270..359 -> {
                realX = radius + Math.abs(xFromCenter)
                realY = radius - Math.abs(yFromCenter)
            }
        }
        val percent = percentFormat.format(piePart.percent * 100) + "%"
        // 绘制文字(加入文字本身宽高)
        textPaint.getTextBounds(percent, 0, percent.length, textRect)
        canvas.drawText(percent, realX.toFloat() - textRect.width() / 2, realY.toFloat() + textRect.height() / 2, textPaint)
    }

    /**
     * 获取参考角度 (将圆分为四个直角扇形,所要绘制扇形占哪个直角扇形面积多绘制到哪边)
     *
     * @param startAngle 起始角度
     * @param endAngle 结束角度
     */
    private fun obtainReferenceAngle(startAngle: Float, endAngle: Float): Float {
        var referenceAngle = startAngle // 参考角度默认为起始角度
        var needSpecialHandling = false // 角度是否需要特殊处理
        var specialIndex = 0 // 在第几个直接扇形产生特殊角度
        val maxSpacialNum = 4 // 最大值4个直角扇形
        while (specialIndex < maxSpacialNum) {
            if ((endAngle - specialIndex * 90) > 0 && (startAngle - specialIndex * 90) < 0) {
                needSpecialHandling = true
                break
            }
            specialIndex++
        }
        if (needSpecialHandling) {
            if ((endAngle - specialIndex * 90) > (specialIndex * 90 - startAngle)) {
                referenceAngle += (endAngle - startAngle)
            } else {
                referenceAngle -= (endAngle - startAngle)
            }
        }
        return referenceAngle
    }
}