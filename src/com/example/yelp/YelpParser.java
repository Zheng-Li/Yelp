package com.example.yelp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

/**
 * This class is used to parse Yelp's response
 * 
 * @author ograycoding.wordpress.com
 *
 */
 
public class YelpParser {
     
    private String yelp_response;
    private Bundle yelp_bundle = new Bundle();
    private ArrayList<String> keys = new ArrayList<String>();
     
    /**
     * Sets Yelp's response for this class
     * @param response
     */
    public void setResponse(String response){yelp_response = response;}
     
    /**
     * Returns the set Yelp response
     * @return string yelp_response
     */
    public String getResponse(){return yelp_response;}
     
    /**
     * Parse's yelp's response for the business name; mobile url; and ratings url.
     * Mobile url and ratings url is separated by " ,,, "
     * @sets yelp_bundle(key = business name)
     * @sets keys arraylist with business name
     * @throws JSONException
     */
    public void parseBusiness() throws JSONException{
        JSONObject o1 = new JSONObject(yelp_response);
        JSONArray businesses = o1.getJSONArray("businesses");
        String tmpString;
        for (int i = 0; businesses.length() > i; i++){
        	
        	//Distance from the search center
        	Double distance = businesses.getJSONObject(i).getDouble("distance");
        	distance = distance / 1609.34;
        	String mDistance = String.format("%.1f miles", distance);
        	
        	//Business mobile url
        	String b_url = businesses.getJSONObject(i).get("mobile_url").toString();
        	
        	//Business rating url
        	String b_rating = businesses.getJSONObject(i).get("rating_img_url").toString();
        	
        	//Business image url
        	String b_image = businesses.getJSONObject(i).get("image_url").toString();
        	
        	//Business address location
        	JSONObject b_location = businesses.getJSONObject(i).getJSONObject("location");
        	String b_address = b_location.getJSONArray("address").getString(0) + "\n" +
        			b_location.getString("city") +", "+ b_location.getString("state_code") + " " +
        			b_location.getString("postal_code");

        	
            tmpString = b_url + " ,,, " + b_rating + " ... " + b_image + " *** "
                    + mDistance + " ''' " + b_address;
            keys.add(businesses.getJSONObject(i).get("name").toString());
            yelp_bundle.putString(keys.get(i), tmpString);
            
        }
        
    }
     
    /**
     * Gets the business name, assuming you supply the bundle, key, and int
     * In reality, all it does is pull myKey.get(i), but I added the myBundle
     * param to try and force all information to be there.
     * @param myBundle
     * @param myKey
     * @param i
     * @return myKey.get(i)
     */
    public String getBusinessName(Bundle myBundle, ArrayList<String> myKey, int i){return myKey.get(i);}
     
    /**
     * This gets the business's name, which is stored in the ArrayList keys, using
     * this class's stored results.
     * @param i
     * @return
     */
    public String getBusinessName(int i){return keys.get(i);}
     
    /**
     * This returns the business's mobile URL from myBundle using the key provided at int i
     * @param myBundle
     * @param myKey
     * @param i
     * @return mobileURL
     */
    public String getBusinessMobileURL(Bundle myBundle, ArrayList<String> myKey, int i){
        String tmp = myBundle.getString(myKey.get(i));
        int x = tmp.indexOf(" ,,, ");
        String mobileURL = tmp.substring(0, x);
        return mobileURL;
    }
     
    /**
     * This returns the mobile URL using this class's internally stored variables at int i.
     * For ease of use I suggest using this method.
     * 
     * @param i
     * @return mobileURL
     */
    public String getBusinessMobileURL(int i){
        String tmp = yelp_bundle.getString(keys.get(i));
        int x = tmp.indexOf(" ,,, ");
        String mobileURL = tmp.substring(0, x);
        return mobileURL;
    }
     
    /**
     * This will return the rating URL from the user-supplied Bundle, key, and int i
     * @param myBundle
     * @param myKey
     * @param i
     * @return ratingURL
     */
    public String getRating (Bundle myBundle, ArrayList<String> myKey, int i){
        String tmp = myBundle.getString(myKey.get(i));
        int x = tmp.indexOf(" ,,, ") + 5;
        int y = tmp.indexOf(" ... ");
        String ratingURL = tmp.substring(x, y);
        return ratingURL;
    }
     
    /**
     * This will return the rating URL using this class's internal variables.
     * I recommend using this method. Int i is for keys.get(i).
     * @param i
     * @return ratingURL
     */
    public String getRating (int i){
        String tmp = yelp_bundle.getString(keys.get(i));
        int x = tmp.indexOf(" ,,, ") + 5;
        int y = tmp.indexOf(" ... ");
        String ratingURL = tmp.substring(x, y);
        return ratingURL;
    }
    
    //Getting the image of each business
    public String getImage (Bundle myBundle, ArrayList<String> myKey, int i) {
    	String tmp = yelp_bundle.getString(keys.get(i));
    	int x = tmp.indexOf(" ... ") + 5;
    	int y = tmp.indexOf(" *** ");
    	String snappetURL = tmp.substring(x, y);
    	return snappetURL;
    }
    
    public String getImage (int i) {
    	String tmp = yelp_bundle.getString(keys.get(i));
    	int x = tmp.indexOf(" ... ") + 5;
    	int y = tmp.indexOf(" *** ");
    	String snappetURL = tmp.substring(x, y);
    	return snappetURL;
    }
    
    //Getting the distance of each business
    public String getDistance(Bundle myBundle, ArrayList<String> myKey, int i) {
    	String tmp = yelp_bundle.getString(keys.get(i));
    	int x = tmp.indexOf(" *** ") + 5;
    	int y = tmp.indexOf(" ''' ");
    	String dirDistance = tmp.substring(x, y);
    	return dirDistance;
    }

    public String getDistance(int i) {
    	String tmp = yelp_bundle.getString(keys.get(i));
    	int x = tmp.indexOf(" *** ") + 5;
    	int y = tmp.indexOf(" ''' ");
    	String dirDistance = tmp.substring(x, y);
    	return dirDistance;    	
    }
    
    //Getting the location of each business
    public String getBusinessAddress(int i) {
    	String tmp = yelp_bundle.getString(keys.get(i));
    	int x = tmp.indexOf(" ''' ") + 5;
    	String bAddress = tmp.substring(x);
    	return bAddress;	
    }
    
    public String getBusinessAddress(Bundle myBundle, ArrayList<String> myKey, int i) {
    	String tmp = yelp_bundle.getString(keys.get(i));
    	int x = tmp.indexOf(" ''' ") + 5;
    	String bAddress = tmp.substring(x);
    	return bAddress;	
    }
    
    /**
     * Returns the bundle, key is the business name.
     * @return yelp_bundle
     */
    public Bundle getYelpBundle(){return yelp_bundle;}
     
    /**
     * Returns the keys (business names) for the yelpBundle.
     * @return keys (business names)
     */
    public ArrayList<String> getBundleKeys(){return keys;}
     
    /**
     * This will return the keys.size(), and is designed to be used with loops
     * @return keys.size()
     */
    public int getBundleKeysSize(){int size = keys.size(); return size; }
}