package com.estyle.teabaike.databinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MyBindingAdapter {

    @BindingAdapter("imgUrl")// 布局中使用的属性名
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(imageView
                    .getContext())
                    .load(url)
                    .into(imageView);
        }
    }

    @BindingAdapter("webData")
    public static void loadWeb(WebView webView, String data) {
        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
    }

}
