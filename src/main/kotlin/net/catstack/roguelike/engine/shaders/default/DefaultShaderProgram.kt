package net.catstack.engine.shaders.default

import net.catstack.engine.shaders.ShaderProgram

class DefaultShaderProgram : ShaderProgram("default/DefaultVertex.glsl", "default/DefaultFragment.glsl") {
    override fun setDefaultUniforms() {

    }
}