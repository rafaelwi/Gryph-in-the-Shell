package factories

import models.Enemy
import models.BattleStage
import models.Player

object BattleStageFactory {


    //fun createStage(): BattleStage {

    //}

    fun createTestStage(battleManager: BattleManager): BattleStage {

        val testEnemy = Enemy("testEnemy", 100, "clawbot\\1\\spritesheet.png")
        val testPlayer = Player("testPlayer", 100)

        return BattleStage("TestStage", null, battleManager, testEnemy, testPlayer)
    }

}