package com.evs.dalinkup.net;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.evs.dalinkup.net.imageProcess.ImageLoader;
import com.squareup.picasso.Picasso;

public class SingleViewActivity extends Activity {
	 
	
	
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.singleimageviewpager);
			ImageLoader imageLoader;
			imageLoader = new ImageLoader(SingleViewActivity.this);
			 
			Intent p = getIntent();
	 
			ImageGridAdapter imageAdapter = new ImageGridAdapter(this);
			List<ImageView> images = new ArrayList<ImageView>();
			List<TextView> names = new ArrayList<TextView>();
			
			// Retrieve all the images
			for (int i = 0; i < imageAdapter.getCount(); i++) {
				
				TextView image_des=new TextView(this);
				ImageView imageView = new ImageView(this);
				
				imageLoader.DisplayImage(DetailsPage.image_list.get(i), imageView);
				//Picasso.with(SingleViewActivity.this).load(DetailsPage.image_list.get(i)).into(imageView);
		        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				
		        
		        image_des.setText(DetailsPage.image_name.get(i));
		        images.add(imageView);
				names.add(image_des);
				
				//image_des.setText();
			}
	 
			// Set the images into ViewPager
			ImagePagerAdapter pageradapter = new ImagePagerAdapter(images,names);
			
			ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
			
			
			viewpager.setAdapter(pageradapter);
			 
			
			// Show images following the position
			viewpager.setCurrentItem(p.getExtras().getInt("id"));
			
	        
	   }
	}