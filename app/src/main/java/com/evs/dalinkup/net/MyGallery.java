package com.evs.dalinkup.net;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class MyGallery extends Activity {
	Button btnBackend;
	GridView gridview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.mygallery);

		//Toast.makeText(getBaseContext(),getIntent().getStringExtra("event_id"), Toast.LENGTH_LONG).show();
		
		if (DetailsPage.image_list.size()>0) {
			
		}else{
			Global.GlobalDialog(MyGallery.this, "No Gallery images found.");
		}

		btnBackend = (Button) findViewById(R.id.btnBackend);
		gridview = (GridView) findViewById(R.id.gridview);
		btnBackend.setText("<< BACK");

		btnBackend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		gridview.setAdapter(new ImageGridAdapter(MyGallery.this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
				// Send intent to SingleViewActivity
				/*Intent i = new Intent(getApplicationContext(),SingleViewActivity.class);
				i.putExtra("id", position);
				startActivity(i);*/
				
				Intent innn=new Intent(getBaseContext(), ImageDisplay.class);
				innn.putExtra("image_url", DetailsPage.image_list.get(position).toString());
				innn.putStringArrayListExtra("all_image_url", DetailsPage.image_list);
				innn.putExtra("position",""+position);
				startActivity(innn);
			}
		});

	}

	

}
