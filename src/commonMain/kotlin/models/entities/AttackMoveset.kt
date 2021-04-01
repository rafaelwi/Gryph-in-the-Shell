package models.entities

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.Json

@Serializable()
class AttackMoveset(private var attackPatterns: Array<AttackPattern>) {

    /**
    object AttackMovesetSerializer : KSerializer<AttackMoveset> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("AttackMoveset") {
            element<Array<AttackPattern>>("attackPatterns")
        }

        override fun serialize(encoder: Encoder, value: AttackMoveset) {
            encoder.encodeStructure(descriptor) {
                Json.encodeToString(value.getAttackPatterns())
            }
        }

        override fun deserialize(decoder: Decoder): AttackMoveset {
            //deserialize an array element
            val ap = decoder.decodeString()
            val app = Json.decodeFromString<Array<AttackPattern>>(ap)
            return AttackMoveset(app)
        }
    } */

    fun getNumberOfPatterns(): Int {
        return attackPatterns.size
    }

    fun getAttackPatterns(): Array<AttackPattern> {
        return attackPatterns
    }

}