package com.android.googlemaps;

import java.util.ArrayList;

import com.android.googlemaps.adapter.MyArrayAdapter;
import com.android.googlemaps.locations.TypeSubtypeSQLiteOpenHelper;
import com.android.googlemaps.R;

import static com.android.googlemaps.pointsManager.TYPES;
import static com.android.googlemaps.locations.TypeSubtypeSQLiteOpenHelper.*;
import static com.android.googlemaps.pointsManager.type_name;
import static com.android.googlemaps.pointsManager.type_id;
import static com.android.googlemaps.pointsManager.subtypes;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class addDetails extends ListActivity {

	TypeSubtypeSQLiteOpenHelper helper;
	public static ArrayList SUB_TYPES;
 

	//static final String[] TYPES = new String[] {"Auto", "Education", "Food and Drink","Government", "Health","Leisure","Recreation","Transport","Travel", "Sports", "services"};

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		 	// setListAdapter((ListAdapter) new ArrayAdapter<String>(this, 
		 //                    android.R.layout.simple_list_item_multiple_choice, TYPES));
		this.setListAdapter(new MyArrayAdapter(addDetails.this, TYPES));
		//this.setListAdapter(new ArrayAdapter<String>(this, R.layout.type_layout,
			//	R.id.label, TYPES));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST,   Menu.NONE, "Add").setIcon(R.drawable.add);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 super.onOptionsItemSelected(item);
		finish();
	    return true;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Cursor c = getPointManagerApplication().getTypeId(keyword);
	 	type_name = keyword;
//	 	addGeopoints g = new addGeopoints();
//	 	g.setTypeText(type_name);
    	type_id = c.getInt(0);
		//Toast.makeText(this, "You selected: " + keyword , Toast.LENGTH_LONG).show();
		Intent intent = new Intent(addDetails.this, addSubtype.class);
		startActivity(intent);
	}
	
	private pointsManager getPointManagerApplication() {
		// TODO Auto-generated method stub
		pointsManager pma = (pointsManager) getApplication();
		return pma;
	}
	
	



}