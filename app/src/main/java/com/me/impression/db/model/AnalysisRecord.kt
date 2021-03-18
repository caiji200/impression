package com.me.impression.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: Jie Cai
 */
@Entity
data class AnalysisRecord (
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long=0,
    var type:Int=0, //1 day study count    //2 day study duration
    var count:Int=0,
    var duration:Long=0, //seconds
    var date:Long=0, //date
    var createTime:Long=0
)