package com.example.shruti.myapplication.View.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.shruti.myapplication.R;


public class DividerDecoration extends RecyclerView.ItemDecoration {
    Drawable dr;


    public DividerDecoration(Context context) {

        dr = context.getDrawable(R.drawable.divider);
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + dr.getIntrinsicHeight();

            dr.setBounds(left, top, right, bottom);
            dr.draw(c);

        }
    }
}
//https://github.com/pkhivesara/GiphyRepo/blob/master/app/src/main/java/com/app/giphy/DividerDecoration.java
