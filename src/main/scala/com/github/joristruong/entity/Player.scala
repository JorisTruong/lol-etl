package com.github.joristruong.entity

import com.jcdecaux.setl.annotation.ColumnName

case class Player(
                   @ColumnName("id") playerId: Long,
                   @ColumnName("matchid") matchId: Long,
                   @ColumnName("championid") championId: Long
                 )
