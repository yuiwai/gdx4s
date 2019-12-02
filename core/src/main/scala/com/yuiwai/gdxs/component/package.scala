package com.yuiwai.gdxs

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.yuiwai.gdxs.drawing.Drawing
import com.yuiwai.gdxs.style._

package object component {
  sealed trait ComponentCell
  case object EmptyCell extends ComponentCell

  sealed trait Component {
    val cell: ComponentCell
    val style: Style
    val region: Region
    def area: Area = region.area
    def modRegion(f: Region => Region): Component
  }
  final case class ComponentImpl(
    cell: ComponentCell,
    style: Style,
    region: Region) extends Component {
    override def modRegion(f: Region => Region): Component = copy(region = f(region))
  }
  case object EmptyComponent extends Component {
    override val cell: ComponentCell = EmptyCell
    override val style: Style = NoStyle
    override val region: Region = NoRegion
    override def modRegion(f: Region => Region): Component = this
  }

  // Label
  type LabelStyle = FontStyle with BackgroundStyle
  trait Label extends ComponentCell {
    val text: String
  }
  object Label {
    def apply(labelText: String, labelStyle: LabelStyle, labelRegion: Region = NoRegion): Component =
      ComponentImpl(
        new Label {
          override val text: String = labelText
        },
        labelStyle,
        labelRegion
      )
  }

  // Button
  type ButtonStyle = BackgroundStyle
  trait Button[A] extends ComponentCell {
    val action: A
  }
  object Button {
    def apply[A](buttonStyle: ButtonStyle, buttonRegion: Region, buttonAction: A): Component =
      ComponentImpl(
        new Button[A] {
          override val action: A = buttonAction
        },
        buttonStyle,
        buttonRegion
      )
    def apply[A](texture: Texture, buttonRegion: Region, buttonAction: A): Component =
      ComponentImpl(
        new Button[A] {
          override val action: A = buttonAction
        },
        new FitTextureBackgroundStyle {
          override val backgroundTexture: Texture = texture
        },
        buttonRegion
      )
    def apply[A](labelText: String, labelStyle: LabelStyle, buttonRegion: Region, buttonAction: A): Component =
      ComponentImpl(
        new Label with Button[A] {
          override val action: A = buttonAction
          override val text: String = labelText
        },
        labelStyle,
        buttonRegion
      )
  }

  // Clip
  trait Clip extends ComponentCell {
    val pos: Pos
    val sprite: Sprite
  }
  object Clip {
    def apply(clipPos: Pos, clipSprite: Sprite): Component =
      ComponentImpl(
        new Clip {
          override val pos: Pos = clipPos
          override val sprite: Sprite = clipSprite
        },
        NoStyle,
        clipSprite.region
      )
  }
  // TODO 置き場を考える
  implicit class SpriteWrap(sprite: Sprite) {
    def region: Region = FixedRegion(Area(Pos(sprite.getX, sprite.getY), Size(sprite.getWidth, sprite.getHeight)))
  }

  // Tiled
  trait Tiled extends ComponentCell {
    val tileTexture: Texture
    val tileSize: Size
    val fillWidth: Int
    val fillPositions: Seq[Boolean]
  }
  object Tiled {
    def apply(
      fillPos: Pos,
      fillTileSize: Size,
      fillTileTexture: Texture,
      tiledFillWidth: Int,
      tiledFillPositions: Seq[Boolean]): Component =
      ComponentImpl(
        new Tiled {
          override val tileTexture: Texture = fillTileTexture
          override val tileSize: Size = fillTileSize
          override val fillWidth: Int = tiledFillWidth
          override val fillPositions: Seq[Boolean] = tiledFillPositions
        },
        NoStyle,
        FixedRegion(Area(fillPos,
          Size(tiledFillWidth * fillTileSize.width, tiledFillPositions.size / tiledFillWidth * fillTileSize.height)))
      )
  }

  // Arrow
  // trait Arrow extends Component {
  // }

  // Canvas
  trait Canvas extends ComponentCell {
    val drawings: Seq[Drawing]
  }
  object Canvas {
    def apply(canvasDrawings: Seq[Drawing]): Component =
      ComponentImpl(
        new Canvas {
          override val drawings: Seq[Drawing] = canvasDrawings
        },
        NoStyle,
        RelativeRegion(Pos.zero)
      )
  }

  // Container
  sealed trait Container extends Component {
    val children: Seq[Component]
  }
  /*
  final case class AbsoluteContainer(children: Seq[Component], mapping: Map[Component, Vector2]) extends Container
  final case class VerticalContainer(children: Seq[Component]) extends Container
  */

  sealed trait Table extends ComponentCell {
    self =>
    val rows: Seq[Row]
    def modRows(f: Seq[Row] => Seq[Row]): Table = new Table {
      override val rows: Seq[Row] = f(self.rows)
    }
    def appendRow(row: Row): Table = modRows(_ :+ row)
    def appendRow(col: Column, cols: Column*): Table =
      appendRow(Row(col +: cols).cell.asInstanceOf[Row])
  }
  object Table {
    def apply(tableRegion: Region): Component =
      ComponentImpl(
        new Table {
          override val rows: Seq[Row] = Seq.empty
        },
        NoStyle, // FIXME style
        tableRegion)
  }
  sealed trait Row extends ComponentCell {
    val columns: Seq[Column]
  }
  object Row {
    def empty: Component = apply(Seq.empty)
    def apply(cols: Seq[Column]): Component =
      ComponentImpl(
        new Row {
          override val columns: Seq[Column] = cols
        },
        NoStyle, // FIXME style
        NoRegion
      )
  }
  sealed trait Column extends ComponentCell {
    val child: Component
  }
  object Column {
    def empty: Component = apply(EmptyComponent)
    def apply(component: Component): Component =
      ComponentImpl(
        new Column {
          override val child: Component = component
        },
        NoStyle, // FIXME style
        NoRegion
      )
  }
}
