package com.evs.dalinkup.net;

import java.io.InputStream;
import java.net.URL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evs.dalinkup.net.imageProcess.ImageLoader;


public class ImageGridAdapter extends BaseAdapter {

	Context context;
	private static LayoutInflater inflater ;
	int size;
	ProgressDialog pDialog;
	Bitmap bitmap;
	ImageView img;
	TextView image_des;
	String imageUrl;

	public ImageGridAdapter(Activity mainActivity){
		context = mainActivity;		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		size = DetailsPage.image_list.size();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return DetailsPage.image_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.imageadapter, parent, false);

		
		img = (ImageView) v.findViewById(R.id.imageView1);
		image_des=(TextView)v.findViewById(R.id.image_des);
		
			if (convertView == null) {
			 	img.setPadding(8, 8, 8, 8);
			} else {
		 	}
		
		imageUrl =  DetailsPage.image_list.get(position);
		
		
		
		//Picasso.with(context).load(DetailsPage.image_list.get(position)).networkPolicy(NetworkPolicy.OFFLINE).resize(100, 100).centerCrop().into(img);
		ImageLoader imageLoader;
		imageLoader = new ImageLoader(context);
		imageLoader.DisplayImage(imageUrl, img);
		
		return v;
	}

	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		protected Bitmap doInBackground(String... args) {
			try {
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
				System.out.println("======" + bitmap);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap image) {

			if (image != null) {

				img.setImageBitmap(image);
				

			} else {
				

			}
		}
	}

}