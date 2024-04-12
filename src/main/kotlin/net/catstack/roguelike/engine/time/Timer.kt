package net.catstack.roguelike.engine.time

open class Timer(val endTime: Float, var isInfinite: Boolean = false, var action: () -> Unit) {
    var currentTime: Float = 0f
        get
        private set

    var hasEnded = true
        get
        private set

    open fun start() {
        hasEnded = false
    }

    open fun update(delta: Float) {
        if (!hasEnded) {
            currentTime += delta

            if (currentTime >= endTime) {
                if (isInfinite) {
                    currentTime -= endTime
                } else {
                    hasEnded = true
                    currentTime = 0f
                }

                action()
            }
        }
    }
}