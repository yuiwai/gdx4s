package com.yuiwai.gdxs.renderer

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.yuiwai.gdxs.Region
import com.yuiwai.gdxs.component.{Button, Component}
import com.yuiwai.gdxs.style.{BackgroundStyle, FitTextureBackgroundStyle, NoBackgroundStyle}
import com.yuiwai.gdxs.view.ViewProfile

trait Renderer[C <: Component] {
  def render(component: C)(implicit batch: SpriteBatch): Unit
}
object Renderer {
  def render(viewProfile: ViewProfile)(implicit batch: SpriteBatch): Unit = render(viewProfile.components)
  def render(components: Seq[Component])(implicit batch: SpriteBatch): Unit = {
    components.foreach {
      case button: Button[_] => render(button)
      case _ =>
    }
  }
  def render[C <: Component](component: C)(implicit batch: SpriteBatch, renderer: Renderer[C]): Unit = {
    renderer.render(component)
  }
  def renderBackground(region: Region, style: BackgroundStyle)(implicit batch: SpriteBatch): Unit = style match {
    case s: FitTextureBackgroundStyle =>
      batch.draw(s.backgroundTexture, region.x, region.y, region.width, region.height)
    case _: NoBackgroundStyle =>
  }
}
