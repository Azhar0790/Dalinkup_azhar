package com.evs.dalinkup.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.evs.dalinkup.net.imageProcess.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class FullScreenImageAdapter extends BaseAdapter {
	private Activity act;
	String[] strings;
	public static ImageLoader imageLoader;
	AQuery aQuery;
	ImageView imgcirculer;

	public FullScreenImageAdapter(Activity acti, String[] strings) {
		act = acti;
		this.strings = strings;
		imageLoader = new ImageLoader(act);
	}

	public int getCount() {
		return strings.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	class ViewHolder {
		protected TextView tviservice;
		protected Button share1;

	}
	public View getView(final int position, View convertView,final ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.image_display_adapter, parent, false);

		final ViewHolder holder = new ViewHolder();
		imgcirculer = (ImageView) v.findViewById(R.id.imageView1);
		holder.share1= (Button) v.findViewById(R.id.share1);
		holder.tviservice = (TextView) v.findViewById(R.id.image_des);

		imageLoader.DisplayImage(strings[position], imgcirculer);
		holder.tviservice.setText(DetailsPage.image_name.get(position));


		holder.share1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {



					Drawable mDrawable = imgcirculer.getDrawable();
					Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
					String path = MediaStore.Images.Media.insertImage(act.getContentResolver(), mBitmap, "Image Description", null);
					//String path = MediaStore.Images.Media.insertImage(act.getContentResolver(), mBitmap, "", null);
					//String path = "https://dalinkup.net/WebContent/WebImage_Folder/Gallery_pics/1466319934temp_photo.jpg";
					Uri uri = Uri.parse(path);
					Intent shareIntent = new Intent();
					shareIntent.setAction(Intent.ACTION_SEND);
					shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
					shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					shareIntent.setType("image/*");
					act.startActivity(Intent.createChooser(shareIntent, "Share images to.."));
			/*Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("image/*");
			intent.putExtra(Intent.EXTRA_STREAM, uri);
			act.startActivity(Intent.createChooser(intent, "Share Image"));*/



				} catch (Exception e) {

					e.printStackTrace();
				}


			}
		});
		
		return v;
	}




}
