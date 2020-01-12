package com.github.joristruong

import com.github.joristruong.entity.{Champ, ChampStats, Player, Stats}
import com.github.joristruong.factory.{AggregateFactory, CrossDataFactory}
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

      .setConnector("champPlayersConnector", deliveryId = "champPlayersConn")

      .setSparkRepository[ChampStats]("champStatsRepository", deliveryId = "champStatsRepo")
      .setConnector("killsDeathsConnector", deliveryId = "killsDeathsConn")

    setl
      .newPipeline()
      .addStage[CrossDataFactory]()
      .addStage[AggregateFactory]()
      .describe()
      .run()
  }
}
