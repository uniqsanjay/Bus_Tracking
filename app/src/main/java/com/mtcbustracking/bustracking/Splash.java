package com.mtcbustracking.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        createdDB();
        db = openOrCreateDatabase("loginStatus", MODE_PRIVATE, null);
        final Cursor c = db.rawQuery("SELECT * FROM Tables", null);
        if (c.getCount() == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                }
            },3000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this, HomePage.class);
                    startActivity(intent);
                }
            },2000);


        }
    }

    void createdDB() {
        db = openOrCreateDatabase("loginStatus", MODE_PRIVATE, null);
        db.execSQL("create table if not exists Tables(status TEXT);");
        db.close();
    }
}
