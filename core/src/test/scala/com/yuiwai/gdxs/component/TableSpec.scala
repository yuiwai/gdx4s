package com.yuiwai.gdxs.component

import com.yuiwai.gdxs.Region
import org.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class TableSpec  extends FlatSpec with Matchers with MockitoSugar {
  "空のTableは" should "子要素を持たない" in {
    Table(mock[Region]).rows shouldBe Seq.empty
  }
  it should "行を追加することができる" in {
    val region = mock[Region]
    Table(region).appendRow(mock[Row]).rows.size shouldBe 1
  }
  it should "カラム指定で行を追加することができる" in {
    val region = mock[Region]
    Table(region)
      .appendRow(mock[Column])
  }
}
