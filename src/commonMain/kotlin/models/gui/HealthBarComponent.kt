package models.gui

import com.soywiz.klock.timesPerSecond
import com.soywiz.klogger.Console
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.Anchor
import models.entities.Enemy
import models.entities.Player
import models.entities.PlayerEntity

open class HealthBarComponent(private val givenEntity: PlayerEntity?, private val color: RGBA) : Container() {

    var healthBar: RoundRect = roundRect(givenEntity?.getHealth()!!, 20.0, 3.0, 3.0, color, Colors.BLACK, 1.0) {
        anchor(Anchor.MIDDLE_CENTER)
    }

    init {
        addChild(healthBar)
        addFixedUpdater(10.timesPerSecond) {
            updateHealthBar()
        }
    }

    private fun updateHealthBar() {
        this.removeChildren()

        if (givenEntity?.getHealth()!! <= 0) givenEntity.setHealth(0.0)

        healthBar = roundRect(givenEntity.getHealth(), 20.0, 3.0, 3.0, color, Colors.BLACK, 1.0) {
            anchor(Anchor.MIDDLE_CENTER)
        }
    }
}