package models

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.klock.timesPerSecond
import com.soywiz.klogger.Console
import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import factories.LevelManager
import models.entities.Enemy
import models.entities.Player
import models.gui.GameOverMenu
import models.gui.LevelBackground
import models.gui.PlayerGui

class LevelData(val levelName: String,
                val score: TimeSpan?,
                val levelManager: LevelManager,
                private val currentEnemy: Enemy,
                val currentPlayer: Player,
                val levelBackground: LevelBackground?): Container() {

    private lateinit var enemySprite: Sprite
    private lateinit var playerGui: Container
    private lateinit var gameOverMenu: Container

    suspend fun init() {
        levelManager.start()
        initStage()
        initGui()
        initEnemy()
        initGameOverMenu()

        addFixedUpdater(10.timesPerSecond) {
            this.checkGameStatus(score)
        }
    }

    suspend fun initGui() {
        playerGui = this.buildGui(currentPlayer, currentEnemy)
        this.addChild(playerGui)
    }

    suspend fun initStage() {

    }

    suspend fun initEnemy() {
        enemySprite = this.buildEnemySprite()
        enemySprite.xy(MainModule.size.width / 2.0, MainModule.size.height / 2.0)
        enemySprite.anchor(Anchor.MIDDLE_CENTER)
        this.startAnimation()

        enemySprite.onClick {
            currentEnemy.reduceHealth(10)
        }
    }

    suspend fun initGameOverMenu() {
        gameOverMenu = this.buildGameOverMenu(levelManager)
    }

    /** Game Status Updater */
    private fun checkGameStatus(dt: TimeSpan?) {
        if ((currentPlayer.getHealth() == 0.0 || currentEnemy.getHealth() == 0.0) && levelManager.isOngoing)  {
            Console.log("hit");
            levelManager.finish()
            this.addChild(gameOverMenu)
        }
    }

    private suspend fun buildGameOverMenu(levelManager: LevelManager): Container {
        val gameOverMenu = GameOverMenu(levelManager)
        gameOverMenu.position(MainModule.size.width / 2.0, MainModule.size.height / 2.0).scale(4.0, 4.0)

        return gameOverMenu
    }

    /** GUI */
    private suspend fun buildGui(currentPlayer: Player, currentEnemy: Enemy): Container {
        val playerGui = PlayerGui(currentPlayer, currentEnemy)

        return playerGui
    }

    /** Enemy Sprite */
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

    override fun toString(): String {
        return "LevelData levelName: ${this.levelName}\n\tScore: ${this.score}\n\t" +
                "levelManager: ${this.levelManager.toString()}\n\tcurrentEnemy: ${this.currentEnemy.toString()}\n\t" +
                "currentPlayer: ${this.currentPlayer.toString()}\n\tstageInfo: ${this.levelBackground.toString()}\n\t"
    }
}