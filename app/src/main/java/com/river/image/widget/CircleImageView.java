package com.river.image.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Created by CaoDongping on 9/6/16.
 */

public class CircleImageView extends de.hdodenhof.circleimageview.CircleImageView {
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(23)
    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setBorderColor(Color.parseColor("#FF4081"));
        } else {
            setBorderColor(Color.WHITE);
        }
    }
}
