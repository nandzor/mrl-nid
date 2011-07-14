package com.android.googlemaps.locations;

import com.google.android.maps.GeoPoint;

public class Locations {
	
	private String name ;
	private Double latitude;
	private Double longitude;
	private long ID;
	private GeoPoint geopoint;
	private String type;
	private String subtypes_ids ;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	
	public GeoPoint getPoint() {
	    geopoint = new GeoPoint((int)(latitude*1000000.0),
				(int)(longitude*1000000.0));
		return(geopoint);
	}

	public void setID(long id2) {
		ID = id2;
	}

	public long getID() {
		return ID;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void addSubtype_id(String id){
        if(subtypes_ids == null){
        	subtypes_ids = id;
        }else{
		subtypes_ids = subtypes_ids.concat(","+id);
        }
	}
	
	
	public void setSubtypes_ids(String subtypes_ids) {
		this.subtypes_ids = subtypes_ids;
	}

	public String getSubtypes_ids() {
		return subtypes_ids;
	}
	
	

}
