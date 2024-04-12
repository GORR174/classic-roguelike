package net.catstack.roguelike.game.scenes.game

import net.catstack.roguelike.engine.level.MapLoader
import net.catstack.roguelike.engine.scenes.Layer

class GameLayer : Layer(20) {
    override fun onCreate() {
        MapLoader(gameObjects).loadLevel("test")
    }
}