package models

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import models.entities.Enemy
import models.entities.Player
import models.components.GameOverMenu
import models.gui.LevelBackground
import models.gui.PlayerGui

class LevelData(private val levelName: String,
                private val score: TimeSpan?,
                private val levelManager: LevelManager,
                private val currentEnemy: Enemy,
                private val currentPlayer: Player,
                private val levelBackground: LevelBackground?): Container() {

    private val deviceWidth : Int = MainModule.size.width
    private val deviceHeight : Int = MainModule.size.height
    private lateinit var enemySprite: Sprite
    private lateinit var playerGui: Container
    private lateinit var levelMechanics: LevelMechanics
    private lateinit var gameOverMenu: Container

    suspend fun init() {
        levelManager.start()
        initStage()
        initGui()
        initEnemy()
        initMechanics()
        initGameOverMenu()

        addFixedUpdater(10.timesPerSecond) {
            this.checkGameStatus(score)
        }
    }

    fun initGui() {
        playerGui = this.buildGui(currentPlayer, currentEnemy)
        this.addChild(playerGui)
    }

    fun initStage() {

    }

    suspend fun initEnemy() {
        enemySprite = this.buildEnemySprite()
        enemySprite.xy(deviceWidth / 2.0, deviceHeight / 2.0)
        enemySprite.anchor(Anchor.MIDDLE_CENTER)
        this.startAnimation(enemySprite)
    }

    fun initMechanics() {
        levelMechanics = this.buildGameMechanics(this, enemySprite, currentEnemy)
    }

    fun initGameOverMenu() {
        gameOverMenu = this.buildGameOverMenu(levelManager)
    }

    private fun buildGameMechanics(levelData: LevelData, enemySprite: Sprite, currentEnemy: Enemy): LevelMechanics {
        return LevelMechanics(levelData, enemySprite, currentEnemy)
    }

    private fun buildGameOverMenu(levelManager: LevelManager): Container {
        return GameOverMenu(levelManager).position(deviceWidth / 2.0, deviceHeight / 3.5).scale(2.0, 2.0)
    }

    /** GUI */
    private fun buildGui(currentPlayer: Player, currentEnemy: Enemy): Container {
        return PlayerGui(currentPlayer, currentEnemy)
    }

    /** Enemy Sprite */
    private suspend fun buildEnemySprite(): Sprite {
        val enemyInitialAnim = SpriteAnimation(
                spriteMap = resourcesVfs[currentEnemy.getSpriteFileLoc()].readBitmap(),
                spriteWidth = currentEnemy.getSpriteWidth(),
                spriteHeight = currentEnemy.getSpriteHeight(),
                columns = currentEnemy.getSpriteMapCols(),
                rows = currentEnemy.getSpriteMapRows(),
        )

        return sprite(enemyInitialAnim).scale(10.0)
    }

    /** Game Status Updater */
    private fun checkGameStatus(dt: TimeSpan?) {
        if ((currentPlayer.getHealth() <= 0.0 || currentEnemy.getHealth() <= 0.0) && levelManager.isOngoing)  {
            levelManager.finish()
            this.addChild(gameOverMenu)
        }
    }

    private fun startAnimation(sprite: Sprite) {
        sprite.playAnimationLooped(spriteDisplayTime = 150.milliseconds)
    }

    private fun stopAnimation(sprite: Sprite) {
        sprite.stopAnimation()
    }

    override fun toString(): String {
        return "LevelData levelName: ${this.levelName}\n\tScore: ${this.score}\n\t" +
                "levelManager: ${this.levelManager.toString()}\n\tcurrentEnemy: ${this.currentEnemy.toString()}\n\t" +
                "currentPlayer: ${this.currentPlayer.toString()}\n\tstageInfo: ${this.levelBackground.toString()}\n\t"
    }
}