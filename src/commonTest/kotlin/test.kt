import com.soywiz.klock.*
import com.soywiz.korge.input.*
import com.soywiz.korge.tests.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korma.geom.*
import factories.LevelDataFactory
import models.LevelManager
import map.GameMap
import map.Placemarker
import models.entities.Enemy
import models.entities.Player
import models.entities.PlayerEntity
import models.gui.LevelBackground
import kotlin.test.*

class MyTest : ViewsForTesting() {
	@Test
	fun test() = viewsTest {
		val log = arrayListOf<String>()
		val rect = solidRect(100, 100, Colors.RED)
		rect.onClick {
			log += "clicked"
		}
		assertEquals(1, views.stage.numChildren)
		rect.simulateClick()
		assertEquals(true, rect.isVisibleToUser())
		tween(rect::x[-102], time = 10.seconds)
		assertEquals(Rectangle(x=-102, y=0, width=100, height=100), rect.globalBounds)
		assertEquals(false, rect.isVisibleToUser())
		assertEquals(listOf("clicked"), log)
	}

	@Test
	fun testLevelData() {
		val testContainer = Container()
		val testManager = LevelManager(testContainer, null)
		val testScore = TimeSpan(0.0)
		val testPlayer = Player("test_player", 1.0)
		val testEnemy = Enemy("test_enemy", 10000.0, "fileLocation/testFile", 0, 0, 0, 0)
		val testBackground = LevelBackground("test_level", "fileLocation/testBackgroundFile")

		val testLevelData = LevelDataFactory.createLevel(testManager, testScore, testEnemy, testPlayer, testBackground)

		println(testLevelData.toString())
	}

	@Test
	fun testLevelManager() {
		val testContainer = Container()
		val testManager = LevelManager(testContainer, null)

		println(testManager.toString())
	}

	@Test
	fun testPlayerEntity() {
		val player = Player("test_player", 1.0)
		val enemy = Enemy("test_enemy", 10000.0, "fileLocation/testFile", 0, 0, 0, 0)
		val entity = PlayerEntity("naked_entity", 0.0)

		println(player.toString())
		println(enemy.toString())
		println(entity.toString())
	}

	@Test
	fun testPlacemarker() {
		val pm = Placemarker(1, 100, 200, levelData = null)
		println(pm.toString())
	}

	@Test
	fun testGameMap() {
		val m = GameMap(1, null, listOf(
				Placemarker(1, 100, 100, levelData = null),
				Placemarker(2, 200, 200, levelData = null),
				Placemarker(3, 300, 300, levelData = null)
		))
		println(m.toString())
	}
}