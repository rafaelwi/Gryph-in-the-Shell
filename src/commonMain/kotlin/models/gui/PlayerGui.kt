package models.gui

import com.soywiz.klock.timesPerSecond
import com.soywiz.klogger.Console
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.math.roundDecimalPlaces
import models.LevelManager
import models.entities.Enemy
import models.entities.Player

class PlayerGui(private var levelManager: LevelManager?, private val currentPlayer: Player?, private val currentEnemy: Enemy?): Container() {

    private var playerHealthBar: Container
    private var enemyHealthBar: Container
    private var defendLayer: SolidRect
    private lateinit var levelTimerText: Text

    init {
        playerHealthBar = PlayerHealthBarComponent(currentPlayer, Colors.GREEN)
        enemyHealthBar = HealthBarComponent(currentEnemy, Colors.RED)
        defendLayer = solidRect(MainModule.size.width, MainModule.size.height, Colors.AQUA)
        buildDefendLayer(defendLayer)
        buildHealthGui(playerHealthBar, enemyHealthBar)
        buildTimeDisplay(levelManager)
        addFixedUpdater(30.timesPerSecond) {
            if (levelManager!!.getIsOngoing()) {
                checkDefendLayer(levelManager, defendLayer)
                checkLevelTimer(levelManager, levelTimerText)
            }
        }
    }

    private fun buildHealthGui(playerHealthBar: Container, enemyHealthBar: Container) {
        enemyHealthBar.position(MainModule.size.width * 0.5, MainModule.size.height * 0.125).scale(8.0, 4.0)
        playerHealthBar.position(MainModule.size.width * 0.5, MainModule.size.height * 0.9).scale(8.0, 4.0)

        addChild(playerHealthBar)
        addChild(enemyHealthBar)
    }

    private fun buildTimeDisplay(levelManager: LevelManager?) {
        var mainText = "${levelManager!!.getCurrTime().seconds.roundDecimalPlaces(1)}s"
        levelTimerText = text(mainText, textSize = 100.0, alignment = TextAlignment.TOP_RIGHT) {
            position(MainModule.size.width * 0.99, MainModule.size.height * 0.01)
        }
        addChild(levelTimerText)
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

    private fun checkLevelTimer(levelManager: LevelManager?, levelTimerText: Text) {
        levelTimerText.setText("${levelManager!!.getCurrTime().seconds.roundDecimalPlaces(1)}s")
    }

}
