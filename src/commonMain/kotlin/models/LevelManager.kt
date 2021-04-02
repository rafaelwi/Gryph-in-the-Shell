package models

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korge.view.Container
import constants.LevelStatus

class LevelManager(private val mainScreen : Container, val sceneContainer: SceneContainer?) {

    private var isOngoing: Boolean
    private var battleStatus: LevelStatus
    private var levelTimer: LevelTimer
    private var isHit: Boolean

    init {
        isOngoing = false
        isHit = false
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

    fun triggerIsHit() {
        isHit = !isHit
    }

    fun getIsOngoing(): Boolean {
        return isOngoing
    }

    fun getCurrTime(): TimeSpan {
        return levelTimer.getTimeElapsed()
    }

    fun getScore(): Double {
        return levelTimer.getTimeCompleted()
    }

    fun getIsHit(): Boolean {
        return isHit
    }

    override fun toString(): String {
        return "LevelManager isOngoing: ${this.isOngoing}\n\tBattleStatus: ${this.battleStatus}\n"
    }
}