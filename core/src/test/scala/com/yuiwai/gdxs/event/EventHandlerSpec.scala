package com.yuiwai.gdxs.event

import com.badlogic.gdx.graphics.{Camera, Texture}
import com.badlogic.gdx.math.Vector3
import com.yuiwai.gdxs.component.{Button, Component}
import com.yuiwai.gdxs.view.{CompositeViewProfile, ViewProfile}
import com.yuiwai.gdxs.{Area, FixedRegion, Pos, Size}
import org.mockito.MockitoSugar._
import org.scalatest.{Matchers, WordSpec}

class EventHandlerSpec extends WordSpec with Matchers {
  "EventHandler" should {
    "handle button event" in new Fixture {
      when(camera.unproject(new Vector3(110, 110, 0))).thenReturn(new Vector3(110, 110, 0))
      when(camera.unproject(new Vector3(99, 110, 0))).thenReturn(new Vector3(99, 110, 0))

      val handler = genHandler(
        genViewProfile(
          Button(texture, FixedRegion(Area(Pos(100, 100), Size(20, 20))), SetFlag(true)) :: Nil)
      )
      handler.touchDown(99, 110, 0, 0)
      flag shouldBe false
      handler.touchDown(110, 110, 0, 0)
      flag shouldBe true
    }

    "handle button event with CompositeViewProfile" in new Fixture {
      when(camera.unproject(new Vector3(100, 100, 0))).thenReturn(new Vector3(100, 100, 0))
      when(camera.unproject(new Vector3(130, 130, 0))).thenReturn(new Vector3(130, 130, 0))

      val handler = genHandler(
        CompositeViewProfile(
          genViewProfile(Button(texture, FixedRegion(Area(Pos(100, 100), Size(20, 20))), SetFlag(true)) :: Nil),
          genViewProfile(Button(texture, FixedRegion(Area(Pos(130, 130), Size(20, 20))), SetFlag(false)):: Nil))
      )
      handler.touchDown(100, 100, 0, 0)
      flag shouldBe true
      handler.touchDown(130, 130, 0, 0)
      flag shouldBe false
    }

    "handle button event with StackedViewProfile" in {
    }
  }
}

trait Fixture {
  var flag = false
  val texture = mock[Texture]
  val camera = mock[Camera]

  def genHandler(targetViewProfile: ViewProfile): EventHandler[Action] = new EventHandler[Action] {
    override protected val actionHandler: Action => Unit = {
      case SetFlag(bool) => flag = bool
      case _ =>
    }
    override protected val viewProfile: ViewProfile = targetViewProfile
  }
  def genViewProfile(components: Seq[Component]) = new DummyViewProfile(camera, components)
}

trait Action
final case class SetFlag(boolean: Boolean) extends Action
class DummyViewProfile(val camera: Camera, val components: Seq[Component]) extends ViewProfile

