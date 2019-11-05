package com.yuiwai.example

import com.badlogic.gdx.{ApplicationListener, Gdx}
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{Camera, Color, GL20, OrthographicCamera, Pixmap, Texture}
import com.badlogic.gdx.utils.viewport.FitViewport
import com.yuiwai.gdxs.{Area, FixedRegion, Pos, Size, component}
import com.yuiwai.gdxs.component.Button
import com.yuiwai.gdxs.event.EventHandler
import com.yuiwai.gdxs.renderer.Renderer
import com.yuiwai.gdxs.view.ViewProfile

object Example {
  def main(arg: Array[String]): Unit = {
    val config = new LwjglApplicationConfiguration()
    config.title = "example"
    new LwjglApplication(new Adapter(), config)
  }
}

sealed trait Action
case object ButtonAction extends Action

class Adapter() extends ApplicationListener { self =>
  private lazy val camera = new OrthographicCamera()
  private lazy val viewport = new FitViewport(600, 300, camera)
  private lazy val pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888)
  private lazy val texture = new Texture(pixmap)
  private lazy val viewProfile = new ViewProfile {
    override val camera: Camera = self.camera
    override val components: Seq[component.Component] =
      Button(texture, new FixedRegion(Area(Pos(0, 0), Size(50, 50))), ButtonAction)::Nil
  }
  private lazy implicit val batch = new SpriteBatch()
  override def create(): Unit = {
    pixmap.setColor(Color.BLUE)
    pixmap.fillCircle(50, 50, 40)
    Gdx.input.setInputProcessor(new EventHandler[Action] {
      override protected val actionHandler: Action => Unit = println
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
    // batch.draw(texture, 0, 0, 100, 100)
    batch.end()
  }
  override def pause(): Unit = {}
  override def resume(): Unit = {}
  override def dispose(): Unit = {}
}
