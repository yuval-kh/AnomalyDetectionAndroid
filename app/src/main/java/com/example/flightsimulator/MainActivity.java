package com.example.flightsimulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.flightsimulator.databinding.ActivityMainBinding;
import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    private ViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModel();
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(vm);
        activityMainBinding.executePendingBindings();
        Joystick js = findViewById(R.id.joystick);
        js.addListener(vm);
    }
    public void Connect(View view){
        EditText ipText = findViewById(R.id.ip);
        EditText portText = findViewById(R.id.port);
        String ip = ipText.getText().toString();
        if(ip.equals("") || portText.getText().toString().equals("")){
            Toast.makeText(this, "Please Enter a Valid IP and port", Toast.LENGTH_SHORT).show();
            return;
        }
        //hide keyboard
        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
        //
        int port =  Integer.parseInt(portText.getText().toString());
        if(vm.modelConnect(ip,port))
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            else {
            Log.d("view","no");
            Toast.makeText(this, "Connection Error!", Toast.LENGTH_SHORT).show();
        }
    }
}