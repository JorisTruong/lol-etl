package com.github.joristruong

import com.github.joristruong.entity.Champ
import com.jcdecaux.setl.Setl

object App {
  def main(args: Array[String]): Unit = {
    val setl = Setl.builder()
      .withDefaultConfigLoader()
      .getOrCreate()

    setl
      .newPipeline()
      .describe()
      .run()
  }
}
