package com.yuiwai.gdxs

import com.badlogic.gdx.graphics.g2d.{GlyphLayout, SpriteBatch}
import com.yuiwai.gdxs.component.{Button, ButtonStyle, Clip, Column, Label, LabelStyle, Row, Table, Tiled}
import com.yuiwai.gdxs.style.{NoStyle, Style}

package object renderer {
  implicit val labelRenderer: Renderer[Label, LabelStyle] = new Renderer[Label, LabelStyle] {
    private val layout = new GlyphLayout()
    override def render(label: Label, style: LabelStyle, region: Region)(implicit batch: SpriteBatch): Unit = {
      import region.area.{pos, size}

      // background
      Renderer.renderBackground(region, style)

      layout.setText(style.font, label.text)
      // TODO alignのスタイル対応
      style.font.draw(
        batch, label.text,
        pos.x + (size.width - layout.width) / 2,
        pos.y + layout.height + (size.height - layout.height) / 2)
    }
  }
  implicit def buttonRenderer[A]: Renderer[Button[A], ButtonStyle] = new Renderer[Button[A], ButtonStyle] {
    override def render(button: Button[A], style: ButtonStyle, region: Region)(implicit batch: SpriteBatch): Unit = {
      // background
      Renderer.renderBackground(region, style)
    }
  }
  implicit val tiledRenderer: Renderer[Tiled, NoStyle.type] = new Renderer[Tiled, NoStyle.type] {
    override def render(tiled: Tiled, style: NoStyle.type, region: Region)(implicit batch: SpriteBatch): Unit = {
      tiled.fillPositions.zipWithIndex.foreach {
        case (bool, index) if bool =>
          val y = index / tiled.fillWidth
          val x = index % tiled.fillWidth
          batch.draw(
            tiled.tileTexture,
            region.x + tiled.tileSize.width * x,
            region.y + tiled.tileSize.height * y,
            tiled.tileSize.width,
            tiled.tileSize.height
          )
      }
    }
  }
  implicit val clipRenderer: Renderer[Clip, NoStyle.type] = new Renderer[Clip, NoStyle.type] {
    override def render(clip: Clip, style: NoStyle.type, region: Region)(implicit batch: SpriteBatch): Unit = {
      clip.sprite.setPosition(clip.pos.x, clip.pos.y)
      clip.sprite.draw(batch)
    }
  }
  implicit val tableRenderer: Renderer[Table, NoStyle.type] = new Renderer[Table, NoStyle.type] {
    override def render(table: Table, style: NoStyle.type, region: Region)(implicit batch: SpriteBatch): Unit = {
      val numOfRows = table.rows.size
      val numOfCols = table.rows.map(_.columns.size).max
      val colHeight = region.height / numOfRows
      val colWidth = region.width / numOfCols

      def renderRow(row: Row): Unit = {
        row.columns.foreach(col => renderCol(col))
      }

      def renderCol(col: Column): Unit = {
        Renderer.render(Seq(col.child))
      }

      table.rows.foreach(row => renderRow(row))
    }
  }
}
