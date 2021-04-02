package models.entities

import com.soywiz.klock.TimeSpan
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@Serializable(with = AttackPattern.AttackPatternSerializer::class)
class AttackPattern(private var attackDamage: Double,
                    private var timeUntilInitiate: Double,
                    private var totalCycles: Int,
                    private var timeBetweenCycles: Double) {

    object AttackPatternSerializer : KSerializer<AttackPattern> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("AttackPattern") {
            element<Double>("attackDamage")
            element<Double>("timeUntilInitiate")
            element<Int>("totalCycles")
            element<Double>("timeBetweenCycles")
        }

        override fun serialize(encoder: Encoder, value: AttackPattern) {
            encoder.encodeStructure(descriptor) {
                encodeDoubleElement(descriptor, 0, value.getDamage())
                encodeDoubleElement(descriptor, 1, value.getTimeUntilInitiatePrim())
                encodeIntElement(descriptor, 2, value.getTotalCycles())
                encodeDoubleElement(descriptor, 3, value.getTimeBetweenCyclesPrim())
            }
        }

        override fun deserialize(decoder: Decoder): AttackPattern {
            return decoder.decodeStructure(descriptor) {
                var attackDamage: Double? = null
                var timeUntilInitiate: Double? = null
                var totalCycles: Int? = null
                var timeBetweenCycles: Double? = null

                loop@ while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        DECODE_DONE -> break@loop

                        0 -> attackDamage = decodeDoubleElement(descriptor, 0)
                        1 -> timeUntilInitiate = decodeDoubleElement(descriptor, 1)
                        2 -> totalCycles = decodeIntElement(descriptor, 2)
                        3 -> timeBetweenCycles = decodeDoubleElement(descriptor, 3)

                        else -> throw SerializationException("Unexpected index $index")
                    }
                }

                AttackPattern(
                        requireNotNull(attackDamage),
                        requireNotNull(timeUntilInitiate),
                        requireNotNull(totalCycles),
                        requireNotNull(timeBetweenCycles)
                )
            }
        }
    }

    fun getTimeUntilInitiate(): TimeSpan {
        return TimeSpan(timeUntilInitiate)
    }

    fun getTimeUntilInitiatePrim(): Double {
        return timeUntilInitiate
    }

    fun getDamage(): Double {
        return attackDamage
    }

    fun getTotalCycles(): Int {
        return totalCycles
    }

    fun getTimeBetweenCycles(): TimeSpan {
        return TimeSpan(timeBetweenCycles)
    }

    fun getTimeBetweenCyclesPrim(): Double {
        return timeBetweenCycles
    }

    fun setDamage(newDamage: Double) {
        attackDamage = newDamage
    }
}