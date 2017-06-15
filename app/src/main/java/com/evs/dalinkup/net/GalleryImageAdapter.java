package com.evs.dalinkup.net;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.evs.dalinkup.net.imageProcess.ImageLoader;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class GalleryImageAdapter extends BaseAdapter {
	private Activity mContext;

	public GalleryImageAdapter(Activity context) {
		mContext = context;
	}

	public int getCount() {

		return DetailsPage.image_list.size();

	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	// Override this method according to your need
	public View getView(final int index, View view, ViewGroup viewGroup) {
		// TODO Auto-generated method stub

		ImageView i = new ImageView(mContext);
		ImageLoader imageLoader;
		imageLoader = new ImageLoader(mContext);
//for (index=0;index<=DetailsPage.image_list.size();index++)

		imageLoader.DisplayImage(DetailsPage.image_list.get(index),i);



		/*if (DetailsPage.image_list.get(index).length() > 1) {
			Picasso.with(mContext).load(DetailsPage.image_list.get(index)).networkPolicy(NetworkPolicy.OFFLINE).resize(100, 100).centerCrop().into(i);
		} else {
			//i.setImageDrawable(mContext.getResources().getDrawable(R.drawable.c1));
		}*/
		i.setLayoutParams(new Gallery.LayoutParams(200, 200));
		i.setScaleType(ScaleType.FIT_XY);

//		i.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				final Dialog saloon = new Dialog(mContext);
//				saloon.setContentView(R.layout.dilaog_gallery_image);
//				ImageView img=(ImageView)saloon.findViewById(R.id.img);
//				Picasso.with(mContext).load(DetailsPage.image_list.get(index)).into(img);
//			}
//		});


		return i;

	}

}