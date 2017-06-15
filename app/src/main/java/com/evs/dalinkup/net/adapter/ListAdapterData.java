package com.evs.dalinkup.net.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.evs.dalinkup.net.DetailsPage;
import com.evs.dalinkup.net.Global;
import com.evs.dalinkup.net.R;
import com.evs.dalinkup.net.imageProcess.ImageLoader;
import com.evs.dalinkup.net.jsonservice.CustomRequest;
import com.evs.dalinkup.net.utils.BlurBuilder;
import com.evs.dalinkup.net.utils.ScalingUtilities;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

public class ListAdapterData implements ListAdapter {

    ArrayList<String> t_listid, t_listname, t_listdescription, t_listadmission,
            t_listpath, t_listtype, t_listhosted, t_listdate, t_listlocation,
            t_listaddress, t_listcity, t_liststate, t_listcountry,
            t_listlatitude, t_listlongitude;
    ArrayList<String> t_listfree,t_listpurchase;
    Activity t_Context;
    int size;
    ImageView imgView, iv_blur;

    Bitmap bitmap;
    String Selecteditem = "";

    public ListAdapterData(ArrayList<String> listid, ArrayList<String> listname, ArrayList<String> listdescription, ArrayList<String> listadmission, ArrayList<String> listfree,ArrayList<String> listpurchase, ArrayList<String> listpath,
                           ArrayList<String> listtype, ArrayList<String> listhosted, ArrayList<String> listdate, ArrayList<String> listlocation, ArrayList<String> listaddress, ArrayList<String> listcity,
                           ArrayList<String> liststate, ArrayList<String> listcountry, ArrayList<String> listlatitude, ArrayList<String> listlongitude, String selecteditem, Activity applicationContext) {
        // TODO Auto-generated constructor stub

        t_Context = applicationContext;
        t_listid = listid;
        t_listname = listname;
        t_listdescription = listdescription;
        t_listadmission = listadmission;
        t_listpath = listpath;
        t_listtype = listtype;
        t_listhosted = listhosted;
        t_listdate = listdate;
        t_listlocation = listlocation;
        t_listaddress = listaddress;
        t_listfree = listfree;
        t_listpurchase = listpurchase;


        t_listcity = listcity;
        t_liststate = liststate;
        t_listcountry = listcountry;
        t_listlatitude = listlatitude;
        t_listlongitude = listlongitude;
        size = t_listid.size();
        Selecteditem = selecteditem;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) t_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.listitems, parent, false);
        imgView = (ImageView) v.findViewById(R.id.imgView);
        iv_blur = (ImageView) v.findViewById(R.id.iv_blur);

        if (t_listpath.get(position).length() > 0) {
            String ImagePath = Global.imgUrl + t_listpath.get(position);
            ImageLoader imageLoader;
            imageLoader = new ImageLoader(t_Context);
//			imageLoader.DisplayImage(ImagePath, imgView);
//			imageLoader.DisplayImage(ImagePath, iv_blur);
            Picasso.with(t_Context)
                    .load(ImagePath)
                    .fit()
                    .centerCrop()
                    .into(iv_blur);
            Picasso.with(t_Context)
                    .load(ImagePath)
                    .fit()
                    .centerInside()
                    .into(imgView);
//			setBackgroundImage(ImagePath, iv_blur) ;

        }


        //	imgView.setFocusable(false);

        imgView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

