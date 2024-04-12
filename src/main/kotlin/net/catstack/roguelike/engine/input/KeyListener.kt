package net.catstack.roguelike.engine.input

import org.lwjgl.glfw.GLFW

object KeyListener {
    private val keyPressed = Array(350) { false }
    private val justPressedTrigger = Array(350) { false }

    fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key <= 0 || key >= keyPressed.size)
            return
        if (action == GLFW.GLFW_PRESS) {
            keyPressed[key] = true
        } else if (action == GLFW.GLFW_RELEASE) {
            keyPressed[key] = false
        }
    }

    fun isKeyPressed(keyCode: Int) = keyPressed[keyCode]

    fun isKeyJustPressed(keyCode: Int): Boolean {
        if (!justPressedTrigger[keyCode] && isKeyPressed(keyCode)) {
            justPressedTrigger[keyCode] = true
            return true
        }

        if (justPressedTrigger[keyCode] && !isKeyPressed(keyCode)) {
            justPressedTrigger[keyCode] = false
        }
        return false
    }
}