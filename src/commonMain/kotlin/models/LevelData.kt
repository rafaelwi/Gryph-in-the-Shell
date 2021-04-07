package models

import com.soywiz.klock.*
import com.soywiz.klogger.Console
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.resources.resource
import com.soywiz.korma.geom.Anchor
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import models.entities.Enemy
import models.entities.Player
import models.components.GameOverMenu
import models.gui.LevelBackground
import models.gui.PlayerGui

@Serializable
class LevelData(private val levelName: String,
                @Transient
                private val score: TimeSpan? = null,
                @Transient
                private var levelManager: LevelManager? = null,
                @Transient
                private var screenManager: ScreenManager? = null,
                private val currentEnemy: Enemy,
                @Transient
                private var currentPlayer: Player? = null,
                private val levelBackground: LevelBackground): Container() {

    @Transient
    private val deviceWidth: Int = MainModule.size.width
    @Transient
    private val deviceHeight: Int = MainModule.size.height
    @Transient
    private lateinit var enemySprite: Sprite
    @Transient
    private lateinit var playerGui: Container
    @Transient
    private lateinit var levelMechanics: LevelMechanics
    @Transient
    private lateinit var gameOverMenu: Container

    suspend fun init() {
        levelManager?.start()
        initStage()
        initEnemy()
        initGui()
        initMechanics()
        initGameOverMenu()

        addFixedUpdater(60.timesPerSecond) {
            this.checkGameStatus(score)
            this.updateEnemyStatus(score)
            this.checkScreenStatus()
        }
    }

    fun initGui() {
        playerGui = this.buildGui(levelManager, currentPlayer, currentEnemy)
        this.addChild(playerGui)
    }

    suspend fun initStage() {
        this.buildStage(levelBackground)
    }

    suspend fun initEnemy() {
        enemySprite = this.buildEnemySprite()
        enemySprite.xy(deviceWidth * 0.5, deviceHeight * 0.5)
        enemySprite.anchor(Anchor.MIDDLE_CENTER)
        this.startAnimation(enemySprite)
    }

    suspend fun initMechanics() {
        levelMechanics = this.buildGameMechanics(this, levelManager, enemySprite, currentEnemy, currentPlayer)
        levelMechanics.init()
    }

    fun initGameOverMenu() {
        gameOverMenu = this.buildGameOverMenu(levelManager)
    }

    private fun buildGameMechanics(levelData: LevelData, levelManager: LevelManager?, enemySprite: Sprite, currentEnemy: Enemy, currentPlayer: Player?): LevelMechanics {
        return LevelMechanics(levelData, levelManager, enemySprite, currentEnemy, currentPlayer)
    }

    private fun buildGameOverMenu(levelManager: LevelManager?): Container {
        return GameOverMenu(levelManager).position(deviceWidth * 0.5, deviceHeight * 0.3).scale(2.0, 2.0)
    }

    private suspend fun buildStage(levelBackground: LevelBackground): Image {
        return image(resourcesVfs[levelBackground.getPngFileLoc()].readBitmap()) {
            smoothing = false
            tint = Colors.DARKGRAY
            scale = 3.0
        }
    }

    /** GUI */
    private fun buildGui(levelManager: LevelManager?, currentPlayer: Player?, currentEnemy: Enemy): Container {
        return PlayerGui(levelManager, currentPlayer, currentEnemy)
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
        if ((currentPlayer?.getHealth()!! <= 0.0 || currentEnemy.getHealth() <= 0.0) && levelManager?.getIsOngoing() == true)  {
            levelManager?.finish()
            Console.log(levelManager!!.getScore())
            this.addChild(gameOverMenu)
        }
    }

    private fun checkScreenStatus() {
        screenManager?.check()
    }

    private fun updateEnemyStatus(dt: TimeSpan?) {
        levelMechanics.initiateAttack(dt)
    }


    private fun startAnimation(sprite: Sprite) {
        sprite.playAnimationLooped(spriteDisplayTime = 150.milliseconds)
    }

    private fun stopAnimation(sprite: Sprite) {
        sprite.stopAnimation()
    }

    fun getLevelManager(): LevelManager? {
        return this.levelManager
    }

    fun setLevelManager(levelManager: LevelManager?) {
        this.levelManager = levelManager
    }

    fun setScreenManager(screenManager: ScreenManager?) {
        this.screenManager = screenManager
    }

    fun setCurrentPlayer (currentPlayer: Player?) {
        this.currentPlayer = currentPlayer
    }

    override fun toString(): String {
        return "LevelData levelName: ${this.levelName}\n\tScore: ${this.score}\n\t" +
                "levelManager: ${this.levelManager.toString()}\n\tcurrentEnemy: ${this.currentEnemy.toString()}\n\t" +
                "currentPlayer: ${this.currentPlayer.toString()}\n\tstageInfo: ${this.levelBackground.toString()}\n\t"
    }
}