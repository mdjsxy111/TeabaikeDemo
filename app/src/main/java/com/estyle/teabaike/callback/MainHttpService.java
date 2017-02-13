package com.estyle.teabaike.callback;


import com.estyle.teabaike.bean.MainBean;
import com.estyle.teabaike.constant.Url;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MainHttpService {

    @GET(Url.HEADLINE_URL)
    public Observable<MainBean> getHeadlineObservable(@Query(Url.PAGE) int page);

    @GET(Url.CHANNEL_URL)
    public Observable<MainBean> getMainObservable(@Query(Url.TYPE) int type, @Query(Url.PAGE) int page);

}
