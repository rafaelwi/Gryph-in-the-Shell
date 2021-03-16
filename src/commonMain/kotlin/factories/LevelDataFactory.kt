package factories

import models.entities.Enemy
import models.LevelData
import models.entities.Player

object LevelDataFactory {


    //fun createStage(): BattleStage {

    //}

    fun createTestStage(levelManager: LevelManager?): LevelData {

        val testEnemy = Enemy("testEnemy", 100.0, "clawbot\\1\\spritesheet.png")
        val testPlayer = Player("testPlayer", 100.0)

        return LevelData("TestStage", null, levelManager, testEnemy, testPlayer)
    }

}