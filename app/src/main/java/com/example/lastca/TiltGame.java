package com.example.lastca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class TiltGame extends AppCompatActivity implements SensorEventListener{

    private int maxTilt = 4;
    private final double NORTH_MOVE_FORWARD = -2.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 4.0;      // lower mag limit
    boolean highLimit = false;      // detect high limit
    int counter = 0;                // step counter
    private boolean isFlat = false;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    Button right, left,up,down, fb;
    public int[] playerSequence = new int[120];
    public int[] gameSequence;
    int n = 0;
    boolean sequenceMatch = true;
    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.activity_tilt_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gameSequence = getIntent().getIntArrayExtra("gameSequence");
        right = findViewById(R.id.Green);
        left = findViewById(R.id.Yellow);
        up = findViewById(R.id.Blue);
        down = findViewById(R.id.Red);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener((SensorEventListener) this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener((SensorEventListener) this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener((SensorListener) this);
    }
    private void flashButton(Button button) {
        fb = button;
        fb.setPressed(true);


    }
    public void unflashButtons(){
        if(up.isPressed() == true)
        {
            up.setPressed(false);
            playerSequence[n] = 1;
            n++;
        }
        else if(down.isPressed() == true)
        {
            down.setPressed(false);
            playerSequence[n] = 2;
            n++;
        }
       else if(left.isPressed() == true)
        {
            left.setPressed(false);
            playerSequence[n] = 3;
            n++;
        }
       else if(right.isPressed() == true)
        {
            right.setPressed(false);
            playerSequence[n] = 4;
            n++;
        }
       if(n == gameSequence.length)
       {
           for (int i = 0; i < gameSequence.length; i++)
           {
               if(gameSequence[i] != playerSequence[i])
               {
                   sequenceMatch = false;
               }
           }
           finish();
       }
    }

    public void onSensorChanged(SensorEvent event) {

        float x, y, z;
        x = Math.abs(event.values[0]);
        y = event.values[1];
        z = Math.abs(event.values[2]);

        if (x < 1 && x > -1 && y < 1 && y > -1) {
            if (isFlat == false) {
                isFlat = true;
                unflashButtons();

            }
        }
        // left tilt
        if (y < -maxTilt) {
            if (isFlat) {
                isFlat = false;
                flashButton(left);
                // add your own outcomes here
            }
        }
        // right tilt
        if (y > maxTilt) {
            if (isFlat) {
                isFlat = false;
                flashButton(right);
                // add your own outcomes here
            }
        }
        // up tilt
        if (x < -maxTilt) {
            if (isFlat) {
                isFlat = false;
                flashButton(up);
                // add your own outcomes here
            }
        }
        // down tilt
        if (x > maxTilt) {
            if (isFlat) {
                isFlat = false;
                flashButton(down);
                // add your own outcomes here
            }
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}