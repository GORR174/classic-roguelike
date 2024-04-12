package net.catstack.roguelike.engine.scenes

import net.catstack.engine.render.SpriteBatch
import net.catstack.roguelike.game.entities.GameObject

abstract class Layer(var fontSize: Int) {
    val spriteBatch = SpriteBatch(fontSize)
    val gameObjects = ArrayList<GameObject>()

    fun create() {
        onCreate()

        gameObjects.forEach(GameObject::create)
    }

    open fun onCreate() {

    }

    open fun draw(spriteBatch: SpriteBatch) {

    }

    fun update(delta: Float) {
        gameObjects.forEach(GameObject::update)

        spriteBatch.begin()
        draw(spriteBatch)
        gameObjects.forEach { it.draw(spriteBatch) }
        spriteBatch.end()
    }
}