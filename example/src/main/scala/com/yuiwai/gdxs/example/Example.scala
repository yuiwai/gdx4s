package com.yuiwai.gdxs.example

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.{ApplicationListener, Gdx}
import com.yuiwai.gdxs.component.{Button, Column, Label, Table, VLayout}
import com.yuiwai.gdxs.event.EventHandler
import com.yuiwai.gdxs.renderer.Renderer
import com.yuiwai.gdxs.style.{BitmapFontStyle, NoBackgroundStyle}
import com.yuiwai.gdxs.view.ViewProfile
import com.yuiwai.gdxs._

object Example {
  def main(arg: Array[String]): Unit = {
    val config = new LwjglApplicationConfiguration()
    config.title = "example"
    new LwjglApplication(new Adapter(), config)
  }
}

sealed trait Action
case object ShowDialog extends Action

class Adapter() extends ApplicationListener {
  self =>
  private lazy val camera = new OrthographicCamera()
  private lazy val viewport = new FitViewport(600, 300, camera)
  private lazy val pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888)
  private lazy val texture = new Texture(pixmap)
  private lazy val fontStyle = new BitmapFontStyle with NoBackgroundStyle {
    override val font: BitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"))
  }
  private lazy val viewProfile = new ViewProfile {
    override val camera: Camera = self.camera
    override val components: Seq[component.Component] = Seq(
      Table(FixedRegion(Area(Pos(0, 0), Size(200, 80)))) {
        _.appendRow(Column(Label("test1", fontStyle)), Column(Label("foo", fontStyle)))
          .appendRow(Column(Label("test2", fontStyle)), Column(Label("bar", fontStyle)))
      },
      Button(texture, FixedRegion(Area(Pos(0, 0), Size(50, 50))), ShowDialog),
      VLayout(
        FixedRegion(Area(Pos(-100, -50), Size(100, 100))),
        Seq.fill(4)(Button(texture, FixedRegion(Size(20, 20)), ShowDialog))
      )
    )
  }
  private lazy implicit val batch = new SpriteBatch()
  def actionHandler(action: Action): Unit = action match {
    case a => println(s"action: $a")
  }
  override def create(): Unit = {
    pixmap.setColor(Color.BLUE)
    pixmap.fillCircle(50, 50, 40)
    Gdx.input.setInputProcessor(new EventHandler[Action] {
      override protected val actionHandler: Action => Unit = self.actionHandler
      override protected val viewProfile: ViewProfile = self.viewProfile
    })
  }
  override def resize(width: Int, height: Int): Unit = {
    viewport.update(width, height)
  }
  override def render(): Unit = {
    batch.setProjectionMatrix(camera.combined)
    Gdx.gl.glClearColor(1, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    batch.begin()
    Renderer.render(viewProfile)
    batch.end()
  }
  override def pause(): Unit = {}
  override def resume(): Unit = {}
  override def dispose(): Unit = {}
}
