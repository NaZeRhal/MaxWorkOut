package com.maxrzhe.maxworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "MaxWorkout.db"
        private const val TABLE_HISTORY = "history"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_COMPLETED_DATE = "completed_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql =
            ("CREATE TABLE $TABLE_HISTORY($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_COMPLETED_DATE TEXT)")
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    fun addDate(date: String): Long {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_COMPLETED_DATE, date)

        val db = this.writableDatabase
        val status = db.insert(TABLE_HISTORY, null, contentValues)
        db.close()
        return status
    }

    fun findAllDates(): ArrayList<String> {
        val dates = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        while (cursor.moveToNext()) {
            val date = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            dates.add(date)
        }
        cursor.close()

        return dates
    }

}