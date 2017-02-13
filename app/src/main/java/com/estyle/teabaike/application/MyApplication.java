package com.estyle.teabaike.application;

import android.app.Application;

import com.estyle.teabaike.bean.DaoMaster;
import com.estyle.teabaike.bean.DaoSession;
import com.estyle.teabaike.constant.Url;

import org.greenrobot.greendao.database.Database;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private Retrofit retrofit;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initDatabaseUtil();
        initNetworkUtil();
    }

    private void initDatabaseUtil() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "tea_baike.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    // 初始化网络数据请求工具
    private void initNetworkUtil() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}