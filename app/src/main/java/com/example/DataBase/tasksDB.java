package com.example.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.entity.Task;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class tasksDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "TASKS";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "myTasks";
    private static final String TOKEN_COL = "token";
    private static final String TITLE_COL = "title";
    private static final String DATE_COL = "date";
    private static final String TIME_COL = "time";

    public tasksDB(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TABLE_NAME+" ("
                +TOKEN_COL+" varchar(60) PRIMARY KEY , "
                +TITLE_COL+" varchar(40) , "
                +DATE_COL+ " varchar(20) , "
                +TIME_COL+" varchar(20) )";
        sqLiteDatabase.execSQL(query);
    }


    public long insert(String token,String title,String date,String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TOKEN_COL,token);
        values.put(TITLE_COL,title);
        values.put(DATE_COL,date);
        values.put(TIME_COL,time);

        long result = db.insert(TABLE_NAME,null,values);
        //db.close();
        return result;
    }

    public void updateTask(String token,String title,String date ,String time)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COL,title);
        values.put(DATE_COL,date);
        values.put(TIME_COL,time);
        String where = TOKEN_COL+"=?";
        String whereArgs[] = {token};
        db.update(TABLE_NAME,values,where,whereArgs);
    }

    public void deleteTask(String token)
    {
        SQLiteDatabase db = getWritableDatabase();
        String where = TOKEN_COL+"=?";
        String whereArgs[] = {token};
        db.delete(TABLE_NAME,where,whereArgs);
    }

    public ArrayList<Task> select()
    {
        //String dql = "SELECT "+TOKEN_COL+","+TITLE_COL+","+TIME_COL +","+DATE_COL+" FROM "+TABLE_NAME;
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String [] {TOKEN_COL,TITLE_COL,TIME_COL,DATE_COL},null,null,null,null,null);

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                String token = cursor.getString(0);
                String title = cursor.getString(1);
                String time = cursor.getString(2);
                String date = cursor.getString(3);
                String datetime = date+"_"+time;
                Task newTask = new Task(title,datetime,token);
                tasks.add(newTask);
            }
        }

        return tasks;
    }

    //get tasks for specific(current) day....
    public ArrayList<Task> select(String date)
    {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{TOKEN_COL,TITLE_COL,TIME_COL,DATE_COL},DATE_COL+" =?",new String[]{date},null,null,null);
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                String token = cursor.getString(0);
                String title = cursor.getString(1);
                String time = cursor.getString(2);
                String Date = cursor.getString(3);
                String datetime = date+"_"+time;
                Task newTask = new Task(title,datetime,token);
                tasks.add(newTask);
            }
        }
        return tasks;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
