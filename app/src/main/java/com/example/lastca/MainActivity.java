package com.example.lastca;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;

    TextView tv;
    StringBuilder sb;
    int sequenceCount = 4, n = 0;
    int[] gameSequence = new int[120];
    int arrayIndex = 0;
    private int maxTilt = 4;
    /* private final double NORTH_MOVE_FORWARD = -2.0;     // upper mag limit
     private final double NORTH_MOVE_BACKWARD = 4.0;      // lower mag limit
     boolean highLimit = false;      // detect high limit
         int counter = 0;                // step counter*/

    Button right, left, up, down, fb;
    CountDownTimer ct = new CountDownTimer(6000, 1500) {

        public void onTick(long millisUntilFinished) {
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish() {
            //mTextField.setText("done!");
            // we now have the game sequence


            for (int i = 0; i < arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence[i]));
            // start next activity
            StartGame();


            // put the sequence into the next activity
            // stack overglow https://stackoverflow.com/questions/3848148/sending-arrays-with-intent-putextra
            //Intent i = new Intent(A.this, B.class);
            //i.putExtra("numbers", array);
            //startActivity(i);

            // start the next activity
            // int[] arrayB = extras.getIntArray("numbers");
        }
    };

    public void StartGame()
    {
        Intent A2 = new Intent(this, TiltGame.class);
        A2.putExtra("gameSequence", gameSequence);
        startActivity(A2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        right = findViewById(R.id.Green);
        left = findViewById(R.id.Yellow);
        up = findViewById(R.id.Blue);
        down = findViewById(R.id.Red);


        /*mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);*/
    }

    private void SetAllButtonInvisible() {
        up.setVisibility(View.INVISIBLE);
        down.setVisibility(View.INVISIBLE);
        left.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);

    }

    // makes one text view visible
    private void SetButtonVisible(Button tv) {
        SetAllButtonInvisible();
        tv.setVisibility(View.VISIBLE);
    }



    public void doPlay(View view) {
        ct.start();

    }

    private void oneButton() {
        //if()
        n = getRandom(sequenceCount);



        switch (n) {
            case 1:
                flashButton(up);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(down);
                gameSequence[arrayIndex++] = RED;
                break;
            case 3:
                flashButton(left);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            case 4:
                flashButton(right);
                gameSequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }   // end switch
    }

    //
    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    private void flashButton(Button button) {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }

    public void doTest(View view) {
        for (int i = 0; i < sequenceCount; i++) {
            int x = getRandom(sequenceCount);

            Toast.makeText(this, "Number = " + x, Toast.LENGTH_SHORT).show();

            if (x == 1)
                flashButton(up);
            else if (x == 2)
                flashButton(down);
            else if (x == 3)
                flashButton(left);
            else if (x == 4)
                flashButton(right);
        }
    }
        //@SuppressLint("SetTextI18n")
    /*public void onSensorChanged(SensorEvent event) {

        float x, y, z;
        x = Math.abs(event.values[0]);
        y = event.values[1];
        z = Math.abs(event.values[2]);

        if (x < 1 && x > -1 && y < 1 && y > -1) {
            if (isFlat == false) {
                isFlat = true;

            }
        }
        // left tilt
        if (y < -maxTilt) {
            if (isFlat) {
                isFlat = false;
                SetButtonVisible(left);
                // add your own outcomes here
            }
        }
        // right tilt
        if (y > maxTilt) {
            if (isFlat) {
                isFlat = false;
                SetButtonVisible(right);
                // add your own outcomes here
            }
        }
        // up tilt
        if (x < -maxTilt) {
            if (isFlat) {
                isFlat = false;
                SetButtonVisible(up);
                // add your own outcomes here
            }
        }
        // down tilt
        if (x > maxTilt) {
            if (isFlat) {
                isFlat = false;
                SetButtonVisible(down);
                // add your own outcomes here
            }
        }



    }*/

        //public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not using
        // }

}