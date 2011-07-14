package com.android.googlemaps;


import java.util.ArrayList;

import com.android.googlemaps.locations.Locations;
import static com.android.googlemaps.pointsManager.type_name;
import static com.android.googlemaps.pointsManager.subtype_id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addGeopoints extends Activity {
private EditText name_add;
private EditText latitude_add;
private EditText longitude_add;
public ArrayList<Locations>  locations = new ArrayList<Locations>() ;
private Locations l;
private TextView type;

@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_tasks);
		setViewClick();
	}

	private void setViewClick() {
		// TODO Auto-generated method stub
		Button typeButton = (Button) findViewById(R.id.type_button);
		Button addButton = (Button) findViewById(R.id.add_button);
		Button cancelButton = (Button) findViewById(R.id.cancel_button);
		type = (TextView) findViewById(R.id.type);
		type.setText("TYPE : "+ type_name);
		typeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(addGeopoints.this, addDetails.class);
		        startActivity(intent);
	      }
        });
		addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                addPoints();
                
                Intent intent = new Intent(addGeopoints.this, GoogleMaps.class);
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
		name_add = (EditText)findViewById(R.id.name);
		latitude_add = (EditText) findViewById(R.id.lat_value);
		longitude_add = (EditText) findViewById(R.id.long_value);
		l = new Locations();
	    l.setName(name_add.getText().toString());
	    l.setLatitude(new Double(latitude_add.getText().toString()));
	    l.setType(type_name);
	    for(int i = 0 ; i < subtype_id.size(); i++ ){
	    	l.addSubtype_id(String.valueOf(subtype_id.get(i)));
	    	//Toast.makeText(this, "added id " + subtype_id.get(i), Toast.LENGTH_LONG).show();
	    }
	    //Toast.makeText(this, l.getSubtypes_ids(),Toast.LENGTH_SHORT).show();
	    l.setLongitude(new Double(longitude_add.getText().toString()));
	     getPointManagerApplication().addLocation(l);
	     
	}

	private pointsManager getPointManagerApplication() {
		// TODO Auto-generated method stub
		pointsManager pma = (pointsManager) getApplication();
		return pma;
	}
}
