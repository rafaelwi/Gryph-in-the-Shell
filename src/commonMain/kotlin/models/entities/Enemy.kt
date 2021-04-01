package models.entities

import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.encodeStructure

@Serializable(with = Enemy.EnemySerializer::class)
class Enemy(name: String, maxHealth: Double,
            private var spriteFileLoc: String,
            private var spriteWidth: Int,
            private var spriteHeight: Int,
            private var spriteMapCols: Int,
            private var spriteMapRows: Int): PlayerEntity(name, maxHealth) {

    // To properly serialize the object for JSON, see https://stackoverflow.com/a/65272372/5310062
    object EnemySerializer : KSerializer<Enemy> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Enemy") {
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

        // TODO: Could be cleaned up (https://stackoverflow.com/a/65272372/5310062)
        override fun deserialize(decoder: Decoder): Enemy {
            val name = decoder.decodeString()
            val health = decoder.decodeDouble()
            val loc = decoder.decodeString()
            val w = decoder.decodeInt()
            val h = decoder.decodeInt()
            val c = decoder.decodeInt()
            val r = decoder.decodeInt()
            return Enemy(name, health, loc, w, h, c, r)
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
}