package factories

import com.soywiz.klock.TimeSpan
import models.entities.Enemy
import models.LevelData
import models.LevelManager
import models.entities.Player
import models.gui.LevelBackground

object LevelDataFactory {

    /**
     * This function could take something else, like a JSON object or something.
     * This is just a placeholder as is.
     */
    fun createLevelTest(levelManager: LevelManager, playerScore: TimeSpan?, enemy: Enemy, player: Player, background: LevelBackground): LevelData {
        return LevelData("testName", playerScore, levelManager, enemy, player, background)
    }

    fun createTestLevel(levelManager: LevelManager): LevelData {
        val testEnemy = Enemy("testEnemy", 100.0, "clawbot\\1\\spritesheet.png", 41, 26, 2, 2)
        val testPlayer = Player("testPlayer", 100.0)
        return LevelData("TestStage", null, levelManager, testEnemy, testPlayer, null)
    }
}
