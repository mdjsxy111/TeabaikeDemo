package com.estyle.teabaike.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.estyle.teabaike.R;

public class PointView extends LinearLayout {

    private int count;

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPointCount(int count) {
        if (count > 0) {
            this.count = count;
            int pointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
            LayoutParams params = new LayoutParams(pointSize, pointSize);
            params.setMargins(0, 0, getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin), 0);
            for (int i = 0; i < count; i++) {
                View point = new View(getContext());
                if (i == count - 1) {
                    params = new LayoutParams(pointSize, pointSize);
                    params.setMargins(0, 0, 0, 0);
                }
                point.setLayoutParams(params);
                point.setBackgroundResource(R.drawable.selector_point);
                addView(point);
            }
            getChildAt(0).setSelected(true);
        }
    }

    public void setSelectedPosition(int position) {
        for (int i = 0; i < count; i++) {
            getChildAt(i).setSelected(i == position);
        }
    }

    public int getPointCount() {
        return count;
    }

}
