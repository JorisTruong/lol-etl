package com.github.joristruong.transformer.AggregateTransformer

import com.github.joristruong.entity.ChampStats
import com.jcdecaux.setl.transformation.Transformer
import com.jcdecaux.setl.util.HasSparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset}

class TotalStatsTransformers(crossData: Dataset[ChampStats]) extends Transformer[DataFrame] with HasSparkSession {
  var transformedData: DataFrame = _

  override def transformed: DataFrame = transformedData

  override def transform(): TotalStatsTransformers.this.type = {
    val totalStats = crossData
      .groupBy("name")
      .agg(
        sum("kills") as "kills",
        sum("deaths") as "deaths"
      )

    transformedData = totalStats

    this
  }
}
