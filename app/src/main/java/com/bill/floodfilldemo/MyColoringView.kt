package com.bill.floodfilldemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.os.AsyncTask
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


/**
 * author ywb
 * date 2024/7/31
 * desc
 */
class MyColoringView : View {

    private var mPath: Path? = null
    var mBitmap: Bitmap? = null
    var mScaledBitmap: Bitmap? = null
    val mPoint: Point = Point()
    var mCanvas: Canvas? = null
    var mPaint: Paint? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeJoin = Paint.Join.ROUND
        mPaint?.strokeWidth = 5f
//        mBitmap = BitmapFactory.decodeResource(
//            resources,
//            R.drawable.cartoon
//        ).copy(Bitmap.Config.ARGB_8888, true)
        this.mPath = Path()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.e("YBill", "onSizeChanged")
        if (mBitmap != null) {
            // 计算图片高度
            val aspectRatio: Float = mBitmap!!.getHeight()*1.0f / mBitmap!!.getWidth()
            val imageHeight = (w * aspectRatio).toInt()
            // 缩放Bitmap
            mScaledBitmap = Bitmap.createScaledBitmap(mBitmap!!, w, imageHeight, true)
        }
    }

    fun setBitmap(bitmap: Bitmap) {
        mBitmap = bitmap
        invalidate()
        Log.e("YBill", "setBitmap")
    }

    override fun onDraw(canvas: Canvas) {
        this.mCanvas = canvas
        mPaint?.setColor(Color.GREEN)
        if (mScaledBitmap != null) {
            canvas.drawBitmap(mScaledBitmap!!, 0f, 0f, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (mScaledBitmap != null) {
                    mPoint.x = x.toInt()
                    mPoint.y = y.toInt()
                    val sourceColor = mScaledBitmap!!.getPixel(x.toInt(), y.toInt())
                    val targetColor: Int = mPaint!!.getColor()
                    TheTask(mScaledBitmap!!, mPoint, targetColor, sourceColor).execute()
                    invalidate()
                }
            }
        }
        return true
    }

    inner class TheTask(
        private val bmp: Bitmap,
        private val pt: Point,
        private val replacementColor: Int,
        private val targetColor: Int
    ) : AsyncTask<Void, Int, Void>() {

        override fun onPreExecute() {
        }

        override fun onProgressUpdate(vararg values: Int?) {
            // No implementation needed if not used
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val filler = QueueLinearFloodFiller(bmp, targetColor, replacementColor)
            filler.setTolerance(10)
            filler.floodFill(pt.x, pt.y)
            return null
        }

        override fun onPostExecute(result: Void?) {
            invalidate()
        }
    }


}