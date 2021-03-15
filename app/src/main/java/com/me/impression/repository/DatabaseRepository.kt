package com.me.impression.repository

import com.me.impression.base.BaseApplication
import com.me.impression.db.AppDatabase
import com.me.impression.db.dao.HistoryRecordDao
import com.me.impression.db.dao.NoteRecordDao

/**
 * @author:Jie Cai
 * @desc: db Repository
 */
object DatabaseRepository
{
    fun noteBookDao():NoteRecordDao
    {
        return AppDatabase.instance(BaseApplication.instance).noteRecordDao()
    }

    fun historyTranslateDao():HistoryRecordDao
    {
        return AppDatabase.instance(BaseApplication.instance).historyRecordDao()
    }


}