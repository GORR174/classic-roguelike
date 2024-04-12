package net.catstack.engine.viewports

import org.joml.Vector4i
import org.lwjgl.opengl.GL11

class FitViewport(val aspectRatio: Float) : Viewport() {
    override fun getBounds(width: Int, height: Int): Vector4i {
        return if (width.toFloat() / height > aspectRatio) {
            val newWidth = (height * (16f / 9)).toInt()

            Vector4i((width - newWidth) / 2, 0, newWidth, height)
        } else {
            val newHeight = (width / (16f / 9)).toInt()

            Vector4i(0, (height - newHeight) / 2, width, newHeight)
        }
    }
}