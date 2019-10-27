package com.yuiwai

package object gdxs {
  final case class Pos(x: Float, y: Float)
  final case class Size(width: Float, height: Float)
  final case class Area(pos: Pos, size: Size)

  sealed trait Region {
    val area: Area
    def x: Float = area.pos.x
    def y: Float = area.pos.y
    def width: Float = area.size.width
    def height: Float = area.size.height
  }
  final case class FixedRegion(area: Area) extends Region
}
