package com.example.yelp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	ArrayList<String> urls = new ArrayList<String>() ;
	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView ls = (ListView)findViewById(R.id.list);
		
		GPSInfoProvider gps = GPSInfoProvider.getInstance(context);
		String location = gps.getLocation();
		int x = location.indexOf("latitude ");
		int y = location.indexOf(" longitude ");
		String latitude = location.substring(x + 9, y);
		String longitude = location.substring(y + 11);
		
		String catagory = "Grill";
		double lat = Double.parseDouble(latitude);
		double lon = Double.parseDouble(longitude);	
		
		
		//Run Yelp search on the background
		try {
			 ArrayList<YelpResult> results = new YelpSearch().
					 execute(catagory, Double.toString(lat), Double.toString(lon)).get();
			 urls = getUrls(results);
			 MyArrayAdapter adapter = new MyArrayAdapter(this, results);
			 ls.setAdapter(adapter);
			 
		} catch (InterruptedException e) {
			Log.d("Error: ", "Interruption");
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.d("Error: ", "Execution");
			e.printStackTrace();
		}
		
		ls.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				    int position, long id) {
				sentOutRequest(position); 
			}
		});	
	}
	
	private void sentOutRequest(int p) {
		
		//Build Intent
		Uri webpage = Uri.parse(urls.get(p));
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
		
		//Verify it resolves
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, 0);
		boolean isIntentSafe = activities.size() > 0;
		
		//Start an activity when it's safe
		if (isIntentSafe) {
			String title = "Choose whatever you want";
			// Create and start the chooser
			Intent chooser = Intent.createChooser(webIntent, title);
			startActivity(chooser);
		}
	}
	
	private ArrayList<String> getUrls(ArrayList<YelpResult> yp) {
		ArrayList<String> urls = new ArrayList<String>();
		for(int i=0; i<yp.size(); i++) {
			urls.add(yp.get(i).get_url());
		}
		return urls;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class YelpSearch extends AsyncTask<String, Void, ArrayList<YelpResult>> {

		@Override
		protected ArrayList<YelpResult> doInBackground(String... str) {
			String keyword = str[0];
			double latitude = Double.valueOf(str[1]);
			double longitude = Double.valueOf(str[2]);
			String result = search_Class(keyword, latitude, longitude);
			ArrayList<YelpResult> yelpResult = new ArrayList<YelpResult>();
			
			YelpParser yParser = new YelpParser();
		    yParser.setResponse(result);
		    
		    try {
		        yParser.parseBusiness();
		    } catch (JSONException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        //Do whatever you want with the error, like throw a Toast error report
		    }
		    
		    for(int x = 0; x < yParser.getBundleKeysSize(); x++) {
				YelpResult yp = new YelpResult();
	        	yp.set_name(yParser.getBusinessName(x));		    
	        	yp.set_address(yParser.getBusinessAddress(x));
	        	yp.set_distance(yParser.getDistance(x));
	        	yp.set_url(yParser.getBusinessMobileURL(x));
		    
	        	String bImage = yParser.getImage(x);
	        	Bitmap b_image = load_image(bImage);
	        	yp.set_image(b_image);
		    
	        	String bRating = yParser.getRating(x);
	        	Bitmap b_rating = load_image(bRating);
	        	yp.set_rating(b_rating);
		    
	        	yelpResult.add(yp);
		    }
			
			return yelpResult;
		}

		protected String search_Class(String s_word, double lat, double lon)
		{
		    API_Static_Stuff api_keys = new API_Static_Stuff();
		     
		    Yelp yelp = new Yelp(api_keys.getYelpConsumerKey(), api_keys.getYelpConsumerSecret(), 
		            api_keys.getYelpToken(), api_keys.getYelpTokenSecret());
		    String response = yelp.search(s_word, lat, lon);
		 
		    YelpParser yParser = new YelpParser();
		    yParser.setResponse(response);
		    try {
		        yParser.parseBusiness();
		    } catch (JSONException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        //Do whatever you want with the error, like throw a Toast error report
		    }
		    return yParser.getResponse();
		}
		
		protected Bitmap load_image(String url) {
			Bitmap bp = null;
			
			try {
				bp = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
				return bp;
			} catch (MalformedURLException e) {
		    	  e.printStackTrace();
		    } catch (IOException e) {
		    	  e.printStackTrace();
		   	}
			
			return bp;
		}
	}
}
