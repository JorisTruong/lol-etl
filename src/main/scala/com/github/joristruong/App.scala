package com.github.joristruong

import com.github.joristruong.entity.{Champ, Player, Stats}
import com.jcdecaux.setl.Setl

object App {
  def main(args: Array[String]): Unit = {
    val setl = Setl.builder()
      .withDefaultConfigLoader()
      .getOrCreate()

    setl
      .setSparkRepository[Champ]("champsRepository", deliveryId = "champsRepo")
      .setSparkRepository[Player]("playersRepository", deliveryId = "playersRepo")
      .setSparkRepository[Stats]("statsRepository", deliveryId = "statsRepo")


    setl
      .newPipeline()
      .describe()
      .run()
  }
}
