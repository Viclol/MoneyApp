package com.example.moneyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.LinkedList;

public class RecordDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Record";

    private static final String CREATE_RECORD_DB = "create table Record ("
            +"id integer primary key autoincrement,"
            +"uuid text,"
            +"type integer,"
            +"category text,"
            +"remark text,"
            +"amount double,"
            +"time integer,"
            +"date date );";

    public RecordDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RECORD_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void addRecord(RecordBean bean){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",bean.getUuid());
        values.put("type",bean.getType());
        values.put("category",bean.getCategory());
        values.put("remark",bean.getRemark());
        values.put("amount",bean.getAmount());
        values.put("time",bean.getTimetamp());
        values.put("date",bean.getDate());
        db.insert(DB_NAME,null,values);
        values.clear();
        db.close();
    }
    public void removeRecord(String uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"uuid = ?",new String[]{uuid});
    }

    public void editRecord(String uuid,RecordBean bean){
        removeRecord(uuid);
        bean.setUuid(uuid);
        addRecord(bean);
    }

    public LinkedList<RecordBean> readRecords(String dataStr){

        LinkedList<RecordBean> records = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from Record where date =  ? order by time asc",new String[]{dataStr});

        if (cursor.moveToFirst()){
//            do {
//
//                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
//                int type = cursor.getInt(cursor.getColumnIndex("type"));
//                String category = cursor.getString(cursor.getColumnIndex("category"));
//                String remark = cursor.getString(cursor.getColumnIndex("remark"));
//                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
//                long timeStamp = cursor.getLong(cursor.getColumnIndex("timeStamp"));
//                String date = cursor.getString(cursor.getColumnIndex("date"));
//
//                RecordBean record = new RecordBean();
//                record.setUuid(uuid);
//                record.setType(type);
//                record.setCategory(category);
//                record.setRemark(remark);
//                record.setAmount(amount);
//                record.setTimetamp(timeStamp);
//                record.setDate(date);
//                cursor.close();
//
//                records.add(record);
//
//            }while (cursor.moveToNext());
            while (cursor.moveToNext()) {
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                Double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                long timeStamp = cursor.getLong(cursor.getColumnIndex("time"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                RecordBean record = new RecordBean();
                record.setUuid(uuid);
                record.setType(type);
                record.setCategory(category);
                record.setRemark(remark);
                record.setAmount(amount);
                record.setTimetamp(timeStamp);
                record.setDate(date);

                records.add(record);
            }
        }

        cursor.close();

        return records;
    }

    public LinkedList<String> getAvaliableDate(){
        LinkedList<String> dates = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from Record order by date asc",new String[]{});

        if (cursor.moveToFirst()){
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if (!dates.contains(date)){
                    dates.add(date);//每一天只添加一次
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        return dates;
    }
}
