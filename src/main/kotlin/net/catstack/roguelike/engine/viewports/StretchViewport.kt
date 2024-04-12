package net.catstack.engine.viewports

import org.joml.Vector4i

class StretchViewport : Viewport() {
    override fun getBounds(width: Int, height: Int): Vector4i = Vector4i(0, 0, width, height)
}