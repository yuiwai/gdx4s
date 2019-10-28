package com.yuiwai

import com.badlogic.gdx.math.Vector3

package object gdxs {
  final case class Pos(x: Float, y: Float) {
    def toVector3: Vector3 = new Vector3(x, y, 0)
  }
  object Pos {
    def apply(vector3: Vector3): Pos = apply(vector3.x, vector3.y)
  }
  final case class Size(width: Float, height: Float)
  final case class Area(pos: Pos, size: Size) {
    def left: Float = pos.x
    def top: Float = pos.y
    def center: Pos = Pos(pos.x + size.width / 2, pos.y + size.height / 2)
    def width: Float = size.width
    def height: Float = size.height
    def hitTest(p: Pos): Boolean =
      p.x >= pos.x && p.x <= pos.x + size.width && p.y >= pos.y && p.y <= pos.y + size.height
  }

  sealed trait Region {
    val area: Area
    def x: Float = area.pos.x
    def y: Float = area.pos.y
    def width: Float = area.size.width
    def height: Float = area.size.height
  }
  final case class FixedRegion(area: Area) extends Region
}
