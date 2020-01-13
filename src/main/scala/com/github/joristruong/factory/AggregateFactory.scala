package com.github.joristruong.factory

import com.github.joristruong.entity.ChampStats
import com.github.joristruong.transformer.aggregate.{ChampTimesPlayedTransformer, TotalStatsTransformers}
import com.jcdecaux.setl.annotation.Delivery
import com.jcdecaux.setl.storage.connector.Connector
import com.jcdecaux.setl.storage.repository.SparkRepository
import com.jcdecaux.setl.transformation.Factory
import org.apache.spark.sql.{DataFrame, Dataset}

class AggregateFactory extends Factory[DataFrame] {

  @Delivery(id = "champPlayersConn")
  var champPlayersConn: Connector = _
  @Delivery(id = "champStatsRepo")
  var champStatsRepo: SparkRepository[ChampStats] = _
  @Delivery(id = "killsDeathsConn")
  var killsDeathsConn: Connector = _

  var champPlayers: DataFrame = _
  var champStats: Dataset[ChampStats] = _

  var output: DataFrame = _

  override def read(): AggregateFactory.this.type = {
    champPlayers = champPlayersConn.read()
    champStats = champStatsRepo.findAll()

    this
  }

  override def process(): AggregateFactory.this.type = {
    val champTimesPlayed = new ChampTimesPlayedTransformer(champPlayers).transform().transformed
    val totalStats = new TotalStatsTransformers(champStats, champTimesPlayed).transform().transformed

    output = totalStats

    this
  }

  override def write(): AggregateFactory.this.type = {
    killsDeathsConn.write(output.repartition(1))

    this
  }

  override def get(): DataFrame = output
}
