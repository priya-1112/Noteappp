package com.example.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBhandler extends SQLiteOpenHelper {


    public DBhandler(Context context) {
        super(context, DBpara.DB_NAME, null, DBpara.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + DBpara.DB_TABLE + "(" + DBpara.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DBpara.KEY_TITLE + " TEXT, " + DBpara.KEY_NOTE + " TEXT " + ")";
        db.execSQL(create);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DBpara.DB_TABLE);
        onCreate(db);

    }

    public void addnote(notemodel notemodel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(DBpara.KEY_TITLE, notemodel.getTitle());
        value.put(DBpara.KEY_NOTE, notemodel.getNote());
        db.insert(DBpara.DB_TABLE, null, value);
        db.close();
    }


      public List<notemodel> getnotemodelList (){
        List<notemodel> notemodelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + DBpara.DB_TABLE;
        Cursor cursor = db.rawQuery(select,null);

        if (cursor.moveToFirst()){
           do {
               notemodel notemodel = new notemodel();
               notemodel.setId(Integer.parseInt(cursor.getString(0)));
               notemodel.setTitle(cursor.getString(1));
               notemodel.setNote(cursor.getString(2));
               notemodelList.add(notemodel);


           }
           while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return notemodelList;
    }

    public int update (notemodel notemodel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(DBpara.KEY_TITLE, notemodel.getTitle());
        value.put(DBpara.KEY_NOTE, notemodel.getNote());

        return db.update(DBpara.DB_TABLE,value, DBpara.KEY_ID + "=?", new String[] {String.valueOf(notemodel.getId())});

    }

    public void delete (notemodel notemodel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBpara.DB_TABLE, DBpara.KEY_ID + "=?", new String[] {String.valueOf(notemodel.getId())});
        db.close();
    }

    public notemodel getnoteid(int id ){
        notemodel note = new notemodel();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + DBpara.DB_TABLE + " WHERE " + DBpara.KEY_ID + "=? ";
        Cursor cursor = db.rawQuery(select, new String[]{ String.valueOf(id)});

        if (cursor.moveToFirst()){
            note.setId(Integer.parseInt(cursor.getString(0)));
            note.setTitle(cursor.getString(1));
            note.setNote(cursor.getString(2));

        }
        cursor.close();
        db.close();
        return note;


    }



}
