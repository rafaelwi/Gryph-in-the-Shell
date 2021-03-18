package models.gui

import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.input.MouseEvents
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.geom.Point

class SwipeComponent(private val mouse: MouseEvents): Container() {

    var inSwipe = false

    init {
        addFixedUpdater(50.timesPerSecond) {
            if (inSwipe) updateSwipe(mouse.currentPosStage)
        }
    }

    fun setSwipe(isDrag: Boolean) {
        inSwipe = isDrag;
    }

    fun updateSwipe(currentPos: Point) {
        solidRect(10, 10, Colors.RED) {
            anchor(Anchor.MIDDLE_CENTER)
            position(currentPos)
        }
    }

    fun clearSwipe() {
        this.removeChildren()
    }

}