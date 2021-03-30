package models.entities

class AttackMoveset(private var attackPatterns: Array<AttackPattern>) {



    fun getAttackPatterns(): Array<AttackPattern> {
        return attackPatterns
    }


}