package com.estyle.teabaike.callback;


import com.estyle.teabaike.bean.ContentBean;
import com.estyle.teabaike.constant.Url;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ContentHttpService {

    @GET(Url.CONTENT_URL)
    public Observable<ContentBean> getObservable(@Query(Url.ID) long id);

}
