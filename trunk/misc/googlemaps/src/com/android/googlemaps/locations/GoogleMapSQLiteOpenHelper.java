package com.android.googlemaps.locations;

import com.android.googlemaps.R;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoogleMapSQLiteOpenHelper extends SQLiteOpenHelper {

	public static final String LOCATION_TABLE = "tasks";
	public static final String LOCATION_ID= "id";
	public static final String LOCATION_NAME = "name";
	public static final String LOCATION_GEOPOINT = "geopoints";
	public static final String LOCATION_LATITUDE = "latitude";
	public static final String LOCATION_LONGITUDE = "longitude";
	public static final String LOCATION_TYPE = "type";
	public static final String LOCATION_SUBTYPE = "subtypes_ids";
	private static String DB_NAME = "location_db.sqlite";
	private static int VERSION = 1;
	

	public GoogleMapSQLiteOpenHelper(Context context){
		super(context, DB_NAME , null, VERSION );
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
          createTable(db);
         	}

	private void createTable(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table "+ LOCATION_TABLE +" ( "+ 
				LOCATION_ID +" integer primary key not null, "+ 
				LOCATION_NAME +" text, "+
				LOCATION_LATITUDE +" text, "+
				LOCATION_LONGITUDE +" text, " +
				LOCATION_TYPE+" text, "+
				LOCATION_SUBTYPE+ " text);"

				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// currently used 

	}


}
