package com.evs.dalinkup.net;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagePagerAdapter extends PagerAdapter{
	
	 private List<ImageView> images;
	 private List<TextView> names;
	public int[] mThumbIds;
	 
	    public ImagePagerAdapter(List<ImageView> images, List<TextView> names) {
	        this.images = images;
	        this.names=names;
	    }
	 
	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	        ImageView imageView = images.get(position);
	        TextView textview=names.get(position);
	        container.addView(imageView);
	        container.addView(textview);
	        return imageView;
	    }
	 
	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	        container.removeView(images.get(position));
	        container.removeView(names.get(position));
	    }
	    
	    @Override
	    public int getCount() {
	        return images.size();
	    }
	 
	    @Override
	    public boolean isViewFromObject(View view, Object o) {
	        return view == o;
	    }
	}