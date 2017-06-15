package com.evs.dalinkup.net;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Gallery;

public class ImageDisplay extends Activity{
	String image_url;
	private Gallery gallery;
	ArrayList<String>image_list=new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_display);
		
		Intent in=getIntent();
		image_url=in.getExtras().getString("image_url");
		image_list = in.getStringArrayListExtra("all_image_url");
	
		gallery = (Gallery) findViewById(R.id.Gallery);		
		gallery.setAdapter(new FullScreenImageAdapter(ImageDisplay.this,image_list.toArray(new String[image_list.size()])));
		gallery.setSelection(Integer.parseInt(in.getExtras().getString("position")));
	}
	
	/*public class AddImgAdp extends BaseAdapter {
		int GalItemBg;
		private Context cont;
		String[] strings;

		public AddImgAdp(Context c, String[] strings) {
			cont = c;
			this.strings = strings;
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

		public View getView(int position, View convertView, ViewGroup parent) {
			
			ImageView img;
			TextView image_des;
			
			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.image_display_adapter, parent, false);
			img = (ImageView) v.findViewById(R.id.imageView1);
			image_des=(TextView)v.findViewById(R.id.image_des);
			
			image_des.setText(DetailsPage.image_name.get(position));
			
			//ImageView imgView = new ImageView(cont);

			img.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			imageLoader.DisplayImage(strings[position], img);
			return v;
		}
	}*/
	
	

}
