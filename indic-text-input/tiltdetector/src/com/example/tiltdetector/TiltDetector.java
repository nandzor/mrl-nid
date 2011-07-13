package com.example.tiltdetector;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.SensorListener;

@SuppressWarnings("deprecation")
public class TiltDetector extends Activity implements SensorListener {
	
	final String tag = "IBMEyes";
	SensorManager sm = null;
	
	TextView xViewA = null;
	TextView yViewA = null;
	TextView zViewA = null;
	TextView xViewO = null;
	TextView yViewO = null;
	TextView zViewO = null;
	TextView Text = null;
	

	
	public float yViewp = 20;
	public float zViewp = 20;
	public float yViewn = -20;
	public float zViewn = -20;
	public float yViewp1 = 30;
	public float zViewp1 = 30;
	public float yViewn1 = -30;
	public float zViewn1 = -30;
	public float initial = 0;
	public float initial1 = 2;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(R.layout.main);
        xViewA = (TextView) findViewById(R.id.xbox);
        yViewA = (TextView) findViewById(R.id.ybox);
        zViewA = (TextView) findViewById(R.id.zbox);
        xViewO = (TextView) findViewById(R.id.xboxo);
        yViewO = (TextView) findViewById(R.id.yboxo);
        zViewO = (TextView) findViewById(R.id.zboxo);
        Text = (TextView) findViewById(R.id.direction); 
        
    }
    
    
    
    public void onSensorChanged(int sensor, float[] values) 
    {
        synchronized (this) 
        {
            Log.d(tag, "onSensorChanged: " + sensor + ", x: " + values[0] + ", y: " + values[1] + ", z: " + values[2]);
            if (sensor == SensorManager.SENSOR_ORIENTATION) 
            	{
	            	xViewO.setText("Orientation X: " + values[0]);
	            	yViewO.setText("Orientation Y: " + values[1]);
	            	zViewO.setText("Orientation Z: " + values[2]);
	            	
	            	if (values[1] >= yViewp && values[1] < yViewp1)
	            	{
	            		Text.setText("Direction is: UP");
	            		setContentView(R.layout.main);
	            	}
	            	else if(values[1] <= yViewn && values[1] > yViewn1)
	            	{
	            		Text.setText("Direction is: DOWN");
	            		setContentView(R.layout.main1);
	            	}
	            	else if(values[2] >= zViewp && values[2] < zViewp1)
	            	{
	            		Text.setText("Direction is: LEFT");
	            		setContentView(R.layout.main2);
	            	}
	            	else if(values[2] <= zViewn && values[2] > zViewp1)
	            	{
	            		Text.setText("Direction is: RIGHT");
	            		setContentView(R.layout.main3);
	            	}
	            	else if(values[1] >= yViewp1)
	            	{
	            		Text.setText("Direction is: Extreme UP");
	            		setContentView(R.layout.main4);
	            	}
	            	else if(values[1] <= yViewn1)
	            	{
	            		Text.setText("Direction is: Extreme DOWN");
	            		setContentView(R.layout.main5);
	            	}
	            	else if(values[2] >= zViewp1)
	            	{
	            		Text.setText("Direction is: Extreme LEFT");
	            		setContentView(R.layout.main6);
	            	}
	            	else if(values[2] <= zViewn1)
	            	{
	            		Text.setText("Direction is: Extreme RIGHT");
	            		setContentView(R.layout.main7);
	            	}
	            	else if (values[1] >= initial || values[1] <= initial1)
	            	{
	            		Text.setText("Direction in: CENTRE");
	            		setContentView(R.layout.main);
	            	}
	            		else if (values[2] >= initial || values[2] <= initial1)
	            		{
	            			Text.setText("Direction is: CENTRE");
	            			setContentView(R.layout.main);
	            		}
	            	}
	            	}
	            	
            	
            if (sensor == SensorManager.SENSOR_ACCELEROMETER) 
            	{
	            	xViewA.setText("Accel X: " + values[0]);
	            	yViewA.setText("Accel Y: " + values[1]);
	            	zViewA.setText("Accel Z: " + values[2]);
            	}       
            }
      
    public void onAccuracyChanged(int sensor, int accuracy) 
    {
    	Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
        
    }
 

    @Override
    protected void onResume() 
    {
        super.onResume();
        sm.registerListener(this, 
                SensorManager.SENSOR_ORIENTATION |
        		SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void onStop() {
        sm.unregisterListener(this);
        super.onStop();
    }    
    
    
}