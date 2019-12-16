package com.yuiwai.gdxs.assets

import com.badlogic.gdx.graphics.Texture

final case class Assets(textures: Map[String, Texture])

sealed trait Resource {
  val name: String
  val extension: Extension
}
final case class TextureResource(name: String, extension: Extension) extends Resource

sealed trait Extension
case object PNG extends Extension

