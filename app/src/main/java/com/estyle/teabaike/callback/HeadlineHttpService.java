package com.estyle.teabaike.callback;


import com.estyle.teabaike.bean.HeadlineBean;
import com.estyle.teabaike.constant.Url;

import retrofit2.http.GET;
import rx.Observable;

public interface HeadlineHttpService {

    @GET(Url.HEADLINE_HEADER_URL)
    public Observable<HeadlineBean> getObservable();

}
