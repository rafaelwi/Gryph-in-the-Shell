package models.gui

import com.soywiz.klock.milliseconds
import com.soywiz.klock.timesPerSecond
import com.soywiz.klogger.Console
import com.soywiz.korge.time.delay
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Anchor
import models.LevelManager
import models.entities.Enemy
import models.entities.Player

class PlayerGui(private var levelManager: LevelManager?, private val currentPlayer: Player?, private val currentEnemy: Enemy?): Container() {

    private var playerHealthBar: Container
    private var enemyHealthBar: Container

    init {
        playerHealthBar = PlayerHealthBarComponent(currentPlayer, Colors.GREEN)
        enemyHealthBar = HealthBarComponent(currentEnemy, Colors.RED)
        buildHealthGui(playerHealthBar, enemyHealthBar)
    }

    private fun buildHealthGui(playerHealthBar: Container, enemyHealthBar: Container) {
        enemyHealthBar.position(MainModule.size.width * 0.5, MainModule.size.height * 0.125).scale(8.0, 4.0)
        playerHealthBar.position(MainModule.size.width * 0.5, MainModule.size.height * 0.9).scale(8.0, 4.0)

        addChild(playerHealthBar)
        addChild(enemyHealthBar)
    }

}