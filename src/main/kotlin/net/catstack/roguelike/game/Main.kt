package net.catstack.roguelike.game

import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.scenes.Game
import net.catstack.roguelike.game.scenes.game.GameScene

class MyGame : Game() {
    override fun onCreate() {
        super.onCreate()

        scene = GameScene()
    }
}

fun main(args: Array<String>) {
    Engine.run(MyGame(), "Colevia Kingdom")
}