package models.gui

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import models.entities.Enemy
import models.entities.Player

class PlayerGui(private val currentPlayer: Player, private val currentEnemy: Enemy): Container() {

    private var playerHealthBar: Container
    private var enemyHealthBar: Container

    init {
        playerHealthBar = PlayerHealthBarComponent(currentPlayer, Colors.GREEN)
        enemyHealthBar = HealthBarComponent(currentEnemy, Colors.RED)
        this.buildHealthGui(playerHealthBar, enemyHealthBar)
    }

    private fun buildHealthGui(playerHealthBar: Container, enemyHealthBar: Container) {
        enemyHealthBar.position(MainModule.size.width / 2.0, MainModule.size.height / 8.0).scale(8.0, 4.0)
        playerHealthBar.position(MainModule.size.width / 2.0, MainModule.size.height / 1.1).scale(8.0, 4.0)

        this.addChild(playerHealthBar)
        this.addChild(enemyHealthBar)
    }

}