package models.entities

import com.soywiz.klock.seconds
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@Serializable(with = Enemy.EnemySerializer::class)
class Enemy(name: String, maxHealth: Double,
            private var spriteFileLoc: String,
            private var spriteWidth: Int,
            private var spriteHeight: Int,
            private var spriteMapCols: Int,
            private var spriteMapRows: Int): PlayerEntity(name, maxHealth) {

    // To properly serialize the object for JSON, see https://stackoverflow.com/a/65272372/5310062
    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(forClass = Enemy::class)
    object EnemySerializer : KSerializer<Enemy> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("currentEnemy") {
            element<String>("name")
            element<Double>("maxHealth")
            element<String>("spriteFileLoc")
            element<Int>("spriteWidth")
            element<Int>("spriteHeight")
            element<Int>("spriteMapCols")
            element<Int>("spriteMapRows")
        }

        override fun serialize(encoder: Encoder, value: Enemy) {
            encoder.encodeStructure(descriptor) {
                encodeStringElement(descriptor, 0, value.getName())
                encodeDoubleElement(descriptor, 1, value.getHealth())
                encodeStringElement(descriptor, 2, value.getSpriteFileLoc())
                encodeIntElement(descriptor, 3, value.getSpriteWidth())
                encodeIntElement(descriptor, 4, value.getSpriteHeight())
                encodeIntElement(descriptor, 5, value.getSpriteMapCols())
                encodeIntElement(descriptor, 6, value.getSpriteMapRows())
            }
        }

        override fun deserialize(decoder: Decoder): Enemy {
            return decoder.decodeStructure(descriptor) {
                var name : String = "noname"
                var maxHealth : Double = 0.0
                var spriteFileLoc : String = "null"
                var spriteWidth: Int = -1
                var spriteHeight: Int = -1
                var spriteMapCols: Int = -1
                var spriteMapRows: Int = -1

                loop@ while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        DECODE_DONE -> break@loop
                        0 -> name = decodeStringElement(descriptor, 0)
                        1 -> maxHealth = decodeDoubleElement(descriptor, 1)
                        2 -> spriteFileLoc = decodeStringElement(descriptor, 2)
                        3 -> spriteWidth = decodeIntElement(descriptor, 3)
                        4 -> spriteHeight = decodeIntElement(descriptor,4)
                        5 -> spriteMapCols = decodeIntElement(descriptor, 5)
                        6 -> spriteMapRows = decodeIntElement(descriptor, 6)
                        else -> throw SerializationException("Unexpected index $index")
                    }
                }

                Enemy(name, maxHealth, spriteFileLoc, spriteWidth, spriteHeight, spriteMapCols, spriteMapRows)
            }
        }
    }

    fun setSpriteFileLoc(newSpriteFileLoc: String) {
        spriteFileLoc = newSpriteFileLoc
    }

    fun getSpriteFileLoc(): String {
        return spriteFileLoc
    }

    fun setSpriteWidth(newSpriteWidth: Int) {
        spriteWidth = newSpriteWidth
    }

    fun getSpriteWidth(): Int {
        return spriteWidth
    }

    fun setSpriteHeight(newSpriteHeight: Int) {
        spriteHeight = newSpriteHeight
    }

    fun getSpriteHeight(): Int {
        return spriteHeight
    }

    fun setSpriteMapCols(newSpriteMapCols: Int) {
        spriteMapCols = newSpriteMapCols
    }

    fun getSpriteMapCols(): Int {
        return spriteMapCols
    }

    fun setSpriteMapRows(newSpriteMapRows: Int) {
        spriteMapRows = newSpriteMapRows
    }

    fun getSpriteMapRows(): Int {
        return spriteMapRows
    }

    fun getAttackMoveset(): AttackMoveset {
        var basicAttackPattern = AttackPattern(10.0, 5.seconds, 3, 2.seconds)
        var basicAttackMoveset = AttackMoveset(arrayOf(basicAttackPattern))
        return basicAttackMoveset
    }

    override fun toString(): String {
        return "Enemy ${this.getName()}\n\tHealth: ${this.getMaxHealth()}\n\tLocation: ${this.getSpriteFileLoc()}"
    }
}