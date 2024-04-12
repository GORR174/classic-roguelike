package net.catstack.roguelike.engine.scenes

import mu.KotlinLogging

private val log = KotlinLogging.logger {}

abstract class Scene {
    var hasBeenCreated = false
        private set

    val layers = ArrayList<Layer>()

    abstract fun onCreate()

    fun create() {
        hasBeenCreated = true
        onCreate()
        layers.forEach(Layer::create)
        log.info { "Scene ${this::class.simpleName} was created!" }
    }

    abstract fun update(delta: Float)

    open fun onResize(width: Int, height: Int) {

    }

    open fun dispose() {
        log.info { "Scene ${this::class.simpleName} was disposed!" }
    }

    companion object {
        val emptyScene = object : Scene() {
            override fun onCreate() {

            }

            override fun update(delta: Float) {

            }
        }
    }
}