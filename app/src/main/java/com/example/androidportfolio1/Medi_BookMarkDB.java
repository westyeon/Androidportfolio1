package com.example.androidportfolio1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Medi_BookMarkDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "medicine.db";
    public static final String TABLE_MEDICINE = "medicine";
    public static final String ITEM_SEQ_NUM = "item_seq_num";
    public static final String ITEM_NAME = "item_name";
    public static final String ENTP_SEQ = "entp_seq";
    public static final String ENTP_NAME = "entp_name";
    public static final String ITEM_IMAGE = "item_image";
    public static final String EDI_CODE = "edi_code";

    public Medi_BookMarkDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEDICINE_TABLE = "CREATE TABLE if not exists " +
                TABLE_MEDICINE + "("
                + ITEM_SEQ_NUM + " TEXT," + ITEM_NAME
                + " TEXT," + ENTP_SEQ + " TEXT," + ENTP_NAME + " TEXT,"+ITEM_IMAGE+ " TEXT,"+ EDI_CODE+ " TEXT"+")";
        db.execSQL(CREATE_MEDICINE_TABLE);
        //Log.e("실행", "dfad");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);
        onCreate(db);
    }

    public void addMedicine(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(ITEM_SEQ_NUM, medicine.getItem_seq_num());
        values.put(ITEM_NAME, medicine.getItem_name());
        values.put(ENTP_SEQ, medicine.getEntp_seq());
        values.put(ENTP_NAME, medicine.getEntp_name());
        values.put(ITEM_IMAGE, medicine.getItem_image());
        values.put(EDI_CODE, medicine.getEdi_code());
        Log.e("value", values.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_MEDICINE, null, values);
        db.close();
    }


    public Medicine findMedicine(String item_seq_num, String item_name, String entp_seq, String entp_name, String item_image, String edi_code) {
        String query = "Select * FROM " + TABLE_MEDICINE + " WHERE " +
                ITEM_SEQ_NUM + " = \'" + item_seq_num + "\' and ITEM_NAME = \'" + item_name + "\' " +
                "and ENTP_SEQ = \'" + entp_seq + "\' and ENTP_NAME = \'" + entp_name + "\' " +
                "and ITEM_IMAGE = \'" + item_image + "\' and EDI_CODE = \'" + edi_code + "\'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Medicine medicine = new Medicine();
        if (cursor.moveToFirst()) {
            medicine.setItem_seq_num(cursor.getString(0));
            medicine.setItem_name(cursor.getString(1));
            medicine.setEntp_seq(cursor.getString(2));
            medicine.setEntp_name(cursor.getString(3));
            medicine.setItem_image(cursor.getString(4));
            medicine.setEdi_code(cursor.getString(5));
            cursor.close();
        } else {
            medicine = null;
        }
        db.close();

        if(medicine != null) {
            Log.e("검색된 내용", medicine.toString());
        }else{
            Log.e("검색된 내용", "없음");
        }
        return medicine;
    }

    public boolean checkMedicine(String item_seq_num, String item_image) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_MEDICINE + " WHERE " +
                ITEM_SEQ_NUM + " = \'" + item_seq_num  +
                "\' and ITEM_IMAGE = \'" + item_image + "\'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Medicine medicine = new Medicine();
        if (cursor.moveToFirst()) {
            result = true;
        }
        db.close();
        return result;
    }

    //전체 내용 가져오기
    public ArrayList<Medicine> listMedicine() {
        ArrayList<Medicine> list = new ArrayList<>();
        String query = "Select * FROM " + TABLE_MEDICINE + " order by item_seq_num desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Medicine medicine = new Medicine();
            medicine.setItem_seq_num(cursor.getString(0));
            medicine.setItem_name(cursor.getString(1));
            medicine.setEntp_seq(cursor.getString(2));
            medicine.setEntp_name(cursor.getString(3));
            medicine.setItem_image(cursor.getString(4));
            medicine.setEdi_code(cursor.getString(5));
            list.add(medicine);
        }
        cursor.close();
        db.close();

        return list;
    }

    public void updateMedicine(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(ITEM_SEQ_NUM, medicine.getItem_seq_num());
        values.put(ITEM_NAME, medicine.getItem_name());
        values.put(ENTP_SEQ, medicine.getEntp_seq());
        values.put(ENTP_NAME, medicine.getEntp_name());
        values.put(ITEM_IMAGE, medicine.getItem_image());
        values.put(EDI_CODE, medicine.getEdi_code());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_MEDICINE, values, ITEM_SEQ_NUM + "=\'" + medicine.getItem_seq_num() + "\' and " + ITEM_NAME + "=\'" + medicine.getItem_name() +"\' and " + ENTP_SEQ + "=\'" + medicine.getEntp_seq() +"\' and " + ENTP_NAME + "=\'" + medicine.getEntp_name() +"\' and " + ITEM_IMAGE + "=\'" + medicine.getItem_image() +"\' and " + EDI_CODE + "=\'" + medicine.getEdi_code() +"\' ", null);
        db.close();
    }

    public void deleteMedicine(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(ITEM_SEQ_NUM, medicine.getItem_seq_num());
        values.put(ITEM_NAME, medicine.getItem_name());
        values.put(ENTP_SEQ, medicine.getEntp_seq());
        values.put(ENTP_NAME, medicine.getEntp_name());
        values.put(ITEM_IMAGE, medicine.getItem_image());
        values.put(EDI_CODE, medicine.getEdi_code());

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MEDICINE,ITEM_SEQ_NUM + "=\'" + medicine.getItem_seq_num() + "\' and " + ITEM_IMAGE + " = \'" + medicine.getItem_image() + "\'", null);
        db.close();
    }

}
