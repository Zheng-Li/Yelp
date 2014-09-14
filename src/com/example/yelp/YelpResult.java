package com.example.yelp;

import android.graphics.Bitmap;

public class YelpResult {
	private String business_name;
	private String business_address;
	private String business_distance;
	private String business_url;
	private Bitmap business_image;
	private Bitmap business_rating;
	
	public YelpResult() {} 
	
	public YelpResult(String name, String address, String distance, String url) {
		business_name = name;
		business_address = address;
		business_distance = distance;
		business_url = url;
	}
	
	public YelpResult(String name, String address, String distance, String url, Bitmap image, Bitmap rating) {
		business_name = name;
		business_address = address;
		business_distance = distance;
		business_url = url;
		business_image = image;
		business_rating = rating;
	}
	
	public void set_name(String s) {
		this.business_name = s;
	}
	
	public String get_name() {
		return this.business_name;
	}
	
	public void set_address(String s) {
		this.business_address = s;
	}
	
	public String get_address() {
		return this.business_address;
	}
	
	public void set_distance(String s) {
		this.business_distance = s;
	}
	
	public String get_distance() {
		return this.business_distance;
	}
	
	public void set_url(String s) {
		this.business_url = s;
	}
	
	public String get_url() {
		return this.business_url;
	}
	
	public void set_image(Bitmap i) {
		this.business_image = i;
	}
	
	public Bitmap get_image() {
		return this.business_image;
	}
	
	public void set_rating(Bitmap r) {
		this.business_rating = r;
	}
	
	public Bitmap get_rating() {
		return this.business_rating;
	}
	
}
