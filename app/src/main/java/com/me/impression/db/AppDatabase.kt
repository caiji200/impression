package com.me.impression.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.me.impression.db.dao.HistoryRecordDao
import com.me.impression.db.dao.NoteRecordDao
import com.me.impression.db.model.HistoryRecord
import com.me.impression.db.model.NoteRecord

@Database(entities = [NoteRecord::class,HistoryRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteRecordDao(): NoteRecordDao
    abstract fun historyRecordDao(): HistoryRecordDao

    companion object {
        private var instance: AppDatabase? = null
        private var DB_NAME:String = "app.db"
        fun instance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
//                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
            return instance as AppDatabase
        }

        /**
         * migration 1->2
         */
//        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                Log.d("db","MIGRATION_1_2")
//                database.execSQL("ALTER TABLE TEST ADD COLUMN name INTEGER NOT NULL DEFAULT 0")
//            }
//        }

    }

}