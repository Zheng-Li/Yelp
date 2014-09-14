package com.example.yelp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSInfoProvider {
	LocationManager manager;
	private static GPSInfoProvider mGPSInfoProvider;
	private static Context context;    
	private static MyLoactionListener listener;
	
	private GPSInfoProvider(){};
	
	public static synchronized GPSInfoProvider getInstance(Context context){
		if(mGPSInfoProvider==null){
			synchronized (GPSInfoProvider.class) {
				if(mGPSInfoProvider == null){
					mGPSInfoProvider = new GPSInfoProvider();
					GPSInfoProvider.context = context;
				}
			}
		}
		return mGPSInfoProvider;
	}
	
	
	public String getLocation(){
		manager =(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		String provider = getProvider(manager);
		manager.requestLocationUpdates(provider,60000, 50, getListener());
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String location = sp.getString("location", "");
		return location;
	}

	
	public void stopGPSListener(){
		manager.removeUpdates(getListener());
	}
	
	private synchronized MyLoactionListener getListener(){
		if(listener==null){
			synchronized (GPSInfoProvider.class) {
				if(listener == null){
					listener = new MyLoactionListener();
				}
			}
			
		}
		return listener;
	}
	
	private class MyLoactionListener implements LocationListener{
		public void onLocationChanged(Location location) {
			String latitude ="latitude "+ location.getLatitude();
			String longitude = "longitude "+ location.getLongitude();
			SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("location", latitude+ " " + longitude);
			editor.commit(); 
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}

		public void onProviderEnabled(String provider) {

		}

		public void onProviderDisabled(String provider) {
			
		}
		
	}
	private String getProvider(LocationManager manager){
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
		criteria.setSpeedRequired(true);
		criteria.setCostAllowed(true);
		return  manager.getBestProvider(criteria, true);
	}
}