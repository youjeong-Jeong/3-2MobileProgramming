package com.example.week2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup

class MyView : View {
    var rect = Rect(200, 200, 200, 200)

    var cirX = 100F
    var cirY = 100F
    var cirR = 50F

    var color = Color.BLUE
    private var paint = Paint()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var shape: String = "rect"

    fun setShape(newShape: String) {
        shape = newShape
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = color


        if(shape == "rect"){
            canvas.drawRect(rect, paint)
        }
        else if(shape == "cir"){
            canvas.drawCircle(cirX, cirY, cirR, paint)
        }


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN) {
            if(shape == "rect"){
                rect.left = event.x.toInt()
                rect.top = event.y.toInt()
                rect.right = rect.left + 100
                rect.bottom = rect.top + 100
            }

            else if(shape == "cir"){
                cirX = event.x.toInt().toFloat()
                cirY = event.y.toInt().toFloat()
            }

            performClick()
            invalidate()

            return true //꼭 해줘야 함 dk
        }
        return super.onTouchEvent(event)
    }
}