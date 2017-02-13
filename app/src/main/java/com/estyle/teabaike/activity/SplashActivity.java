package com.estyle.teabaike.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.estyle.teabaike.R;


public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("app_config", Context.MODE_PRIVATE);
        sleepTask.execute();
    }

    private AsyncTask sleepTask = new AsyncTask() {

        @Override
        protected Object doInBackground(Object[] params) {
            // 睡眠
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 跳转
            Intent intent = new Intent();
            // 是否首次启动
            boolean isFirstLogin = sharedPreferences.getBoolean("is_first_login", true);
            if (isFirstLogin) {
                intent.setClass(SplashActivity.this, WelcomeActivity.class);
                sharedPreferences.edit()
                        .putBoolean("is_first_login", false)
                        .commit();
            } else {
                intent.setClass(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
            return null;
        }
    };

}
