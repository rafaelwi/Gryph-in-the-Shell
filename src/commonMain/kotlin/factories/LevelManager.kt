package factories

import com.soywiz.korge.view.Container
import constants.LevelStatus

class LevelManager(private val container : Container) {

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
}