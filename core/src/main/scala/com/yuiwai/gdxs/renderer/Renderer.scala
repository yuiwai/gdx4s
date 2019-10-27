package com.yuiwai.gdxs.renderer

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.yuiwai.gdxs.Region
import com.yuiwai.gdxs.component.Component
import com.yuiwai.gdxs.style.{BackgroundStyle, FitTextureBackgroundStyle}

trait Renderer[C <: Component] {
  def render(component: C)(implicit batch: SpriteBatch): Unit
}
object Renderer {
  def render[C <: Component](component: C)(implicit batch: SpriteBatch, renderer: Renderer[C]): Unit = {
    renderer.render(component)
  }
  def renderBackground(region: Region, style: BackgroundStyle)(implicit batch: SpriteBatch): Unit = style match {
    case s: FitTextureBackgroundStyle =>
      batch.draw(s.backgroundTexture, region.x, region.y, region.width, region.height)
  }
}
