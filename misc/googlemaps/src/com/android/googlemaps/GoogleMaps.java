package com.android.googlemaps;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import static com.android.googlemaps.pointsManager.TYPES;
import com.android.googlemaps.locations.Locations;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import java.util.ArrayList;
import java.util.List;

public class GoogleMaps extends MapActivity implements OnGestureListener , OnDoubleTapListener{
	private MapView map=null;
	private MyLocationOverlay me=null;
	private MapController mapController;
	private ArrayList<Locations> location;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		
		map=(MapView)findViewById(R.id.map);
		map.setSatellite(false);
		map.setBuiltInZoomControls(true);
		getOverlaysitems();
		/*Drawable marker=getResources().getDrawable(R.drawable.marker);
        		
    marker.setBounds(0, 0, marker.getIntrinsicWidth(),
 						marker.getIntrinsicHeight());
		GeoPoint g = getPoint(23.18865102161924,72.62698888778687);
		String n = "Kokel";		
		map.getOverlays().add(new SitesOverlay(marker , g, n));
		g = getPoint(23.1885,72.6290);
		n = "heena";
		map.getOverlays().add(new SitesOverlay(marker , g, n));*/
		me=new MyLocationOverlay(this, map);
		map.getOverlays().add(me);	
	CheckBox satelliteView = (CheckBox) findViewById(R.id.satellite_view);
	satelliteView.setOnClickListener(new Button.OnClickListener(){ 
	   public void onClick(View v){
		   	CheckBox v2 = (CheckBox) v;
		   	if(v2.isChecked())
		   		map.setSatellite(true);
		   	else
		   		map.setSatellite(false);
			}
		}
	);
	}
	
	private void getOverlaysitems() {
		// TODO Auto-generated method stub
        Drawable marker;
		
		
		//map.getOverlays().add(new SitesOverlay(marker));
	       if(getPointManagerApplication().getLocations() != null)
	       {
	    	   location = getPointManagerApplication().getLocations();
	    	   int size = location.size();
	    	   for(int i = 0 ; i <size ; i++){
		    	    Locations l = location.get(i);  
					GeoPoint g = l.getPoint();
					String n = l.getName();
					String t = l.getType();
					if(t.equals(TYPES[0])){
						marker =getResources().getDrawable(R.drawable.auto);
					}else if(t.equals(TYPES[1])){
						marker =getResources().getDrawable(R.drawable.education);
					}else if(t.equals(TYPES[2])){
						marker =getResources().getDrawable(R.drawable.food_and_drink);
					}else if(t.equals(TYPES[3])){
						marker =getResources().getDrawable(R.drawable.government);
					}else if(t.equals(TYPES[4])){
						marker =getResources().getDrawable(R.drawable.health);
					}else if(t.equals(TYPES[5])){
						marker =getResources().getDrawable(R.drawable.leisure);
					}else if(t.equals(TYPES[6])){
						marker =getResources().getDrawable(R.drawable.recreation);
					}else if(t.equals(TYPES[7])){
						marker =getResources().getDrawable(R.drawable.transport);
					}else if(t.equals(TYPES[8])){
						marker =getResources().getDrawable(R.drawable.travel);
					}else if(t.equals(TYPES[9])){
						marker =getResources().getDrawable(R.drawable.sports);
					}else if(t.equals(TYPES[10])){
						marker =getResources().getDrawable(R.drawable.services);
					}else if(t.equals(TYPES[11])){
						marker =getResources().getDrawable(R.drawable.shopping);
					}else {
						marker = getResources().getDrawable(R.drawable.marker);
					}
		            
					marker.setBounds(0, 0, marker.getIntrinsicWidth(),
							marker.getIntrinsicHeight());
		            map.getOverlays().add(new SitesOverlay(marker,g,n));
			   }
	    	   
	       }

	}
	
	private pointsManager getPointManagerApplication() {
		// TODO Auto-generated method stub
		pointsManager pma = (pointsManager) getApplication();
		return pma;
	}

	private GeoPoint getPoint(double lat, double lon) {
		return(new GeoPoint((int)(lat*1000000.0),
							(int)(lon*1000000.0)));
	    }
	
	public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, Menu.FIRST,   Menu.NONE, "Add").setIcon(R.drawable.add);
    	menu.add(0, Menu.FIRST+1, Menu.NONE, "add current location").setIcon(R.drawable.gps);
    	return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1){
			Intent intent1 = new Intent(GoogleMaps.this, addGeopoints.class);
	        startActivity(intent1);
		}else if(item.getItemId() == 2){
			Intent intent2 = new Intent(GoogleMaps.this, gpsPoints.class);
	        startActivity(intent2);
		}
		return true;
    }
	

	
   	@Override
	public void onResume() {
		super.onResume();
		
		me.enableCompass();
	}		
	
	@Override
	public void onPause() {
		super.onPause();
		
		me.disableCompass();
	}		
	
 	@Override
	protected boolean isRouteDisplayed() {
		return(true);
	}
	
 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			map.setSatellite(!map.isSatellite());
			return(true);
		}
		else if (keyCode == KeyEvent.KEYCODE_Z) {
			map.displayZoomControls(true);
			return(true);
		}
		
		return(super.onKeyDown(keyCode, event));
	}
	
	private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items=new ArrayList<OverlayItem>();
			private Drawable marker=null;
	    	private ArrayList<Locations> location;

			
			public SitesOverlay(Drawable marker, GeoPoint g, String n) {
				super(marker);
				this.marker=marker;
			 items.add(new OverlayItem(g, "DA", n));
			/*items.add(new OverlayItem(getPoint
					(23.188909901214362,72.62872159481049),
								"DA", "Resource centre"));
	      
			/*	items.add(new OverlayItem(getPoint
						(23.188554866213448,72.6290112733841),
									"DA", "Lotus Pond"));
				items.add(new OverlayItem(getPoint
						(23.18841926231854,72.62804567813873),
									"DA", "CEP Building"));
				items.add(new OverlayItem(getPoint
						(23.18865102161924,72.62698888778687),
									"DA", "Cafeteria"));
				items.add(new OverlayItem(getPoint
						(23.18865102161924,72.62698888778687),
									"DA", "Cafeteria"));
				items.add(new OverlayItem(getPoint
				    	(23.187006508109413,72.62817174196243),
									"DA", "Lab Building"));
				items.add(new OverlayItem(getPoint
						(23.186631743581287,72.62897104024887),
									"DA", "Lecture Theatre-3"));
				items.add(new OverlayItem(getPoint
						(23.186525724478216,72.62889593839645),
									"DA", "Lecture Theatre-2"));
				items.add(new OverlayItem(getPoint
						(23.186326013846504,72.62861162424088),
									"DA", "Lecture Theatre-1"));
				items.add(new OverlayItem(getPoint
						(23.18912933209788,72.62700766324997),
									"DA", "Open Air Theatre"));
				items.add(new OverlayItem(getPoint
						(23.188993728785398,72.62804836034775),
									"DA", "Administration Block"));
				items.add(new OverlayItem(getPoint
						(23.189338900583127,72.62799471616745),
									"DA", "Faculty Block-4"));
				items.add(new OverlayItem(getPoint
						(23.18953614121045,72.62823611497879),
									"DA", "Faculty Block-3"));
				items.add(new OverlayItem(getPoint
						(23.18934136609277,72.62861162424088),
									"DA", "Faculty Block-2"));
				items.add(new OverlayItem(getPoint
						(23.189235349137334,72.6288852095604),
									"DA", "Faculty Block-1"));*/
				populate();
			}
			
		
			private pointsManager getPointManagerApplication() {
				// TODO Auto-generated method stub
				pointsManager pma = (pointsManager) getApplication();
				return pma;
			}

			private GeoPoint getPoint(double lat, double lon) {
			return(new GeoPoint((int)(lat*1000000.0),
								(int)(lon*1000000.0)));
		    }

			@Override
			protected OverlayItem createItem(int i) {
				return(items.get(i));
			}
			
			@Override
			public void draw(Canvas canvas, MapView mapView,
											boolean shadow) {
				super.draw(canvas, mapView, shadow);
				
				boundCenterBottom(marker);
			}
	 		
			@Override
			protected boolean onTap(int i) {
				Toast.makeText(GoogleMaps.this,
									items.get(i).getSnippet(),
									Toast.LENGTH_SHORT).show();
				return(true);
			}
			
			
			
			@Override
			public int size() {
				return(items.size());
			}
		}
	
	public boolean onDoubleTap(MotionEvent e) {
	    GeoPoint p = map.getProjection().fromPixels((int)e.getX(), (int)e.getY());
	    
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    dialog.setTitle("Double Tap");
	    dialog.setMessage("Location: " + p.getLatitudeE6() + ", " + p.getLongitudeE6());
	    dialog.show();
	    
	    return true;
	  }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mapController = map.getController();
		mapController.zoomIn();
		return true;
	}
	
	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}