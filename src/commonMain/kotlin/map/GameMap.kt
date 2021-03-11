package map

class GameMap(
        val world : Int,
        //var background : SomeDrawableAssetClassHere,
        var levels : List<Placemarker>
) {
    override fun toString(): String {
        var levelStrings = ""
        for (pm in this.levels) levelStrings += pm.toString() + "\n"
        return "World ${this.world}\nLevels:\n$levelStrings"
    }
}