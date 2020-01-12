package com.github.joristruong.entity

import com.jcdecaux.setl.annotation.ColumnName

case class Champ(
                  name: String,
                  @ColumnName("id") championId: Long
                )
