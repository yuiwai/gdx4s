package com.yuiwai.gdxs.event

import com.badlogic.gdx.InputProcessor
import com.yuiwai.gdxs.Pos
import com.yuiwai.gdxs.component.Button
import com.yuiwai.gdxs.view.ViewProfile

trait EventHandler[A] extends InputProcessor {
  protected val actionHandler: A => Unit
  protected val viewProfile: ViewProfile
  protected def screenToLocal(pos: Pos): Pos = Pos {
    viewProfile
      .camera
      .unproject(pos.toVector3)
  }
  lazy val buttons: Seq[Button[A]] = viewProfile.components.collect {
    case button: Button[A] => button
  }

  // FIXME 暫定ボタン実装
  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    val pos = screenToLocal(Pos(screenX, screenY))
    buttons.find(_.area.hitTest(pos)) match {
      case Some(button) =>
        actionHandler(button.action)
        true
      case None => false
    }
  }
}
