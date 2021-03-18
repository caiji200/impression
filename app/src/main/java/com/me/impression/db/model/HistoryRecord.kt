package com.me.impression.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author : Jie Cai
 * @desc : History Translate Record
 */
@Entity
data class HistoryRecord (
    @PrimaryKey(autoGenerate = true)
    var id: Long=0,
    var from: String="",
    var to: String="",
    var srcText: String="",
    var destText: String="",
    var extra: String="",
    var createTime: Long=0
)