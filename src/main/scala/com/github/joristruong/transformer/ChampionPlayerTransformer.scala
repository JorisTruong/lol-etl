package com.github.joristruong.transformer

import com.github.joristruong.entity.{Champ, Player}
import com.jcdecaux.setl.transformation.Transformer
import org.apache.spark.sql.{DataFrame, Dataset}

class ChampionPlayerTransformer(champs: Dataset[Champ], players: Dataset[Player]) extends Transformer[DataFrame] {

  var transformedData: DataFrame = _

  override def transformed: DataFrame = transformedData

  override def transform(): ChampionPlayerTransformer.this.type = {
    val championPlayer = players
      .join(champs, Seq("championId"), "left_outer")

    transformedData = championPlayer

    this
  }
}