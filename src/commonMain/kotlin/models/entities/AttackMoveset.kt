package models.entities

import kotlinx.serialization.Serializable

@Serializable()
class AttackMoveset(private var attackPatterns: Array<AttackPattern>) {

    fun getNumberOfPatterns(): Int {
        return attackPatterns.size
    }

    fun getAttackPatterns(): Array<AttackPattern> {
        return attackPatterns
    }

}