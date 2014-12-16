package com.project.freecookies.dota2assistant;

/**
 * Created by Jefim on 12/13/2014.
 */
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class FlipSensorDetector extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private long lastUpdate;
    public float axisX, axisY, axisZ;
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gyro);
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            //getAccelerometer(event);

            float[] values = event.values;
            TextView txt2change = (TextView)findViewById(R.id.textX);
            axisX = values[0];
            txt2change.setText(String.valueOf(axisX));
            txt2change = (TextView)findViewById(R.id.textY);
            axisY = values[1];
            txt2change.setText(String.valueOf(axisY));
            txt2change = (TextView)findViewById(R.id.textZ);
            axisZ = values[2];
            txt2change.setText(String.valueOf(axisZ));

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                super.onBackPressed();
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
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}