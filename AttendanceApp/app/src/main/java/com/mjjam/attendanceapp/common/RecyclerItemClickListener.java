package com.mjjam.attendanceapp.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Archish on 1/30/2016.
 */

/***
 * RecyclerItemClick Commons
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        public void onItemLongClicked(View view, int position);
    }


    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener, OnItemLongClickListener mLongListener) {
        mListener = listener;
        this.mLongListener = mLongListener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mLongListener.onItemLongClicked(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}