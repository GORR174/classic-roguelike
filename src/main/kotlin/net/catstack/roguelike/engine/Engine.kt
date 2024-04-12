package net.catstack.roguelike.engine

import mu.KotlinLogging
import net.catstack.roguelike.engine.asset.AssetPool
import net.catstack.roguelike.engine.scenes.Game
import org.lwjgl.Version

private val log = KotlinLogging.logger {}

object Engine {
    val window = Window()
    val assets = AssetPool()

    lateinit var game: Game
        private set

    private fun loadResources() {

    }

    fun run(game: Game, title: String = "CatEngine", width: Int = 1280, height: Int = 720) {
        this.game = game

        showInfo()

        window.init(game, title, width, height)

        loadResources()

        game.onCreate()
        window.loop()

        window.destroy()
    }

    fun close() {
        window.close()
    }

    private fun showInfo() {
        log.info { "---------------------------" }
        log.info { " _._     _,-'\"\"`-._" }
        log.info { "(,-.`._,'(       |\\`-/|" }
        log.info { "    `-.-' \\ )-`-( , o o)" }
        log.info { "          `-    \\`_`\"'-" }
        log.info { "---------------------------" }
        log.info { "LWJGL ${Version.getVersion()}" }
        log.info { "---------------------------" }
    }
}