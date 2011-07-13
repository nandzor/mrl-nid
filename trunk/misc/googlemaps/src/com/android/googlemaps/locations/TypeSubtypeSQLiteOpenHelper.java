package com.android.googlemaps.locations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.android.googlemaps.pointsManager.TYPES;
import static com.android.googlemaps.pointsManager.SUB_TYPES;;

public class TypeSubtypeSQLiteOpenHelper extends SQLiteOpenHelper {


	private static final String DB_NAME = "type_subtype_db.sqlite";
	private static final int VERSION = 1;
	public static final String TYPE_TABLE = "type";
	public static final String TYPE_ID = "_id";
	public static final String TYPE_NAME = "name";
	public static final String PARENT_ID = "parentId";

	public TypeSubtypeSQLiteOpenHelper(Context context){
		super(context, DB_NAME , null, VERSION );
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
          createTable(db);
          insert(db);
          
        }

	private void insert(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		for(int i = 0; i < TYPES.length ;i++ ){
            insertinDB(TYPES[i] , 0, db);
		}
		for(int i = 0; i < TYPES.length ;i++ ){
            String[] subtype = (String[]) SUB_TYPES.get(i);
    		for(int  k = 0 ; k < subtype.length ; k++){
    			insertinDB(subtype[k],i+1,db);
    		}
		}
		
			
	}

	private void createTable(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table "+ TYPE_TABLE +" ( "+ 
				TYPE_ID +" integer primary key not null, "+ 
		        TYPE_NAME +" text, "+
				PARENT_ID +" integer );"
				);
	}

	private void insertinDB(String name , int p_id, SQLiteDatabase db) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(TYPE_NAME, name);
		values.put(PARENT_ID, p_id);
		db.insert(TYPE_TABLE, null, values);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// currently used 
	}

	
}
