package com.graphics.piechart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.graphics.piechart.piechart.Pie
import com.graphics.piechart.piechart.PiePercentPainter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pcv.addDrawHelper(PiePercentPainter())
        val pies: MutableList<Pie.PiePart> = mutableListOf()
        pies.add(Pie.PiePart(1F))
        pies.add(Pie.PiePart(2F))
        pies.add(Pie.PiePart(3F))
        pies.add(Pie.PiePart(3F))
        pies.add(Pie.PiePart(3F))
        pies.add(Pie.PiePart(3F))
        pies.add(Pie.PiePart(3F))
        pies.add(Pie.PiePart(4F))
        pies.add(Pie.PiePart(7F))
        pies.add(Pie.PiePart(3F))
        pcv.pie = Pie(pies)
        pies.add(Pie.PiePart(9F))
        pies.add(Pie.PiePart(3F))
        pies.add(Pie.PiePart(3F))
        pcv.notifyPieChange()

    }
}
