package com.example.flightsimulator;


import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.android.material.slider.Slider;

public class ViewModel extends BaseObservable implements Joystick.JoystickListener {

    private model model;

    public void onRudValueChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        int seekBarValue = seekBar.getProgress();
        double ret = (double) seekBarValue /100;
      //  ret = ret * 2;
        //ret --;
        model.setRud(ret);

    }
    public void onThrValueChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

        int seekBarValue = seekBar.getProgress();
        model.setThr((double)seekBarValue / 100);
    }
    public ViewModel(){
        model = model.getInstance();
    }

//    public void ipUpdate(CharSequence s) {
//        String ip = s.toString();
//        model.setIp(ip);
//    }
//    public void portUpdate(CharSequence s) {
//
//        int port =  Integer.parseInt(s.toString());
//        model.setPort(port);
//    }
    public boolean modelConnect(String ip,int port) {
        return model.connect(ip,port);

    }

    @Override
    public void onJoystickMoved(float jx, float jy) {
        float aileron = convertRange(jx,394,0,1,-1);
        float elevator = convertRange(jy,394,0,1,-1);
        model.movePlane(aileron,elevator);
    }

    float convertRange(float val,float oldMax,float oldMin,float newMax, float newMin){
        float OldRange = (oldMax - oldMin);
        float NewRange = (newMax - newMin);
        return (((val - oldMin) * NewRange) / OldRange) + (newMin);
    }
}
