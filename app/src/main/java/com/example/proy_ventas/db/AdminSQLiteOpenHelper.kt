package com.example.primeropasoskotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(
            "CREATE TABLE producto(" +
                "id_producto INTEGER PRIMARY KEY," +
                " nombre TEXT," +
                " precio REAL)"
        )
        db?.execSQL("CREATE TABLE ventas( " +
                "id_venta INTEGER PRIMARY KEY, " +
                "id_producto INTEGER, " +
                "cantidad INTEGER, " +
                "precio_total REAL, " +
                "cliente TEXT," +
                "FOREIGN KEY (id_producto) REFERENCES producto(id_producto))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS productos")
        db?.execSQL("DROP TABLE IF EXISTS ventas")
        onCreate(db)
    }
}