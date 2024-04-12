package net.catstack.roguelike.game.scenes.game

import net.catstack.roguelike.engine.scenes.Layer
import net.catstack.roguelike.game.entities.Cell
import net.catstack.roguelike.game.entities.Player
import org.joml.Vector2f

class GameLayer : Layer(20) {
    override fun onCreate() {
        for (i in 0..90)
            for (j in 0..35) {
                var cell = Cell()
                cell.sprite.position = Vector2f(i.toFloat(), j.toFloat())

                gameObjects.add(cell)
            }
        gameObjects.add(Player())
    }
}