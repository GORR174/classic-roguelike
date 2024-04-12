package net.catstack.roguelike.engine

import mu.KotlinLogging
import net.catstack.engine.viewports.FitViewport
import net.catstack.engine.viewports.StretchViewport
import net.catstack.engine.viewports.Viewport
import net.catstack.roguelike.engine.input.KeyListener
import net.catstack.roguelike.engine.scenes.Game
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL
import java.lang.IllegalStateException

private val log = KotlinLogging.logger {}

class Window {
    var width = 1280
    var height = 720

    var glfwWindow: Long = -1
        private set

    var title = "CatEngine"
        set(value) {
            field = value

            if (glfwWindow != -1L) {
                glfwSetWindowTitle(glfwWindow, value)
            }
        }

    private var dt = -1f

    lateinit var game: Game
        private set

    var viewport: Viewport = FitViewport(16 / 9f)

    fun destroy() {
        // Free The memory
        glfwFreeCallbacks(glfwWindow)
        glfwDestroyWindow(glfwWindow)

        // Terminate GLFWW and the free the error callbacks
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()

        log.info { "Game has closed" }
    }

    fun close() {
        glfwSetWindowShouldClose(glfwWindow, true)
    }

    fun init(game: Game, title: String = "CatEngine", width: Int = 1280, height: Int = 720) {
        this.width = width
        this.height = height
        this.title = title
        this.game = game

        GLFWErrorCallback.createPrint(System.err).set()

        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        // Configure GLFW
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE)

        // Create the window
        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL)
        if (glfwWindow == NULL) {
            throw IllegalStateException("Failed to create the GLFW window")
        }

        glfwSetKeyCallback(glfwWindow) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            KeyListener.keyCallback(window, key, scancode, action, mods)
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow)
        // Enable v-sync
        glfwSwapInterval(1)

        // Make the window visible
        glfwShowWindow(glfwWindow)

        GL.createCapabilities()

        glfwSetWindowSizeCallback(glfwWindow) { window: Long, width: Int, height: Int ->
            this.width = width
            this.height = height

            game.onResize(width, height)

            val viewportBounds = viewport.getBounds(width, height)
            glViewport(viewportBounds.x, viewportBounds.y, viewportBounds.z, viewportBounds.w)
        }

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }

    fun loop() {
        var beginTime = glfwGetTime().toFloat()
        var endTime: Float

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents()

            glClearColor(0f, 0f, 0f, 1f)
            glClear(GL_COLOR_BUFFER_BIT)

            if (dt >= 0) {
                // TODO: 19.08.2021 CAP DELTA
                game.update(dt)

                glfwSwapBuffers(glfwWindow)
            }

            endTime = glfwGetTime().toFloat()
            dt = endTime - beginTime
            beginTime = endTime
        }

        game.dispose()
    }
}