package com.github.joristruong.entity

case class ChampStats(
                       playerId: Long,
                       championId: Long,
                       matchId: Long,
                       name: String,
                       kills: Int,
                       deaths: Int
                     )
