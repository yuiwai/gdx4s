package com.yuiwai.gdxs

import com.yuiwai.gdxs.style.{BackgroundStyle, FontStyle, Style}

package object component {
  sealed trait Component {
    val style: Style
    val region: Region
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

  // Container
  sealed trait Container extends Component {
    val children: Seq[Component]
  }
  /*
  final case class AbsoluteContainer(children: Seq[Component], mapping: Map[Component, Vector2]) extends Container
  final case class VerticalContainer(children: Seq[Component]) extends Container
  */
}
