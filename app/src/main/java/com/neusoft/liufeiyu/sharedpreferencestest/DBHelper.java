package com.neusoft.liufeiyu.sharedpreferencestest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper {

    private static final String TAG = "DBHelper";
    SQLiteDatabase db;
    Context context;

    public DBHelper(Context context) {
        this.context = context;
    }
    public boolean open(){
        String path = context.getFilesDir() + "/"+"user_info.db";
        db = SQLiteDatabase.openOrCreateDatabase(path,null);

        //创建表格
        String sql = "create table if not exists user_info" +
                "(username varchar(50) primary key," +
                "fullname varchar(50)," +
                "password varchar(20)," +
                "age int)";
        try {
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            Log.e(TAG, "open: error"+e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(String username, String fullname, String password, int age){

        if (db != null && db.isOpen()){
            ContentValues values = new ContentValues();
            //准备插入的数据
            values.put("username",username);
            values.put("fullname",fullname);
            values.put("password",password);
            values.put("age",age);

            //进行数据库插入
            long c = db.insert("user_info",null,values);


            if(c > 0)return true;
            else return false;
        }else {
            return false;
        }
    }

    public boolean checkNamePassword(String name, String password){
        Cursor c = db.query("user_info",//表名
                            null,//要显示的内容
                            "username=? and password=?",
                            new String[]{name,password},
                            null,
                            null,
                            null);
        if(c.getCount() > 0)return true;
        else return false;
    }
}
