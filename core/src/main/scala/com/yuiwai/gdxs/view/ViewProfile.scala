package com.yuiwai.gdxs.view

import com.badlogic.gdx.graphics.Camera
import com.yuiwai.gdxs.component.Component

trait ViewProfile {
  val camera: Camera
  val components: Seq[Component]
}

final case class CompositeViewProfile(
  first: ViewProfile,
  second: ViewProfile
) extends ViewProfile {
  override val camera: Camera = first.camera
  override val components: Seq[Component] = first.components ++ second.components
}

final case class StackedViewProfile(
  current: ViewProfile,
  rest: List[ViewProfile]
) extends ViewProfile {
  override val camera: Camera = current.camera
  override val components: Seq[Component] = current.components
  def pop: Option[StackedViewProfile] = rest match {
    case head :: tail => Some(StackedViewProfile(head, tail))
    case _ => None
  }
}
