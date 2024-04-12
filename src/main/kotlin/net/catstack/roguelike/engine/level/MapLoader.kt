package net.catstack.roguelike.engine.level

import net.catstack.roguelike.engine.util.Resources
import net.catstack.roguelike.game.entities.*

class MapLoader(val gameObjects: ArrayList<GameObject>) {
    fun loadLevel(mapName: String) {
        var map = Resources.getTextFromResource("assets/maps/$mapName.map")
        val mapLines = map.lines()
        val linesSize = mapLines.size

        mapLines.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                val y = linesSize - y - 1
                gameObjects.add(Cell(x, y))
            }
        }

        spawnObjects(map, '|') { x, y -> Fence('|', x, y) }
        spawnObjects(map, '-') { x, y -> Fence('-', x, y) }
        spawnObjects(map, '/') { x, y -> Fence('/', x, y) }
        spawnObjects(map, '\\') { x, y -> Fence('\\', x, y) }
        spawnObjects(map, '#') { x, y -> Brick(x, y) }
        spawnObjects(map, '1') { x, y -> Shop(x, y) }

        spawnObjects(map, '>') { x, y -> LadderDown(x, y) }
        spawnObjects(map, '<') { x, y -> LadderUp(x, y) }

        spawnObjects(map, '@') { x, y -> Player(x, y) }
    }

    private fun spawnObjects(map: String, spawnSymbol: Char, supplier: (Int, Int) -> GameObject) {
        val mapLines = map.lines()
        val linesSize = mapLines.size

        mapLines.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                val y = linesSize - y - 1
                if (symbol == spawnSymbol) {
                    gameObjects.add(supplier(x, y))
                }
            }
        }
    }
}