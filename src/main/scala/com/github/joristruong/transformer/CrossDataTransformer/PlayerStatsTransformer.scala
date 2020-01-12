package com.github.joristruong.transformer.CrossDataTransformer

import com.github.joristruong.entity.Stats
import com.jcdecaux.setl.transformation.Transformer
import org.apache.spark.sql.{DataFrame, Dataset}

class PlayerStatsTransformer(championPlayers: DataFrame, stats: Dataset[Stats]) extends Transformer[DataFrame] {

  var transformedData: DataFrame = _

  override def transformed: DataFrame = transformedData

  override def transform(): PlayerStatsTransformer.this.type = {
    val playerStats = championPlayers
      .join(stats, Seq("playerId"))

    transformedData = playerStats

    this
  }
}
