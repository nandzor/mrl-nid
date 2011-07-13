package com.android.googlemaps;


import java.util.ArrayList;

import com.android.googlemaps.locations.Locations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class gpsPoints extends Activity {
private EditText name_add;
private TextView latitude_add;
private TextView longitude_add;
public ArrayList<Locations>  locations = new ArrayList<Locations>() ;
private Locations l;
public double longitude;
public double latitude;


@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_tasks);
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new MyLocationListener();
    	mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
       setViewClick();
	}

	private void setViewClick() {
		// TODO Auto-generated method stub
		name_add = (EditText)findViewById(R.id.name);
		latitude_add = (TextView) findViewById(R.id.lat_value);
		longitude_add = (TextView) findViewById(R.id.long_value);
		Button cancelButton = (Button) findViewById(R.id.cancel_button);
		Button addButton = (Button) findViewById(R.id.add_button);
		addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                addPoints();
                Intent intent = new Intent(gpsPoints.this, GoogleMaps.class);
		        startActivity(intent);
            }
        });
		cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            finish();  
            }
        });
		
	}

	private void addPoints() {
		// TODO Auto-generated method stub
		l = new Locations();
	    l.setName(name_add.getText().toString());
	    l.setLatitude(new Double(latitude_add.getText().toString()));
	    l.setLongitude(new Double(longitude_add.getText().toString()));
	     getPointManagerApplication().addLocation(l);
	}

	private pointsManager getPointManagerApplication() {
		// TODO Auto-generated method stub
		pointsManager pma = (pointsManager) getApplication();
		return pma;
	}

	public class MyLocationListener implements LocationListener{
		
		
		@Override
		public void onLocationChanged(Location loc)
		{
			latitude = loc.getLatitude();
			longitude = loc.getLongitude();
			TextView lat = (TextView) findViewById(R.id.lat_value);
			TextView lon = (TextView) findViewById(R.id.long_value);
			lat.setText(String.valueOf(latitude));
			lon.setText(String.valueOf(longitude));
			}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText( getApplicationContext(),
					"Gps Disabled",
					Toast.LENGTH_SHORT ).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText( getApplicationContext(),
					"Gps Enabled",
					Toast.LENGTH_SHORT).show();
		
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		
  }

	
}
