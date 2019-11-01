package com.yuiwai.gdxs

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.yuiwai.gdxs.drawing.Drawing
import com.yuiwai.gdxs.style._

package object component {
  sealed trait Component {
    val style: Style
    val region: Region
    def area: Area = region.area
  }

  // Label
  type LabelStyle = FontStyle with BackgroundStyle
  trait Label extends Component {
    val style: LabelStyle
    val text: String
  }
  object Label {
    def apply(labelText: String, labelStyle: LabelStyle, labelRegion: Region): Label = new Label {
      override val style: LabelStyle = labelStyle
      override val text: String = labelText
      override val region: Region = labelRegion
    }
  }

  // Button
  type ButtonStyle = BackgroundStyle
  trait Button[A] extends Component {
    val action: A
    val style: ButtonStyle
  }
  object Button {
    def apply[A](buttonStyle: ButtonStyle, buttonRegion: Region, buttonAction: A): Button[A] = new Button[A] {
      override val action: A = buttonAction
      override val style: ButtonStyle = buttonStyle
      override val region: Region = buttonRegion
    }
    def apply[A](texture: Texture, buttonRegion: Region, buttonAction: A): Button[A] = new Button[A] {
      override val action: A = buttonAction
      override val style: ButtonStyle = new FitTextureBackgroundStyle {
        override val backgroundTexture: Texture = texture
      }
      override val region: Region = buttonRegion
    }
    def apply[A](labelText: String, labelStyle: LabelStyle, buttonRegion: Region, buttonAction: A): Label with Button[A] =
      new Label with Button[A] {
        override val action: A = buttonAction
        override val text: String = labelText
        override val style: LabelStyle = labelStyle
        override val region: Region = buttonRegion
      }
  }

  // Clip
  trait Clip extends Component {
    val pos: Pos
    val sprite: Sprite
  }
  object Clip {
    def apply(clipPos: Pos, clipSprite: Sprite): Clip = new Clip {
      override val pos: Pos = clipPos
      override val sprite: Sprite = clipSprite
      override val style: Style = NoStyle
      override val region: Region = sprite.region
    }
  }
  // TODO 置き場を考える
  implicit class SpriteWrap(sprite: Sprite) {
    def region: Region = FixedRegion(Area(Pos(sprite.getX, sprite.getY), Size(sprite.getWidth, sprite.getHeight)))
  }

  // Arrow
  trait Arrow extends Component {
  }

  // Canvas
  trait Canvas extends Component {
    val drawings: Seq[Drawing]
  }
  object Canvas {
    def apply(canvasDrawings: Seq[Drawing]): Canvas = new Canvas {
      override val drawings: Seq[Drawing] = canvasDrawings
      override val style: Style = NoStyle
      override val region: Region = RelativeRegion(Pos.zero)
    }
  }

  // Container
  sealed trait Container extends Component {
    val children: Seq[Component]
  }
  /*
  final case class AbsoluteContainer(children: Seq[Component], mapping: Map[Component, Vector2]) extends Container
  final case class VerticalContainer(children: Seq[Component]) extends Container
  */
}
