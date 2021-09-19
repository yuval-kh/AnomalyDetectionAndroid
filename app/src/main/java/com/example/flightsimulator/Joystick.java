package com.example.flightsimulator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class Joystick extends View implements View.OnTouchListener   {
    private float _xDelta;
    private float _yDelta;
    private float centerX;
    private float centerY;
    private ArrayList<JoystickListener> listeners;
    public boolean isInitiated = false;
    public Joystick(Context context) {
        super(context);
        listeners = new ArrayList<>();
    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        listeners = new ArrayList<>();
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        listeners = new ArrayList<>();
    }

    public void addListener(JoystickListener jm){
        listeners.add(jm);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        isInitiated = true;
    }

    public void init(){
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    _xDelta = v.getX() - event.getRawX();
                    _yDelta = v.getY() - event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = (float) (centerX + (event.getRawX() + _xDelta - centerX) * 0.5);
                    float y = (float) (centerY + (event.getRawY() + _yDelta - centerY) * 0.5);
                    if (x > centerX * 2 + 10) {
                        x = centerX * 2 + 10;
                    } else if (x < 0) {
                        x = 0;
                    }
                    if (y < 0) {
                        y = 0;
                    } else if (y > centerY * 2) {
                        y = centerY * 2;
                    }
                    v.animate()
                            .x(x)
                            .y(y)
                            .setDuration(0)
                            .start();
                    for(JoystickListener jm : listeners){
                        jm.onJoystickMoved(x,y);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    v.animate()
                            .x(centerX)
                            .y(centerY)
                            .setDuration(100)
                            .start();
                    for(JoystickListener jm : listeners){
                        jm.onJoystickMoved(centerX,centerY);
                    }
                    break;

                default:
            }
        return true;
    }

    public interface JoystickListener
    {
        void onJoystickMoved(float jx, float jy);
    }
}
