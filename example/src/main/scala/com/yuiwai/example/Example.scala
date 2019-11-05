package com.yuiwai.example

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

object Example {
  def main(arg: Array[String]): Unit = {
    val config = new LwjglApplicationConfiguration()
    config.title = "example"
    new LwjglApplication(new Adapter(), config)
  }
}

class Adapter() extends ApplicationListener {
  override def create(): Unit = {}
  override def resize(width: Int, height: Int): Unit = {}
  override def render(): Unit = {}
  override def pause(): Unit = {}
  override def resume(): Unit = {}
  override def dispose(): Unit = {}
}
