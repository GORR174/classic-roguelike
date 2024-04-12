package net.catstack.engine.viewports

import org.joml.Vector4i

abstract class Viewport {
    abstract fun getBounds(width: Int, height: Int): Vector4i
}