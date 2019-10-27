package com.yuiwai.gdxs

import com.badlogic.gdx.graphics.g2d.{GlyphLayout, SpriteBatch}
import com.yuiwai.gdxs.component.Label

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
}
