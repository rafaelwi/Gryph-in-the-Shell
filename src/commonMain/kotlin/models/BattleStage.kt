package models

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import factories.BattleManager
import models.entities.Enemy
import models.entities.Player

class BattleStage(val levelName: String,
                  val score: TimeSpan?,
                  val battleManager: BattleManager,
                  private val currentEnemy: Enemy,
                  val currentPlayer: Player): Container() {

    private lateinit var enemySprite: Sprite
    private lateinit var playerGui: Container

    suspend fun init() {
        initStage()
        initGui()
        initEnemy()
    }

    suspend fun initGui() {
        playerGui = this.buildGui(battleManager, currentPlayer, currentEnemy)
        this.addChild(playerGui)
    }

    suspend fun initStage() {

    }

    suspend fun initEnemy() {
        enemySprite = this.buildEnemySprite()
        enemySprite.xy(MainModule.size.width / 2.0, MainModule.size.height / 2.0)
        enemySprite.anchor(Anchor.MIDDLE_CENTER)
        this.startAnimation()
    }

    private suspend fun buildGui(battleManager: BattleManager, currentPlayer: Player, currentEnemy: Enemy): Container {
        val playerGui = PlayerGui(battleManager, currentPlayer, currentEnemy)

        return playerGui
    }

    private suspend fun buildEnemySprite(): Sprite {
        val enemySpriteMap = resourcesVfs[currentEnemy.spriteFile].readBitmap()
        val enemyInitialAnim = SpriteAnimation(
                spriteMap = enemySpriteMap,
                spriteWidth = 41,
                spriteHeight = 26,
                columns = 2,
                rows = 2,
        )

        return sprite(enemyInitialAnim).scale(10.0)
    }

    private fun startAnimation() {
        enemySprite.playAnimationLooped(spriteDisplayTime = 150.milliseconds)
    }

    private fun stopAnimation() {
        enemySprite.stopAnimation()
    }

}