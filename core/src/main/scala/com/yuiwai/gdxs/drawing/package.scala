package com.yuiwai.gdxs

package object drawing {
  sealed trait Drawing
  case class Line(from: Pos, to: Pos) extends Drawing
}
