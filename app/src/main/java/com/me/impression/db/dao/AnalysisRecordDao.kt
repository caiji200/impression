package com.me.impression.db.dao

import androidx.room.*
import com.me.impression.db.model.AnalysisRecord
import com.me.impression.db.model.HistoryRecord
import com.me.impression.db.model.NoteRecord

/**
 * @author: Jie Cai
 * @desc : analysis record
 * */
@Dao
interface AnalysisRecordDao
{
    @Query("SELECT * FROM AnalysisRecord ORDER BY date")
    fun getAll(): List<AnalysisRecord>

    @Query("SELECT * FROM AnalysisRecord WHERE type = :type ORDER BY date")
    fun getAll(type:Int): List<AnalysisRecord>

    @Query("SELECT * FROM AnalysisRecord where type =:type and date= :date")
    fun getRecord(type:Int,date:Long): AnalysisRecord?

    @Insert
    fun insert(note: AnalysisRecord): Long

    @Update
    fun update(note: AnalysisRecord)

    @Query("DELETE FROM AnalysisRecord")
    fun deleteAll()

}