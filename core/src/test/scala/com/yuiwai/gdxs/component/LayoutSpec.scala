package com.yuiwai.gdxs.component

import com.yuiwai.gdxs.{FixedRegion, Size}
import org.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class LayoutSpec extends FlatSpec with Matchers with MockitoSugar {
  val parentRegion = FixedRegion(Size(200, 200))
  "HLayoutは" should "固定サイズの子要素を横に均等配置する" in {
    def childRegion(w: Float) = FixedRegion(Size(w, 10))
    def child(w: Float): Component = (Spacer(childRegion(w)))
    def check(children: Seq[Component], expectedXs: Seq[Float]): Unit =
      HLayout(parentRegion, children)
        .cell.asInstanceOf[HLayout].children.map(_.region.x) shouldBe expectedXs
    check(Seq.fill(2)(child(100)), Seq(0, 100))
    check(Seq.fill(2)(child(40)), Seq(40, 120))
    check(Seq.fill(2)(child(150)), Seq(0, 150))
  }
  "VLayoutは" should "固定サイズの子要素を縦に均等配置する" in {
    def childRegion(h: Float) = FixedRegion(Size(10, h))
    def child(h: Float): Component = (Spacer(childRegion(h)))
    def check(children: Seq[Component], expectedXs: Seq[Float]): Unit =
      VLayout(parentRegion, children)
        .cell.asInstanceOf[VLayout].children.map(_.region.y) shouldBe expectedXs
    check(Seq.fill(2)(child(100)), Seq(0, 100))
    check(Seq.fill(2)(child(40)), Seq(40, 120))
    check(Seq.fill(2)(child(150)), Seq(0, 150))
  }
}
