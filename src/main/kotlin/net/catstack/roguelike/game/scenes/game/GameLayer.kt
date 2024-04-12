package net.catstack.roguelike.game.scenes.game

import net.catstack.roguelike.engine.level.MapLoader
import net.catstack.roguelike.engine.scenes.Layer
import net.catstack.roguelike.game.entities.Cell
import net.catstack.roguelike.game.entities.Player
import org.joml.Vector2f

class GameLayer : Layer(20) {
    override fun onCreate() {
        MapLoader(gameObjects).loadLevel()
    }
}