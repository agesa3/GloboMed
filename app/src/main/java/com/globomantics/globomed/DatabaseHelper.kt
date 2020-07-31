package com.globomantics.globomed

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(GloboMedDBContract.EmployeeEntry.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(GloboMedDBContract.EmployeeEntry.ALTER_TABLE_1)

    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    companion object {
        const val DATABASE_NAME = "globomed.db"
        const val DATABASE_VERSION = 2
    }

}