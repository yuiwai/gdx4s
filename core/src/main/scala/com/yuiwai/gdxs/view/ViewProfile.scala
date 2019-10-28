package com.yuiwai.gdxs.view

import com.badlogic.gdx.graphics.Camera
import com.yuiwai.gdxs.component.Component

trait ViewProfile {
  val camera: Camera
  val components: Seq[Component]
}
