package factories

import models.entities.Enemy
import models.BattleStage
import models.entities.Player

object BattleStageFactory {


    //fun createStage(): BattleStage {

    //}

    fun createTestStage(battleManager: BattleManager): BattleStage {

        val testEnemy = Enemy("testEnemy", 100.0, "clawbot\\1\\spritesheet.png")
        val testPlayer = Player("testPlayer", 100.0)

        return BattleStage("TestStage", null, battleManager, testEnemy, testPlayer)
    }

}