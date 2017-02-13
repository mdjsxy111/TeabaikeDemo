package com.estyle.teabaike.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.estyle.teabaike.activity.ContentActivity;
import com.estyle.teabaike.bean.HeadlineBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HeadlinePagerAdapter extends PagerAdapter implements View.OnClickListener {

    private Context context;
    private List<ImageView> viewList;

    public HeadlinePagerAdapter(Context context, List<HeadlineBean.DataBean> datas) {
        this.context = context;
        if (datas.size() < 4) {
            datas.addAll(datas);
        }
        viewList = new ArrayList<>();
        for (HeadlineBean.DataBean data : datas) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setTag(data);
            imageView.setOnClickListener(this);
            Picasso.with(context)
                    .load(data.getImage())
                    .into(imageView);
            viewList.add(imageView);
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % viewList.size();
        container.addView(viewList.get(realPosition));
        return viewList.get(realPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = position % viewList.size();
        container.removeView(viewList.get(realPosition));
    }

    @Override
    public void onClick(View v) {
        HeadlineBean.DataBean data = (HeadlineBean.DataBean) v.getTag();
        String id = data.getId();
        if (TextUtils.isEmpty(id)) {
            String link = data.getLink();
            id = link.substring(link.lastIndexOf("/") + 1);
        }
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra("id", Long.parseLong(id));
        context.startActivity(intent);
    }
}
