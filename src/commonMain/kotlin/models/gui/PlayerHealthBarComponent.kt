package models.gui

import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.view.*
import com.soywiz.korim.color.RGBA
import models.entities.Player

class PlayerHealthBarComponent(private val givenEntity: Player?, color: RGBA): HealthBarComponent(givenEntity, color) {

    private var healthText: Text = text(givenEntity?.getHealth().toString()).apply {
        centerBetween(0.0, 0.0, 0.0, 0.0)
    }

    init {
        addChild(healthText)
        addFixedUpdater(10.timesPerSecond) {
            updateHealthText()
        }
    }

    private fun updateHealthText() {
        healthText = text(givenEntity?.getHealth().toString()).apply {
            centerBetween(0.0, 0.0, 0.0, 0.0)
        }
    }

}