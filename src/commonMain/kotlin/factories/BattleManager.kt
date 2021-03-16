package factories

import com.soywiz.korge.view.Container
import constants.BattleStatus

class BattleManager(private val container : Container) {

    var isOngoing = false;
    var battleStatus = BattleStatus.NOT_STARTED

    fun start() {
        isOngoing = true;
        battleStatus = BattleStatus.ON
    }

    fun finish() {
        isOngoing = false
        battleStatus = BattleStatus.FINISHED
    }
}