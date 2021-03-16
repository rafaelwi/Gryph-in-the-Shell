package factories

import com.soywiz.klock.TimeSpan
import models.entities.Enemy
import models.LevelData
import models.entities.Player
import models.gui.LevelBackground

object LevelDataFactory {


    fun createLevel(levelManager: LevelManager, playerScore: TimeSpan?, enemy: Enemy, player: Player, background: LevelBackground): LevelData {
        return LevelData("testName", playerScore, levelManager, enemy, player, background);
    }

    fun createTestLevel(levelManager: LevelManager?): LevelData {

        val testEnemy = Enemy("testEnemy", 100.0, "clawbot\\1\\spritesheet.png")
        val testPlayer = Player("testPlayer", 100.0)

        return LevelData("TestStage", null, levelManager, testEnemy, testPlayer)
    }

}