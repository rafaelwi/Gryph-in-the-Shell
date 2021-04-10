package models.entities

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

/** Holds the score accquired in a level **/
@Serializable(with = LevelScore.LevelScoreSerializer::class)
class LevelScore(private var world: Int,
                 private var level: Int,
                 private var time: Double) {
    /** Serialization/deserialization to JSON **/
    object LevelScoreSerializer : KSerializer<LevelScore> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("LevelScore") {
            element<Int>("world")
            element<Int>("level")
            element<Double>("time")
        }

        override fun serialize(encoder: Encoder, value: LevelScore) {
            encoder.encodeStructure(descriptor) {
                encodeIntElement(descriptor, 0, value.getWorld())
                encodeIntElement(descriptor, 1, value.getLevel())
                encodeDoubleElement(descriptor, 2, value.getScore())
            }
        }

        override fun deserialize(decoder: Decoder): LevelScore {
            return decoder.decodeStructure(descriptor) {
                var world: Int? = null
                var level: Int? = null
                var time: Double? = null

                loop@ while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        CompositeDecoder.DECODE_DONE -> break@loop

                        0 -> world = decodeIntElement(descriptor, 0)
                        1 -> level = decodeIntElement(descriptor, 1)
                        2 -> time = decodeDoubleElement(descriptor, 2)

                        else -> throw SerializationException("Unexpected index $index")
                    }
                }

                LevelScore(
                        requireNotNull(world),
                        requireNotNull(level),
                        requireNotNull(time)
                )
            }
        }
    }

    fun getScore(): Double {
        return time
    }

    fun getLevel(): Int {
        return level
    }

    fun getWorld(): Int {
        return world
    }

    override fun toString(): String {
        return "World: $world, Level: $level, Score: $time"
    }
}