//                if (t_listfree.get(position).isEmpty() && t_listpurchase.get(position).isEmpty())   {
//                    Intent intent = new Intent(t_Context, DetailsPage.class);
//                    intent.putExtra("id", t_listid.get(position));
//                    intent.putExtra("name", t_listname.get(position));
//                    intent.putExtra("description", t_listdescription.get(position));
//                    intent.putExtra("admission", t_listadmission.get(position));
//                    intent.putExtra("path", t_listpath.get(position));
//                    intent.putExtra("type", t_listtype.get(position));
//                    intent.putExtra("hosted", t_listhosted.get(position));
//                    intent.putExtra("date", t_listdate.get(position));
//                    intent.putExtra("location", t_listlocation.get(position));
//                    intent.putExtra("address", t_listaddress.get(position));
//                    intent.putExtra("city", t_listcity.get(position));
//                    intent.putExtra("state", t_liststate.get(position));
//                    intent.putExtra("country", t_listcountry.get(position));
//                    intent.putExtra("latitude", t_listlatitude.get(position));
//                    intent.putExtra("longitude", t_listlongitude.get(position));
//                    intent.putExtra("Selecteditem", Selecteditem);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    t_Context.startActivity(intent);
//                    t_Context.finish();
//                } else  if (t_listfree.get(position).isEmpty())  {
//                    Intent intent = new Intent(t_Context, DetailsPage.class);
//                    intent.putExtra("id", t_listid.get(position));
//                    intent.putExtra("name", t_listname.get(position));
//                    intent.putExtra("description", t_listdescription.get(position));
//                    intent.putExtra("admission", t_listadmission.get(position));
//                    intent.putExtra("path", t_listpath.get(position));
//                    intent.putExtra("type", t_listtype.get(position));
//                    intent.putExtra("hosted", t_listhosted.get(position));
//                    intent.putExtra("date", t_listdate.get(position));
//                    intent.putExtra("location", t_listlocation.get(position));
//                    intent.putExtra("address", t_listaddress.get(position));
//                    intent.putExtra("purchase", t_listpurchase.get(position));
//                    intent.putExtra("city", t_listcity.get(position));
//                    intent.putExtra("state", t_liststate.get(position));
//                    intent.putExtra("country", t_listcountry.get(position));
//                    intent.putExtra("latitude", t_listlatitude.get(position));
//                    intent.putExtra("longitude", t_listlongitude.get(position));
//                    intent.putExtra("Selecteditem", Selecteditem);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    t_Context.startActivity(intent);
//                    t_Context.finish();
//                }else  if (t_listpurchase.get(position).isEmpty())  {
//                    Intent intent = new Intent(t_Context, DetailsPage.class);
//                    intent.putExtra("id", t_listid.get(position));
//                    intent.putExtra("name", t_listname.get(position));
//                    intent.putExtra("description", t_listdescription.get(position));
//                    intent.putExtra("admission", t_listadmission.get(position));
//                    intent.putExtra("path", t_listpath.get(position));
//                    intent.putExtra("type", t_listtype.get(position));
//                    intent.putExtra("hosted", t_listhosted.get(position));
//                    intent.putExtra("date", t_listdate.get(position));
//                    intent.putExtra("location", t_listlocation.get(position));
//                    intent.putExtra("address", t_listaddress.get(position));
//                    intent.putExtra("free", t_listfree.get(position));
//                    intent.putExtra("city", t_listcity.get(position));
//                    intent.putExtra("state", t_liststate.get(position));
//                    intent.putExtra("country", t_listcountry.get(position));
//                    intent.putExtra("latitude", t_listlatitude.get(position));
//                    intent.putExtra("longitude", t_listlongitude.get(position));
//                    intent.putExtra("Selecteditem", Selecteditem);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    t_Context.startActivity(intent);
//                    t_Context.finish();
//                }else   {
                System.out.println("purchase :- "+t_listpurchase.get(position));
                    Intent intent = new Intent(t_Context, DetailsPage.class);
                    intent.putExtra("id", t_listid.get(position));
                    intent.putExtra("name", t_listname.get(position));
                    intent.putExtra("description", t_listdescription.get(position));
                    intent.putExtra("admission", t_listadmission.get(position));
                    intent.putExtra("path", t_listpath.get(position));
                    intent.putExtra("type", t_listtype.get(position));
                    intent.putExtra("hosted", t_listhosted.get(position));
                    intent.putExtra("date", t_listdate.get(position));
                    intent.putExtra("location", t_listlocation.get(position));
                    intent.putExtra("address", t_listaddress.get(position));
                    intent.putExtra("free", t_listfree.get(position));
                    intent.putExtra("purchase", t_listpurchase.get(position));
                    intent.putExtra("city", t_listcity.get(position));
                    intent.putExtra("state", t_liststate.get(position));
                    intent.putExtra("country", t_listcountry.get(position));
                    intent.putExtra("latitude", t_listlatitude.get(position));
                    intent.putExtra("longitude", t_listlongitude.get(position));
                    intent.putExtra("Selecteditem", Selecteditem);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    t_Context.startActivity(intent);
                    t_Context.finish();


//                }


            }
        });


        if (convertView != null) {

            convertView.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        convertView.setBackgroundColor(Color.RED);
                        return true;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        convertView.setBackgroundColor(Color.BLUE);
                        return true;
                    }


                    return false;
                }

            });
        }


        return v;
    }

    private void setBackgroundImage(String imageUrl, final ImageView imageView) {
        if (!TextUtils.isEmpty(imageUrl)) {
            com.nostra13.universalimageloader.core.ImageLoader imageLoader1 = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            imageLoader1.init(ImageLoaderConfiguration.createDefault(t_Context));
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .build();
//			DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//					.cacheOnDisc(true).resetViewBeforeLoading(true)
//					.showImageForEmptyUri(R.drawable.no_image)
//					.showImageOnFail(R.drawable.no_image)
//					.showImageOnLoading(R.drawable.no_image).build();


            // imageLoader.displayImage(imageUrl, imageView, options);
          /*
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisk(true).resetViewBeforeLoading(true).build();*/
            // imageLoader.displayImage(imgUrl + "", imageView, options);
            imageLoader1.displayImage(imageUrl, imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    if (loadedImage != null) {
                        imageView.setImageBitmap(loadedImage);
                        setBlurImageOnBackground(loadedImage);
                    }
                }
            });
        }
    }

    private void setBlurImageOnBackground(final Bitmap loadedImage) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                /*Drawable d = getResources().getDrawable(R.drawable.nature);
                if (d != null) {
                    Bitmap bitmap = ((BitmapDrawable) d).getBitmap();*/
                int[] wh = CustomRequest.getDeviceScreenWidth(t_Context);
                Bitmap cropBitmap = ScalingUtilities.createScaledBitmap(loadedImage, wh[0], wh[1], ScalingUtilities.ScalingLogic.CROP);
                if (cropBitmap != null) {
                    final Bitmap blurredBitmap = BlurBuilder.blur(t_Context, cropBitmap);
                    t_Context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_blur.setBackgroundDrawable(new BitmapDrawable(t_Context.getResources(), blurredBitmap));
                        }
                    });
                }

                //    }

            }
        }).start();


    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub

        return size;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return false;
    }

}
