package factories

import com.soywiz.klock.TimeSpan
import models.entities.Enemy
import models.LevelData
import models.LevelManager
import models.ScreenManager
import models.entities.AttackMoveset
import models.entities.AttackPattern
import models.entities.Player
import models.gui.LevelBackground

object LevelDataFactory {

    /**
     * This function could take something else, like a JSON object or something.
     * This is just a placeholder as is.
     */
    fun createLevelTest(levelManager: LevelManager, screenManager: ScreenManager, playerScore: TimeSpan?, enemy: Enemy, player: Player, background: LevelBackground): LevelData {
        return LevelData("testName", playerScore, levelManager, screenManager, enemy, player, background);
    }

    fun createTestLevel(levelManager: LevelManager, screenManager: ScreenManager): LevelData {
        var basicAttackPattern = AttackPattern(5.0, 5000.0, 3, 1000.0)
        var basicAttackPattern2 = AttackPattern(2.0, 5000.0, 6, 100.0)
        var basicAttackPattern3 = AttackPattern(20.0, 5000.0, 2, 2000.0)
        var basicAttackMoveset = AttackMoveset(arrayOf(basicAttackPattern, basicAttackPattern2, basicAttackPattern3))
        val testEnemy = Enemy("testEnemy", 100.0, "clawbot\\1\\spritesheet.png", 41, 26, 2, 2, basicAttackMoveset)
        val testPlayer = Player("testPlayer", 100.0)
        val testBackground = LevelBackground("first level", "background\\city.png")

        return LevelData("TestStage", null, levelManager, screenManager, testEnemy, testPlayer, testBackground)
    }
}
