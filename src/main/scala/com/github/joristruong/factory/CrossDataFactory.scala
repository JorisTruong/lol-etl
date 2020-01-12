package com.github.joristruong.factory

import com.github.joristruong.entity.{Champ, Player, Stats}
import com.github.joristruong.transformer.CrossDataTransformer.{ChampionPlayerTransformer, PlayerStatsTransformer}
import com.jcdecaux.setl.annotation.Delivery
import com.jcdecaux.setl.storage.repository.SparkRepository
import com.jcdecaux.setl.transformation.Factory
import org.apache.spark.sql.{DataFrame, Dataset}

class CrossDataFactory extends Factory[DataFrame] {

  @Delivery(id = "champsRepo")
  var champsRepo: SparkRepository[Champ] = _
  @Delivery(id = "playersRepo")
  var playersRepo: SparkRepository[Player] = _
  @Delivery(id = "statsRepo")
  var statsRepo: SparkRepository[Stats] = _

  var champs: Dataset[Champ] = _
  var players: Dataset[Player] = _
  var stats: Dataset[Stats] = _

  var output: DataFrame = _

  override def read(): CrossDataFactory.this.type = {
    champs = champsRepo.findAll()
    players = playersRepo.findAll()
    stats = statsRepo.findAll()

    this
  }

  override def process(): CrossDataFactory.this.type = {
    val championPlayer = new ChampionPlayerTransformer(champs, players).transform().transformed
    val playerStats = new PlayerStatsTransformer(championPlayer, stats).transform().transformed

    output = playerStats

    this
  }

  override def write(): CrossDataFactory.this.type = this

  override def get(): DataFrame = output
}
