package com.me.impression.db.dao

import androidx.room.*
import com.me.impression.db.model.HistoryRecord
import com.me.impression.db.model.NoteRecord

/**
 * @author: Jie Cai
 * @desc : operate History Translate Record
 * */
@Dao
interface HistoryRecordDao
{
    @Query("SELECT * FROM HistoryRecord ORDER BY createTime DESC ")
    fun getAll(): List<HistoryRecord>

    @Query("SELECT * FROM HistoryRecord WHERE type = :type ORDER BY createTime DESC ")
    fun getAllByType(type:Int): List<HistoryRecord>

    @Query("SELECT * FROM HistoryRecord WHERE type = 1 ORDER BY createTime DESC limit 3")
    fun getRecent(): List<HistoryRecord>

    @Query("SELECT * FROM HistoryRecord where id IN (:id)")
    fun getRecord(id:Long?): HistoryRecord?

    @Insert
    fun insert(note: HistoryRecord): Long

    @Delete
    fun delete(note: HistoryRecord?)

    @Update
    fun update(note: HistoryRecord?)

    @Query("SELECT count(id) FROM HistoryRecord")
    fun getCount(): Long

}