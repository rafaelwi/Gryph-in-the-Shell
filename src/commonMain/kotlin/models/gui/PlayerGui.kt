package models.gui

import com.soywiz.klock.timesPerSecond
import com.soywiz.klogger.Console
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Anchor
import models.LevelManager
import models.entities.Enemy
import models.entities.Player

class PlayerGui(private var levelManager: LevelManager?, private val currentPlayer: Player?, private val currentEnemy: Enemy?): Container() {

    private var playerHealthBar: Container
    private var enemyHealthBar: Container
    private var defendLayer: SolidRect

    init {
        playerHealthBar = PlayerHealthBarComponent(currentPlayer, Colors.GREEN)
        enemyHealthBar = HealthBarComponent(currentEnemy, Colors.RED)
        defendLayer = solidRect(MainModule.size.width, MainModule.size.height, Colors.AQUA)
        buildDefendLayer(defendLayer)
        buildHealthGui(playerHealthBar, enemyHealthBar)
        addFixedUpdater(30.timesPerSecond) {
            if (levelManager!!.getIsOngoing()) checkDefendLayer(levelManager, defendLayer)
        }
    }

    private fun buildHealthGui(playerHealthBar: Container, enemyHealthBar: Container) {
        enemyHealthBar.position(MainModule.size.width * 0.5, MainModule.size.height * 0.125).scale(8.0, 4.0)
        playerHealthBar.position(MainModule.size.width * 0.5, MainModule.size.height * 0.9).scale(8.0, 4.0)

        addChild(playerHealthBar)
        addChild(enemyHealthBar)
    }

    private fun buildDefendLayer(defendLayer: SolidRect) {
        defendLayer.position(MainModule.size.width * 0.5, MainModule.size.height * 0.5).anchor(Anchor.MIDDLE_CENTER)
        defendLayer.alpha(0)
        addChild(defendLayer)
    }

    private fun checkDefendLayer(levelManager: LevelManager?, hitScreen: SolidRect) {
        if (levelManager!!.getIsDefending()) {
            hitScreen.alpha(0.2)
        } else {
            hitScreen.alpha(0)
        }
    }

}
