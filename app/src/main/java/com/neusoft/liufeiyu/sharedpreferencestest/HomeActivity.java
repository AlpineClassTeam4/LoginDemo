package com.neusoft.liufeiyu.sharedpreferencestest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "DATEBASE";
    DBHelper dbHelper = new DBHelper(this);
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button = findViewById(R.id.button_login);
        TextView textView = findViewById(R.id.textView2);

        Intent intent = getIntent();

        SharedPreferences preferences = getSharedPreferences("user_date",MODE_PRIVATE);

//        String fullname = preferences.getString("user_name","");


        dbHelper.open();
        Cursor c = dbHelper.db.query("user_info",null,"username=?",
                new String[]{intent.getStringExtra("name")},
        null,null,null,null);

        while (c.moveToNext()){
            name = c.getString(c.getColumnIndex("username"));
            textView.setText(name+" 您好，欢迎登录！");
            Toast.makeText(HomeActivity.this,name+" 已登录",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: "+name);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_date",MODE_PRIVATE);
                String user_date = sharedPreferences.getString("user_name","");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_name");
                editor.remove("user_password");
                editor.commit();
                finish();
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this,name+" 已登出",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
