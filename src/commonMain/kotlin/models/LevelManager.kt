package models

import com.soywiz.klogger.Logger
import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korge.view.Container
import constants.LevelStatus

class LevelManager(private val container : Container, val sceneContainer: SceneContainer?) {

    private var isOngoing: Boolean
    private var battleStatus: LevelStatus
    private var levelTimer: LevelTimer

    init {
        isOngoing = false
        battleStatus = LevelStatus.NOT_STARTED
        levelTimer = LevelTimer()
    }

    fun start() {
        levelTimer.startTimer()
        isOngoing = true;
        battleStatus = LevelStatus.ON
    }

    fun finish() {
        levelTimer.finishTimer()
        isOngoing = false
        battleStatus = LevelStatus.FINISHED
    }

    fun getIsOngoing(): Boolean {
        return isOngoing
    }

    fun getScore(): Double {
        return levelTimer.getTimeElapsed()
    }

    override fun toString(): String {
        return "LevelManager isOngoing: ${this.isOngoing}\n\tBattleStatus: ${this.battleStatus}\n"
    }
}