package com.graphics.piechart.piechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @author zhangnan
 * @date 2018/5/8
 */
class PieChartView : View {

    private val drawHelpers: MutableList<OnDrawSomethingByPiePartHelper> = mutableListOf()

    // 绘制扇形的工具
    private val paint: Paint by lazy { Paint() }
    private val rectF: RectF by lazy { RectF() }

    var pie: Pie = Pie()
        set(value) {
            field = value
            notifyPieChange()
        }

    private var drawToolInitialized = false

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 初始化外部绘制工具
        if (!drawToolInitialized) {
            drawHelpers.forEach({ it.initDrawTools() })
            drawToolInitialized = true
        }
        val radius = Math.min(width, height) / 2F
        rectF.set(0F, 0F, 2 * radius, 2 * radius)
        var startAngle = 0F
        var endAngle = 0F
        pie.allPies().forEach({ piePart ->
            endAngle += piePart.angle
            // 绘制扇形
            paint.color = piePart.color
            canvas.drawArc(rectF, startAngle, piePart.angle, true, paint)
            // 基于扇形绘制其他
            drawHelpers.forEach({ it.drawSomething(canvas, piePart, radius, startAngle, endAngle) })
            // 重置下一次起始角度
            startAngle += piePart.angle
        })
    }

    fun notifyPieChange() {
        pie.notifyDataChange()
        invalidate()
    }

    interface OnDrawSomethingByPiePartHelper {

        /**
         * 初始化画图工具
         */
        fun initDrawTools()

        /**
         * 绘制
         *
         * @param canvas 画布
         * @param piePart 扇形
         * @param radius 扇形半径
         * @param startAngle 起始角度
         * @param endAngle 结束角度
         */
        fun drawSomething(canvas: Canvas, piePart: Pie.PiePart, radius: Float, startAngle: Float, endAngle: Float)
    }

    fun addDrawHelper(drawHelper: OnDrawSomethingByPiePartHelper) {
        drawHelpers.add(drawHelper)
    }

    fun removeDrawHelper(drawHelper: OnDrawSomethingByPiePartHelper) {
        drawHelpers.remove(drawHelper)
    }
}