package com.yuiwai.gdxs

import com.yuiwai.gdxs.component.{Button, Component, LabelStyle}

package object dsl {
  def labeledBtn[A](style: LabelStyle)(label: String, region: Region, action: A): Component =
    Button(label, style, region, action)
  def labeledBtn[A](style: LabelStyle, size: Size)(label: String, action: A): Component =
    Button(label, style, FixedRegion(size), action)
}
