package com.project.freecookies.dota2assistant;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.MotionEventCompat;


public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private long lastUpdate;
    public float axisX, axisY, axisZ;
    private GestureDetector gestureDetector;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Intent myIntent = new Intent(this,FlipSensorDetector.class);
                startActivity(myIntent);
                return true;
            case (MotionEvent.ACTION_MOVE) :

                return true;
            case (MotionEvent.ACTION_UP) :

                return true;
            case (MotionEvent.ACTION_CANCEL) :

                return true;
            case (MotionEvent.ACTION_OUTSIDE) :

                return true;
            default :
                return super.onTouchEvent(event);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            TextView txt2change = (TextView)findViewById(R.id.textViewX);
            axisX = values[0];
            txt2change.setText(String.valueOf(axisX));
            txt2change = (TextView)findViewById(R.id.textViewY);
            axisY = values[1];
            txt2change.setText(String.valueOf(axisY));
            txt2change = (TextView)findViewById(R.id.textViewZ);
            axisZ = values[2];
            txt2change.setText(String.valueOf(axisZ));
        }

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}