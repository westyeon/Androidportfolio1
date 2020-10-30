package com.example.androidportfolio1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookMarkDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bookmark.db";
    public static final String TABLE_BOOKMARK = "bookmark";
    public static final String ITEM = "item";
    public static final String SEARCH_WORD = "search_word";
    public static final String DATE = "date";

    public BookMarkDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKMARK_TABLE = "CREATE TABLE if not exists " +
                TABLE_BOOKMARK + "("
                + ITEM + " TEXT," + SEARCH_WORD
                + " TEXT," + DATE + " " +
                "TEXT" + ")";
        db.execSQL(CREATE_BOOKMARK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARK);
        onCreate(db);
    }

    public void addBookMark(BookMark bookmark) {
        ContentValues values = new ContentValues();
        values.put(ITEM, bookmark.getItem());
        values.put(SEARCH_WORD, bookmark.getSearch_word());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        values.put(DATE, format.format(date));
        Log.e("value", values.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_BOOKMARK, null, values);
        db.close();
    }

    public BookMark findBookMark(String item, String search_word) {
        String query = "Select * FROM " + TABLE_BOOKMARK + " WHERE " +
                ITEM + " = \'" + item + "\' and SEARCH_WORD = \'" + search_word + "\'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BookMark bookmark = new BookMark();
        if (cursor.moveToFirst()) {
            bookmark.setItem(cursor.getString(0));
            bookmark.setSearch_word(cursor.getString(1));
            bookmark.setDate(cursor.getString(2));
            cursor.close();
        } else {
            bookmark = null;
        }
        db.close();

        if(bookmark != null) {
            Log.e("검색된 내용", bookmark.toString());
        }else{
            Log.e("검색된 내용", "없음");
        }
        return bookmark;
    }

    //전체 내용 가져오기
    public ArrayList<BookMark> listBookMark() {
        ArrayList<BookMark> list = new ArrayList<>();
        String query = "Select * FROM " + TABLE_BOOKMARK + " order by date desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            BookMark bookmark = new BookMark();
            bookmark.setItem(cursor.getString(0));
            bookmark.setSearch_word(cursor.getString(1));
            bookmark.setDate(cursor.getString(2));
            list.add(bookmark);
        }
        cursor.close();
        db.close();


        return list;
    }


    public void updateBookMark(BookMark bookmark) {
        ContentValues values = new ContentValues();
        values.put(ITEM, bookmark.getItem());
        values.put(SEARCH_WORD, bookmark.getSearch_word());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        values.put(DATE, format.format(date));

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_BOOKMARK, values, ITEM + "=\'" +  bookmark.getItem() + "\' and " + SEARCH_WORD + "=\'" + bookmark.getSearch_word()+"\'", null);
        db.close();
    }
    public void deleteBookMark(BookMark bookmark) {
        ContentValues values = new ContentValues();
        values.put(ITEM, bookmark.getItem());
        values.put(SEARCH_WORD, bookmark.getSearch_word());

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_BOOKMARK, ITEM + "= \'" +  bookmark.getItem() + "\' and " + SEARCH_WORD + "= \'" + bookmark.getSearch_word() + "\'", null);
        Log.e("where",ITEM + "= \'" +  bookmark.getItem() + "\' and " + SEARCH_WORD + "= \'" + bookmark.getSearch_word() + "\'");
        db.close();
    }
}


