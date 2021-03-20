package models.entities

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import models.entities.PlayerEntity

@Serializable(with = Enemy.EnemySerializer::class)
class Enemy(name: String, maxHealth: Double,
            private var spriteFileLoc: String,
            private var spriteWidth: Int,
            private var spriteHeight: Int,
            private var spriteMapCols: Int,
            private var spriteMapRows: Int ): PlayerEntity(name, maxHealth) {

    object EnemySerializer : KSerializer<Enemy> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Enemy", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Enemy) {
            encoder.encodeString(value.getName())
            encoder.encodeDouble(value.getHealth())
            encoder.encodeString(value.getSpriteFileLoc())
            encoder.encodeInt(value.getSpriteWidth())
            encoder.encodeInt(value.getSpriteHeight())
            encoder.encodeInt(value.getSpriteMapCols())
            encoder.encodeInt(value.getSpriteMapRows())
        }

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
}