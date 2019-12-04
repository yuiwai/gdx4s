package com.yuiwai.gdxs.renderer

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.yuiwai.gdxs.Region
import com.yuiwai.gdxs.component.{Button, Clip, Component, ComponentCell, EmptyCell, EmptyComponent, Label, Table, Tiled}
import com.yuiwai.gdxs.style.{BackgroundStyle, FitTextureBackgroundStyle, NoBackgroundStyle, Style}
import com.yuiwai.gdxs.view.ViewProfile

trait Renderer[C <: ComponentCell, S <: Style] {
  def render(componentCell: C, style: S, region: Region)(implicit batch: SpriteBatch): Unit
}
object Renderer {
  def render(viewProfile: ViewProfile)(implicit batch: SpriteBatch): Unit = render(viewProfile.components)
  def render(components: Seq[Component])(implicit batch: SpriteBatch): Unit = {
    components.foreach { c =>
        c.cell match {
          case label: Label => render(label, c)
          case button: Button[_] => render(button, c)
          case clip: Clip => render(clip, c)
          case tiled: Tiled => render(tiled, c)
          case table: Table => render(table, c)
          case EmptyCell =>
        }
      }
    }
  def render[C <: ComponentCell, S <: Style](componentCell: C, component: Component)
    (implicit batch: SpriteBatch, renderer: Renderer[C, S]): Unit = {
    renderer.render(componentCell, component.style.asInstanceOf[S], component.region)
  }
  def renderBackground(region: Region, style: BackgroundStyle)(implicit batch: SpriteBatch): Unit = style match {
    case s: FitTextureBackgroundStyle =>
      batch.draw(s.backgroundTexture, region.x, region.y, region.width, region.height)
    case _: NoBackgroundStyle =>
  }
}
