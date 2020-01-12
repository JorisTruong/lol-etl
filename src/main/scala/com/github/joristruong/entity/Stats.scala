package com.github.joristruong.entity

import com.jcdecaux.setl.annotation.ColumnName

case class Stats(
                  @ColumnName("id") playerId: Long,
                  kills: Long,
                  deaths: Long
                )
