package com.neusoft.liufeiyu.sharedpreferencestest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etPassword;

    TextUtils textUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editText);
        etPassword = findViewById(R.id.editText2);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = etName.getText().toString();
                String inputPassword = etPassword.getText().toString();
                //从preferences中获取注册的用户名密码
//                String regName = preferences.getString("user_name","");
//                String regpwd = preferences.getString("user_password","");
//                if (inputName.equals(regName) && inputPassword.equals(regpwd)){

                DBHelper db = new DBHelper(MainActivity.this);
                db.open();
                if(db.checkNamePassword(inputName,inputPassword)){
                    //验证成功
                    Intent i = new Intent(MainActivity.this,HomeActivity.class);
                    i.putExtra("name",inputName);
                    //临时存入shar_pref
                    //数据库操作成功后再存入sharepref
                    SharedPreferences preferences = getSharedPreferences("user_date",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();//获取编辑器
                    editor.putString("user_name",inputName);//保存用户名
                    editor.putString("user_password",inputPassword);//保存密码
                    editor.commit();
                    startActivity(i);
                    finish();
                }else {
                    //验证失败
                    Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
