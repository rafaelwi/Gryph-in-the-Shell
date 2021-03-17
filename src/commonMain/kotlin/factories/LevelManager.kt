package factories

import com.soywiz.korge.scene.SceneContainer
import com.soywiz.korge.view.Container
import constants.LevelStatus

class LevelManager(private val container : Container, val sceneContainer: SceneContainer?) {

    var isOngoing = false;
    var battleStatus = LevelStatus.NOT_STARTED

    fun start() {
        isOngoing = true;
        battleStatus = LevelStatus.ON
    }

    fun finish() {
        isOngoing = false
        battleStatus = LevelStatus.FINISHED
    }

    override fun toString(): String {
        return "LevelManager isOngoing: ${this.isOngoing}\n\tBattleStatus: ${this.battleStatus}\n"
    }
}