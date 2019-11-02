package com.yuiwai.gdxs

import com.badlogic.gdx.graphics.g2d.{GlyphLayout, SpriteBatch}
import com.yuiwai.gdxs.component.{Button, Clip, Label, Tiled}

package object renderer {
  implicit val labelRenderer: Renderer[Label] = new Renderer[Label] {
    private val layout = new GlyphLayout()
    override def render(label: Label)(implicit batch: SpriteBatch): Unit = {
      import label.region.area.{pos, size}

      // background
      Renderer.renderBackground(label.region, label.style)

      layout.setText(label.style.font, label.text)
      // TODO alignのスタイル対応
      label.style.font.draw(
        batch, label.text,
        pos.x + (size.width - layout.width) / 2,
        pos.y + layout.height + (size.height - layout.height) / 2)
    }
  }
  implicit def buttonRenderer[A]: Renderer[Button[A]] = new Renderer[Button[A]] {
    override def render(button: Button[A])(implicit batch: SpriteBatch): Unit = {
      // background
      Renderer.renderBackground(button.region, button.style)
    }
  }
  implicit val tiledRenderer: Renderer[Tiled] = new Renderer[Tiled] {
    override def render(tiled: Tiled)(implicit batch: SpriteBatch): Unit = {
      tiled.fillPositions.zipWithIndex.foreach {
        case (bool, index) if bool =>
          val y = index / tiled.fillWidth
          val x = index % tiled.fillWidth
          batch.draw(
            tiled.tileTexture,
            tiled.region.x + tiled.tileSize.width * x,
            tiled.region.y + tiled.tileSize.height * y,
            tiled.tileSize.width,
            tiled.tileSize.height
          )
      }
    }
  }
  implicit val clipRenderer = new Renderer[Clip] {
    override def render(clip: Clip)(implicit batch: SpriteBatch): Unit = {
      clip.sprite.setPosition(clip.pos.x, clip.pos.y)
      clip.sprite.draw(batch)
    }
  }
}
