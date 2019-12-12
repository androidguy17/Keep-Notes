package com.varun.keepnotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.media.projection.MediaProjection
import android.provider.ContactsContract
import android.widget.Toast
import javax.xml.transform.Templates


class DbManager{

     val dbName= "MyNotes"
     val  dbTable= "Notes"
     val colID= "ID"
     val colTitle="Title"
     val colDes="Description"
     val dbVersion=1



     // CREATE TABLE IF NOT EXISTS Notes (ID INTEGER PRIMARY KEY,Title TEXT, Description TEXT);
     val sqlCreateTable="CREATE TABLE IF NOT EXISTS "+ dbTable +" ("+colID + " INTEGER PRIMARY KEY,"+
             colTitle+" TEXT, "+ colDes+" TEXT);"

     var sqlDB: SQLiteDatabase?=null


     constructor(context:Context){
         var db = DataBaseHelperNotes(context)

         sqlDB=db.writableDatabase

     }

    inner class DataBaseHelperNotes:SQLiteOpenHelper{
        var context:Context?=null

        constructor(context:Context):super(context,dbName,null,dbVersion){

        this.context=context

        }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(context,"DataBase is Created",Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS "+ dbTable)
        }

    }


    fun Insert(values: ContentValues):Long{

        val ID= sqlDB!!.insert(dbTable,"",values)
        return ID

    }

    fun Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String):Cursor{
        val qb= SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor = qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }

    fun Delete(selection:String,selectionArgs: Array<String>):Int{

        val count =sqlDB!!.delete(dbTable,selection,selectionArgs)
        return count

    }

    fun Update(values:ContentValues,selection: String,selectionArgs: Array<String>):Int
    {
        val count=sqlDB!!.update(dbTable,values,selection,selectionArgs)

        return count
    }
 }