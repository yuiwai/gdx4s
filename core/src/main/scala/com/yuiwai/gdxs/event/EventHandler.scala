package com.yuiwai.gdxs.event

import com.badlogic.gdx.InputProcessor
import com.yuiwai.gdxs.{Pos, Region}
import com.yuiwai.gdxs.component.{Button, Component, Layout}
import com.yuiwai.gdxs.view.ViewProfile

trait EventHandler[A] extends InputProcessor {
  protected val actionHandler: A => Unit
  protected val viewProfile: ViewProfile
  protected def screenToLocal(pos: Pos): Pos = Pos {
    viewProfile
      .camera
      .unproject(pos.toVector3)
  }
  lazy val buttons: Seq[(Button[A], Region)] = findButtons(viewProfile.components)
  private def findButtons(components: Seq[Component]): Seq[(Button[A], Region)] =
    components
      .map(c =>  c -> c.cell)
      .flatMap {
        case (c, b: Button[A]) => Seq(b -> c.region)
        case (_, l: Layout) => findButtons(l.children)
        case _ => Seq.empty
      }
  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    val pos = screenToLocal(Pos(screenX, screenY))
    buttons.find(_._2.area.hitTest(pos)) match {
      case Some(c) =>
        actionHandler(c._1.action)
        true
      case None => false
    }
  }

  // TODO 以下のイベントで必要なものを実装する
  override def keyDown(keycode: Int): Boolean = false
  override def keyUp(keycode: Int): Boolean = false
  override def keyTyped(character: Char): Boolean = false
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false
  override def scrolled(amount: Int): Boolean = false
}
