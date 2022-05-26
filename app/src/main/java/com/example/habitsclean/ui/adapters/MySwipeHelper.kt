package com.example.habitsclean.ui.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class MySwipeHelper(

    private val context: Context,
    private val recyclerView: RecyclerView,
    private var buttonWidth: Int

) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private var buttonsList: List<MyButton> = emptyList()
    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent?): Boolean {

            for (button in buttonsList!!) {

                if (button.onClick(e!!.x, e.y))  break
            }
            return true
        }
    }
    private val gestureDetector = GestureDetector(context, gestureListener)
    private var swipePosition = -1
    private var swipeThreshold = 0.5f
    private var buttonBuffer: MutableMap<Int, MutableList<MyButton>> = mutableMapOf()

    private val onTouchListener = object : View.OnTouchListener {
        override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
            if (swipePosition < 0) return false

            val point = Point(p1!!.rawX.toInt(), p1.rawY.toInt())

            val swipeViewHolder = recyclerView!!.findViewHolderForLayoutPosition(swipePosition)
            val swipedItem = swipeViewHolder!!.itemView
            val rect = Rect()
            swipedItem.getGlobalVisibleRect(rect)

            if (p1.action == MotionEvent.ACTION_DOWN ||
                p1.action == MotionEvent.ACTION_UP ||
                p1.action == MotionEvent.ACTION_HOVER_MOVE
            ) {
                if (rect.top + 420 < point.y && rect.bottom + 420 > point.y) gestureDetector!!.onTouchEvent(p1)
                else {

                    removerQueue!!.add(swipePosition)
                    swipePosition = -1
                    recoverSwipedItem()
                }
            }
            return false
        }
    }

    private var removerQueue: Queue<Int> = MyLinkedList()

    init {

        recyclerView.setOnTouchListener(onTouchListener)
        attachSwipe()
    }

    fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun recoverSwipedItem() {
        while (!removerQueue!!.isEmpty()) {

            val pos = removerQueue!!.poll()
            if (pos > -1) recyclerView!!.adapter!!.notifyItemChanged(pos)
        }
    }

    data class MyButton(
        val text: String,
        val imageResId: Int,
        val color: Int,
        val textSize: Int,
        val listener: ButtonClickListener,
        val context: Context
    ) {

        private var pos: Int = -1
        private var clickRegion: RectF? = null

        val resources: Resources = context.resources

        fun onClick(x: Float, y: Float): Boolean {

            if (clickRegion!!.contains(x, y)) {

                listener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(c: Canvas, rectF: RectF, pos: Int) {

            val p = Paint()
            p.color = color
            c.drawRect(rectF, p)
            //text
            p.color = Color.WHITE
            p.textSize = textSize.toFloat()

            val r = Rect()
            val cHeight = rectF.height()
            val cWidth = rectF.width()
            p.textAlign = Paint.Align.LEFT
            p.getTextBounds(text, 0, text.length, r)
            val x: Float
            val y: Float
            if (imageResId == 0) {

                x = cWidth/2f - r.width()/2f - r.left
                y = cHeight/2f + r.height()/2f - r.bottom
                c.drawText(text, rectF.left+x, rectF.top+y, p)
            } else {
                val d = ContextCompat.getDrawable(context, imageResId)!!
                val bitMap = d.toBitmap(d.intrinsicWidth,
                                        d.intrinsicHeight, Bitmap.Config.ARGB_8888)
                c.drawBitmap(bitMap, (rectF.left+rectF.right)/2, (rectF.top+rectF.bottom)/2, p)
            }

            clickRegion = rectF
            this.pos = pos
        }

        fun drawableToBitmap(d: Drawable): Bitmap {

            if (d is BitmapDrawable) return  d.bitmap
            val bitmap = Bitmap.createBitmap(d.intrinsicWidth,
                                             d.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            d.setBounds(0,0,canvas.width , canvas.height )
            d.draw(canvas)
            return bitmap
        }
    }

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        if (swipePosition != pos)
            removerQueue.add(swipePosition)
        swipePosition = pos
        if (buttonBuffer.containsKey(swipePosition))
            buttonsList = buttonBuffer.get(swipePosition)!!
        else
            buttonsList = emptyList()
        buttonBuffer = mutableMapOf()
        swipeThreshold = 0.5f*buttonsList.size*buttonWidth
        recoverSwipedItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(c: Canvas,
                             recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float,
                             dY: Float,
                             actionState: Int,
                             isCurrentlyActive: Boolean) {

        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0) {
            swipePosition = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX< 0) {
                var buffer = mutableListOf<MyButton>()
                if (!buttonBuffer.containsKey(pos)) {

                    instantiateMyButtons(viewHolder, buffer)
                    buttonBuffer.put(pos, buffer)
                } else {
                    buffer = buttonBuffer[pos]!!
                }
                translationX = dX*buffer.size* buttonWidth/itemView.width
                drawButton(c,itemView,buffer,pos,translationX)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }

    private fun drawButton(c: Canvas,
                           itemView: View,
                           buffer: List<MyButton>,
                           pos: Int,
                           translationX: Float) {
        var right = itemView.right.toFloat()
        val dButtonWidth = -1*translationX/ buffer.size
        for (button in buffer) {

            val left = right - dButtonWidth
            button.onDraw(c, RectF(left, itemView.top.toFloat(), right, itemView.bottom.toFloat()), pos)
            right = left
        }
    }

    abstract fun instantiateMyButtons(viewHolder: RecyclerView.ViewHolder, buffer: MutableList<MyButton>)
}

class MyLinkedList: LinkedList<Int>() {

    override fun add(element: Int): Boolean {
        return if (contains(element)) false
        else super.add(element)
    }
}

