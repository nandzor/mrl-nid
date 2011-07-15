package com.android.googlemaps;

import java.util.ArrayList;

import com.android.googlemaps.locations.GoogleMapSQLiteOpenHelper;
import com.android.googlemaps.locations.Locations;
import com.android.googlemaps.locations.TypeSubtypeSQLiteOpenHelper;

import static com.android.googlemaps.locations.GoogleMapSQLiteOpenHelper.*;
import static com.android.googlemaps.locations.TypeSubtypeSQLiteOpenHelper.PARENT_ID;
import static com.android.googlemaps.locations.TypeSubtypeSQLiteOpenHelper.TYPE_ID;
import static com.android.googlemaps.locations.TypeSubtypeSQLiteOpenHelper.TYPE_NAME;
import static com.android.googlemaps.locations.TypeSubtypeSQLiteOpenHelper.TYPE_TABLE;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class pointsManager extends Application {

	private ArrayList<Locations> locations ;
	private SQLiteDatabase database1;
	private SQLiteDatabase database2;
	public static String[] TYPES;
	public static ArrayList SUB_TYPES;
	public static String type_name ;
	public static int type_id ;
	public static ArrayList subtypes  ;
	public static ArrayList subtype_id;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
        super.onCreate();
        GoogleMapSQLiteOpenHelper helper = new GoogleMapSQLiteOpenHelper(this); 
        database1 = helper.getWritableDatabase();
        getallArrays();
	 	TypeSubtypeSQLiteOpenHelper typehelper = new TypeSubtypeSQLiteOpenHelper(this);
	 	try {
            database2 = typehelper.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(this, "misfunctioning open" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
      	   loadLocations();
	}	

	Cursor getTypeId(String key) {
		// TODO Auto-generated method stub
		 Cursor c = database2.query(true, TYPE_TABLE, new String[] {
                TYPE_ID,TYPE_NAME,PARENT_ID}, 
        		TYPE_NAME+ " like '"+ key +"'" , 
        		null,
        		null, 
        		null, 
        		null, 
        		null);
		if(c != null){
		  c.moveToFirst();
		}
		return c;
	}
		
	private void loadLocations() {
		// TODO Auto-generated method stub
		locations = new ArrayList<Locations>();
		Cursor locationCursor = database1.query(LOCATION_TABLE, new String[] {
				LOCATION_ID ,
				LOCATION_NAME,
				LOCATION_LATITUDE,
			    LOCATION_LONGITUDE,
			    LOCATION_TYPE,
			    LOCATION_SUBTYPE},
				null,null,null,null, null);
		if(locationCursor != null){
		locationCursor.moveToFirst();
		Locations l;
		if(! locationCursor.isAfterLast()){
			do{
				long id = locationCursor.getLong(0);
			    String name = locationCursor.getString(1);
			    Double latitude = locationCursor.getDouble(2);
			    Double longitude = locationCursor.getDouble(3);
			    String type = locationCursor.getString(4);
			    String subtypes_ids = locationCursor.getString(5);
			    l = new Locations();
			    l.setID(id);
			    l.setName(name);
			    l.setLatitude(latitude);
			    l.setLongitude(longitude);
			    l.setType(type);
			    l.setSubtypes_ids(subtypes_ids);
			    locations.add(l);
			}while (locationCursor.moveToNext());
		}
		}
		locationCursor.close();
	}


	public void addLocation(Locations l) {
		 assert(null != l);
	       ContentValues values = new ContentValues();
	       values.put(LOCATION_NAME, l.getName());
	       values.put(LOCATION_LATITUDE, l.getLatitude());
	       values.put(LOCATION_LONGITUDE, l.getLongitude());
	       values.put(LOCATION_TYPE, l.getType());
	       values.put(LOCATION_SUBTYPE, l.getSubtypes_ids());
	       long id = database1.insert(LOCATION_TABLE,null, values);
	       l.setID(id);
	     //  locations.add(l);
	       type_name = null;
	       subtype_id = new ArrayList() ;
	       subtypes = new ArrayList<String>();
           type_id = 0;
	}
            
   public ArrayList<Locations> getLocations(){
	   loadLocations();
       return locations;
   }
   
   public void setLocations(ArrayList<Locations> current) {
       this.locations = current;
   }

	private void getallArrays() {
		// TODO Auto-generated method stub
		TYPES = getResources().getStringArray(R.array.TYPES);
		subtypes = new ArrayList();
		subtype_id = new ArrayList();
		SUB_TYPES = new ArrayList();
		SUB_TYPES.add(getResources().getStringArray(R.array.Auto));
		SUB_TYPES.add(getResources().getStringArray(R.array.education));
		SUB_TYPES.add(getResources().getStringArray(R.array.Food_and_Drink));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Government));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Health));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Leisure));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Recreation));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Transport));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Travel));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Sports));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Services));
	    SUB_TYPES.add(getResources().getStringArray(R.array.Shopping));
	}
   
}
