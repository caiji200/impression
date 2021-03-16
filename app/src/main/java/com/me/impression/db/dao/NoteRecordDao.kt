package com.me.impression.db.dao

import androidx.room.*
import com.me.impression.db.model.NoteRecord

/**
 * @author: Jie Cai
 * @desc : operate Note Record
 * */
@Dao
interface NoteRecordDao
{
    @Query("SELECT * FROM NoteRecord ORDER BY createTime DESC")
    fun getAll(): List<NoteRecord>

    @Query("SELECT * FROM NoteRecord where id IN (:id)")
    fun getRecord(id:Long?): NoteRecord?

    @Insert
    fun insert(note: NoteRecord): Long

    @Delete
    fun delete(note: NoteRecord?)

    @Update
    fun update(note: NoteRecord?)

    @Query("SELECT count(id) FROM NoteRecord")
    fun getCount(): Long

}