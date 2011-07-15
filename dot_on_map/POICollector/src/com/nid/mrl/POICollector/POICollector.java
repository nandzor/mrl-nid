package com.nid.mrl.POICollector;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import static com.nid.mrl.POICollector.PointManager.*; 
import com.nid.mrl.POICollector.location.Locations;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;
import java.util.List;

public class POICollector extends MapActivity{
	private MapView map=null;
	private MyLocationOverlay me=null;
	private ArrayList<Locations> location;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		
		map=(MapView)findViewById(R.id.map);
		map.setSatellite(false);
		map.setBuiltInZoomControls(true);
		getOverlaysitems();
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
	
	private PointManager getPointManagerApplication() {
		// TODO Auto-generated method stub
		PointManager pma = (PointManager) getApplication();
		return pma;
	}

	
	
	public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, Menu.FIRST,   Menu.NONE, "Add").setIcon(R.drawable.add);
    	menu.add(0, Menu.FIRST+1, Menu.NONE, "add current location").setIcon(R.drawable.gps);
    	return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1){
			Intent intent1 = new Intent(POICollector.this, AddPOIManually.class);
	        startActivity(intent1);
		}else if(item.getItemId() == 2){
			Intent intent2 = new Intent(POICollector.this, AddPOIUsingGPS.class);
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
	    				
			public SitesOverlay(Drawable marker, GeoPoint g, String n) {
				super(marker);
				this.marker=marker;
			 items.add(new OverlayItem(g, "200901199", n));
				populate();
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
				Toast.makeText(POICollector.this,
									items.get(i).getSnippet(),
									Toast.LENGTH_SHORT).show();
				return(true);
			}
			
			
			
			@Override
			public int size() {
				return(items.size());
			}
		}

}