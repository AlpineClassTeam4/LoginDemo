package com.neusoft.liufeiyu.sharedpreferencestest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerClick(View v) {
        //获取SharedPreferences对象，准备操作数据
        SharedPreferences preferences = getSharedPreferences("user_date",MODE_PRIVATE);
        //获取用户名、密码、年龄的用户数据
        EditText etName = findViewById(R.id.editText3);
        EditText etPassword = findViewById(R.id.editText4);
        EditText etAge = findViewById(R.id.editText5);
        EditText etFullname = findViewById(R.id.editText6);


//        //保存注册信息
//        SharedPreferences.Editor editor = preferences.edit();//获取编辑器
//        editor.putString("user_name",etName.getText().toString());//保存用户名
//        editor.putString("user_password",etPassword.getText().toString());//保存密码
//        int age;
//        try{
//            age = Integer.parseInt(etAge.getText().toString());
//            editor.putInt("user_age",age);//保存年龄
//        }catch (Exception e){
//            Toast.makeText(this,"年龄输入错误",Toast.LENGTH_SHORT).show();
//            editor.clear();
//            return;
//        }
//        editor.putString("user_fullname",etFullname.getText().toString());
//        //提交保存
//        editor.commit();

        //信息保存到数据库
        boolean ret;
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.open();
        ret = dbHelper.insert(etName.getText().toString(),
                etFullname.getText().toString(),
                etPassword.getText().toString(),
                Integer.parseInt(etAge.getText().toString()));
        if(!ret) {
            Toast.makeText(this,"数据库操作失败",Toast.LENGTH_SHORT).show();
        }
        //返回登录界面
        Toast.makeText(this,"账号注册成功",Toast.LENGTH_SHORT).show();
        finish();
    }
}
