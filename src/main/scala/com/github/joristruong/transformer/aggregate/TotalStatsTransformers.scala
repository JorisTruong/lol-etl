package com.github.joristruong.transformer.aggregate

import com.github.joristruong.entity.ChampStats
import com.jcdecaux.setl.transformation.Transformer
import com.jcdecaux.setl.util.HasSparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset}

class TotalStatsTransformers(crossData: Dataset[ChampStats], champTimesPlayed: DataFrame) extends Transformer[DataFrame] with HasSparkSession {

  import spark.implicits._

  var transformedData: DataFrame = _

  override def transformed: DataFrame = transformedData

  override def transform(): TotalStatsTransformers.this.type = {
    val totalStats = crossData
      .groupBy("name", "championId")
      .agg(
        sum("kills") as "kills",
        sum("deaths") as "deaths"
      )
      .join(champTimesPlayed, Seq("name"))
      .withColumn("normalizedKills", $"kills" / $"gamesNumber")
      .withColumn("normalizedDeaths", $"deaths" / $"gamesNumber")
      .sort($"deaths".desc, $"normalizedDeaths".desc, $"championId".asc)

    transformedData = totalStats

    this
  }
}
