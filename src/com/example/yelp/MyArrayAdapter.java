package com.example.yelp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<YelpResult>{
	private final Context context;
	private final ArrayList<YelpResult> yp;
	
	public MyArrayAdapter(Context context, ArrayList<YelpResult> yp) {
		super(context, R.layout.row_layout, yp);
		this.context = context;
		this.yp = yp;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		
		TextView business_name = (TextView)rowView.findViewById(R.id.business_name_1);
		TextView business_address = (TextView)rowView.findViewById(R.id.business_address_1);
		TextView business_distance = (TextView)rowView.findViewById(R.id.business_distance_1);
		ImageView business_image = (ImageView)rowView.findViewById(R.id.business_image_1);
		ImageView business_rating = (ImageView)rowView.findViewById(R.id.business_rating_1);
		
//		business_name.setText(Integer.toString(position));
		business_name.setText(yp.get(position).get_name());
		business_address.setText(yp.get(position).get_address());
		business_distance.setText(yp.get(position).get_distance());
		business_image.setImageBitmap(yp.get(position).get_image());
		business_rating.setImageBitmap(yp.get(position).get_rating());

		return rowView;
	}
}

