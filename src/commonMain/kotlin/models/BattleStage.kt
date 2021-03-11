package models

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import factories.BattleManager

class BattleStage(val levelName: String,
                  val score: TimeSpan,
                  val battleManager: BattleManager,
                  private val mainEnemy: Enemy,
                  val currentPlayer: Player): Container() {

    private lateinit var enemySprite: Sprite

    suspend fun init() {
        initEnemy()
        initStage()
    }

    suspend fun initStage() {

    }

    suspend fun initEnemy() {
        enemySprite = this.buildEnemySprite()
        enemySprite.xy(MainModule.size.width / 2.0, MainModule.size.height / 2.0)
        enemySprite.anchor(Anchor.MIDDLE_CENTER)
        this.startAnimation()
    }

    private suspend fun buildEnemySprite(): Sprite {
        val enemySpriteMap = resourcesVfs[mainEnemy.spriteFile].readBitmap()
        val enemyInitialAnim = SpriteAnimation(
                spriteMap = enemySpriteMap,
                spriteWidth = 41,
                spriteHeight = 26,
                columns = 2,
                rows = 2,
        )

        return sprite(enemyInitialAnim).scale(4.0)
    }

    private fun startAnimation() {
        enemySprite.playAnimationLooped(spriteDisplayTime = 150.milliseconds)
    }

    private fun stopAnimation() {
        enemySprite.stopAnimation()
    }

}