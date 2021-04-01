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
import models.gui.LevelBackground
import kotlin.test.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import models.LevelData
import models.entities.*

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
		println("---------------------- Level Data Test ----------------------")
		val testContainer = Container()
		val testManager = LevelManager(testContainer, null)
		val testScore = TimeSpan(0.0)
		val testPlayer = Player("test_player", 1.0)
		var testAttackPattern = AttackPattern(5.0, 5000.0, 3, 1000.0)
		var testAttackMoveset = AttackMoveset(arrayOf(testAttackPattern))
		val testEnemy = Enemy("test_enemy", 10000.0, "fileLocation/testFile", 0, 0, 0, 0, testAttackMoveset)
		val testBackground = LevelBackground("test_level", "fileLocation/testBackgroundFile")

		val testLevelData = LevelDataFactory.createLevelTest(testManager, testScore, testEnemy, testPlayer, testBackground)

		println(testLevelData.toString())
	}

	@Test
	fun testLevelManager() {
		println("---------------------- Level Manager Test ----------------------")
		val testContainer = Container()
		val testManager = LevelManager(testContainer, null)

		println(testManager.toString())
	}

	@Test
	fun testPlayerEntity() {
		println("---------------------- Player Entity Test ----------------------")
		var basicAttackPattern = AttackPattern(5.0, 5000.0, 3, 1000.0)
		var basicAttackMoveset = AttackMoveset(arrayOf(basicAttackPattern))

		val player = Player("test_player", 1.0)
		val enemy = Enemy("test_enemy", 10000.0, "fileLocation/testFile", 0, 0, 0, 0, basicAttackMoveset)
		val entity = PlayerEntity("naked_entity", 0.0)

		println(player.toString())
		println(enemy.toString())
		println(entity.toString())
	}

	@Test
	fun testPlacemarker() {
		println("---------------------- Place Marker Test ----------------------")
		val pm = Placemarker(1, 100, 200)
		println(pm.toString())
	}

	@Test
	fun testGameMap() {
		println("---------------------- GameMap Test ----------------------")
		val m = GameMap(1, null, listOf(
				Placemarker(1, 100, 100),
				Placemarker(2, 200, 200),
				Placemarker(3, 300, 300)
		))
		println(m.toString())
	}

	@Test
	fun testJson() {
		/*
		val p = Placemarker(1, 100, 200)
		val pJson= Json.encodeToString(p)
		println(pJson)
		*/
		println("---------------------- Initial JSON Test ----------------------")
		var basicAttackPattern = AttackPattern(5.0, 5000.0, 3, 1000.0)
		var basicAttackMoveset = AttackMoveset(arrayOf(basicAttackPattern))
		val world = GameMap(1, "map\\grass.png",
		listOf(Placemarker(1, 300, 300, false,
				LevelData("First Level", TimeSpan(60000.0),
						currentEnemy = Enemy("Leroy", 100.0, "ballbot\\spritesheet.png", 64, 64, 2, 2, basicAttackMoveset),
						levelBackground = LevelBackground("First Level Background", "battle\\first_level.png")
		))))
		val pWorld = Json{prettyPrint = true}.encodeToString(world)
		println(pWorld)
	}

	@Test
	fun testEnemySerialization() {
		println("---------------------- Enemy Json Test ----------------------")
		println("1. Object Encoding (to Json)")
		var basicAttackPattern = AttackPattern(5.0, 5000.0, 3, 1000.0)
		var basicAttackMoveset = AttackMoveset(arrayOf(basicAttackPattern))
		val enemy = Enemy("Leroy", 100.0, "ballbot\\spritesheet.png", 64, 64, 2, 2, basicAttackMoveset)
		var sEnemy = Json.encodeToString(enemy)
		sEnemy = sEnemy.substring(1, sEnemy.length - 1)
		println(sEnemy)
		println("2. Object Decoding (from Json)")
		val jEnemy = Json.decodeFromString<Enemy>("""
		{"name" : "Leroy","maxHealth":100.0,"spriteFileLoc":"ballbot\\spritesheet.png","spriteWidth":64,"spriteHeight":64,"spriteMapCols":2,"spriteMapRows":2,"attackMoveset":{"attackPatterns":[{"attackDamage":5.0,"timeUntilInitiate":5000.0,"totalCycles":3,"timeBetweenCycles":1000.0}]}}
			""".trimIndent())
		println(jEnemy.toString())
		println(jEnemy.getMaxHealth())
		println(jEnemy.getName())
	}

	fun testAttackPatternJson() {
		println("---------------------- Attack Pattern Json Test ----------------------")
		println("1. Object Encoding (to Json)")
		var testAttackPattern = AttackPattern(5.0, 5000.0, 3, 1000.0)
		val pPattern = Json{prettyPrint = true}.encodeToString(testAttackPattern)
		println(pPattern)
		println("2. Object Decoding (from Json)")
		val pPatternObj = Json.decodeFromString<AttackPattern>("""
		{"attackDamage":5.0,"timeUntilInitiate":5000.0,"totalCycles":3,"timeBetweenCycles":1000.0}""".trimIndent())
		println(pPatternObj.getDamage())
		println(pPatternObj.getTimeBetweenCycles())
		println(pPatternObj.getTimeUntilInitiate())
		println(pPatternObj.getTotalCycles())
	}

	@Test
	fun testAttackMovesetJson() {
		println("---------------------- Attack Moveset Json Test ----------------------")
		println("1. Object Encoding (to Json)")
		var testAttackPattern = AttackPattern(5.0, 5000.0, 3, 1000.0)
		var testAttackMoveset = AttackMoveset(arrayOf(testAttackPattern))
		val pMoveset = Json{prettyPrint = true}.encodeToString(testAttackMoveset)
		println(pMoveset)
		println("2. Object Decoding (from Json)")
		val pMovesetObj = Json.decodeFromString<AttackMoveset>("""
		{"attackPatterns":[{"attackDamage":5.0,"timeUntilInitiate":5000.0,"totalCycles":3,"timeBetweenCycles":1000.0}]}""".trimIndent())
		println("Size of moveset: ${pMovesetObj.getNumberOfPatterns()}")
		println(pMovesetObj.getAttackPatterns()[0].getDamage())
		println(pMovesetObj.getAttackPatterns()[0].getTimeBetweenCycles())
		println(pMovesetObj.getAttackPatterns()[0].getTimeUntilInitiate())
		println(pMovesetObj.getAttackPatterns()[0].getTotalCycles())
	}
}