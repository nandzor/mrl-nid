package com.android.googlemaps;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static com.android.googlemaps.pointsManager.type_id;
import static com.android.googlemaps.pointsManager.subtypes;
import static com.android.googlemaps.pointsManager.subtype_id;


public class addSubtype extends ListActivity {
	
	private String[] SUB_TYPES;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        getSubType();
        //subtype = new ArrayList<String>();
		this.setListAdapter((ListAdapter) new ArrayAdapter<String>(this, 
 				                    R.layout.subtype, SUB_TYPES ));
		ListView lv = getListView();
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //Toast.makeText(this, "You selected: "+ id[0], Toast.LENGTH_LONG).show();
	}
	
/*	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST,   Menu.NONE, "Do").setIcon(R.drawable.add);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 super.onOptionsItemSelected(item);
         Intent intent = new Intent(addSubtype.this,addGeopoints.class);
         startActivity(intent);
		 return true;
	}*/
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "You selected: " + keyword , Toast.LENGTH_LONG).show();
		Cursor c = getPointManagerApplication().getTypeId(keyword);
		int s_id = c.getInt(0);
		if(subtypes.contains(keyword))
		{
		     subtype_id.remove(s_id);
	         subtypes.remove(keyword);
		}else {
			subtypes.add(keyword);
			subtype_id.add(s_id);
		}
	  
	}
	
	private pointsManager getPointManagerApplication() {
		// TODO Auto-generated method stub
		pointsManager pma = (pointsManager) getApplication();
		return pma;
	}
	
	private void getSubType() {
		// TODO Auto-generated method stub
		switch(type_id){
		case 1:
			   SUB_TYPES = getResources().getStringArray(R.array.Auto);
			   break;
		case 2:
			   SUB_TYPES = getResources().getStringArray(R.array.education);
			   break;
		case 3:
			   SUB_TYPES = getResources().getStringArray(R.array.Food_and_Drink);
			   break;
		case 4:
			   SUB_TYPES = getResources().getStringArray(R.array.Government);
			   break;
		case 5:
			   SUB_TYPES = getResources().getStringArray(R.array.Health);
			   break;
		case 6:
			   SUB_TYPES = getResources().getStringArray(R.array.Leisure);
			   break;
		case 7:
			   SUB_TYPES = getResources().getStringArray(R.array.Recreation);
			   break;
		case 8:
			   SUB_TYPES = getResources().getStringArray(R.array.Transport);
			   break;
		case 9:
			   SUB_TYPES = getResources().getStringArray(R.array.Travel);
			   break;
		case 10:
			   SUB_TYPES = getResources().getStringArray(R.array.Sports);
			   break;
		case 11:
			   SUB_TYPES = getResources().getStringArray(R.array.Services);
			   break;
		case 12:
			   SUB_TYPES = getResources().getStringArray(R.array.Shopping);
			   break;
		}
	}

}
