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
    private static final String STATE_COL = "state";
    private static final String CATEGORY_COL = "category";
    private static final String DESC_COL = "description";

    public tasksDB(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TABLE_NAME+" ("
                +TOKEN_COL+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TITLE_COL+" varchar(40) , "
                +DATE_COL+ " varchar(20) , "
                +TIME_COL+" varchar(20) ,"
                +DESC_COL+" varchar(120) ,"
                +STATE_COL+" varchar(20) ,"
                +CATEGORY_COL+" varchar(20) )";
        sqLiteDatabase.execSQL(query);
    }


    public long insert(String title,String date,String time,String desc,String state,String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COL,title);
        values.put(DATE_COL,date);
        values.put(TIME_COL,time);
        values.put(DESC_COL,desc);
        values.put(STATE_COL,state);
        values.put(CATEGORY_COL,category);

        long result = db.insert(TABLE_NAME,null,values);
        //db.close();
        return result;
    }

    public void updateTask(String token,String title,String date ,String time,String desc,String state,String category)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COL,title);
        values.put(DATE_COL,date);
        values.put(TIME_COL,time);
        values.put(DESC_COL,desc);
        values.put(STATE_COL,state);
        values.put(CATEGORY_COL,category);
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
        Cursor cursor = db.query(TABLE_NAME,new String [] {TOKEN_COL,TITLE_COL,TIME_COL,DATE_COL,DESC_COL,CATEGORY_COL,STATE_COL},null,null,null,null,null);

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                int token = cursor.getInt(0);
                String tokenStr = Integer.toString(token);
                String title = cursor.getString(1);
                String time = cursor.getString(2);
                String date = cursor.getString(3);
                String desc = cursor.getString(4);
                String category = cursor.getString(5);
                String state = cursor.getString(6);
                String datetime = date+"_"+time;
                Task newTask = new Task(title,desc,datetime, category, state);
                newTask.setTaskToken(tokenStr);
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
        Cursor cursor = db.query(TABLE_NAME,new String[]{TOKEN_COL,TITLE_COL,TIME_COL,DATE_COL,DESC_COL,CATEGORY_COL,STATE_COL},DATE_COL+" =?",new String[]{date},null,null,null);
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                int token = cursor.getInt(0);
                String tokenStr = Integer.toString(token);
                String title = cursor.getString(1);
                String time = cursor.getString(2);
                String Date = cursor.getString(3);
                String desc = cursor.getString(4);
                String category = cursor.getString(5);
                String state = cursor.getString(6);
                String datetime = date+"_"+time;
                Task newTask = new Task(title,desc,datetime, category, state);
                newTask.setTaskToken(tokenStr);
                tasks.add(newTask);
            }
        }
        return tasks;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
