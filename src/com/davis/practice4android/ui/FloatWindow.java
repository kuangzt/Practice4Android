package com.davis.practice4android.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class FloatWindow extends LinearLayout {

    private float mTouchStartX;
    private float mTouchStartY;
    private int x;
    private int y;

    private WindowManager wm = (WindowManager) getContext().getApplicationContext().getSystemService("window");
    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

    private Boolean showFlag = false;

    private LayoutInflater mInflater;
    private Resources mResources;
    public FloatWindow(Context context) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setOrientation(VERTICAL);
        setLayoutParams(params);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = context.getResources();
        
        wmParams.type = 2002;
        wmParams.format = 1;
        wmParams.flags |= 8;

        wmParams.gravity = Gravity.LEFT|Gravity.TOP;

        wmParams.x = 60;
        wmParams.y = 100;
        wmParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        
    }

    public void setContentView(int resource){
    	mInflater.inflate(resource, this,true);
    }
    
    public void removeView(){
    	removeAllViews();
    }
    
	@Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mTouchStartX = event.getRawX();
                mTouchStartY = event.getRawY();
                x = wmParams.x;
                y = wmParams.y;

                break;
            case MotionEvent.ACTION_MOVE:
                int xDistance = (int) (event.getRawX() - mTouchStartX);
                wmParams.x = x + xDistance;
                int yDistance = (int) (event.getRawY() - mTouchStartY);
                wmParams.y = y + yDistance;
                updateViewPosition();

                break;

            case MotionEvent.ACTION_UP:
                int xUpDistance = (int) (event.getRawX() - mTouchStartX);
                wmParams.x = x + xUpDistance;
                int yUpDistance = (int) (event.getRawY() - mTouchStartY);
                wmParams.y = y + yUpDistance;

                updateViewPosition();
                break;
        }
        return true;
    }

	public void show(){
        if (!showFlag) {
        	wm.addView(this, wmParams);
            showFlag = true;
        }
	}
    public void hide() {
        if (showFlag) {
            wm.removeView(this);
            showFlag = false;
        }

    }


    private void updateViewPosition() {
        wm.updateViewLayout(this, wmParams);
        showFlag = true;
    }

}

