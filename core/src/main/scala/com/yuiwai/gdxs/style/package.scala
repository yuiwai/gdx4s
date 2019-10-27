package com.yuiwai.gdxs

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont

package object style {
  sealed trait Style

  // background
  sealed trait BackgroundStyle extends Style
  trait FitTextureBackgroundStyle extends BackgroundStyle {
    val backgroundTexture: Texture
  }

  // font
  sealed trait FontStyle extends Style {
    val font: BitmapFont
  }
  trait BitmapFontStyle extends FontStyle with TextureStyleOps {
    def withLinearFilter(): this.type = {
      withLinearFilter(font.getRegion.getTexture)
      this
    }
  }

  trait TextureStyleOps {
    def withLinearFilter(texture: Texture): Unit = {
      texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
    }
  }
}
