package com.evs.dalinkup.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Global {
		public static String URLglobal="https://dalinkup.net/Xml_Connect/EventListing.xml";
	public static String imgUrl="https://dalinkup.net/";
	public static String URLglobalPastEvents="https://dalinkup.net/Xml_Connect/PastEventListing.xml";
	public static String Dis="https://dalinkup.net/API/disclosureinfo.php";
	public static String URLShare="https://dalinkup.net/?flyer=";
	public static String URL_ticket="https://dalinkup.net/Webservice.php/";

	public static String URL="https://dalinkup.net/API/webservices.php";
	public static String CHANGEIMAGE="https://dalinkup.net/Webservice.php/";
	public static String TOKENREGISTRATION="https://dalinkup.net/Webservice.php/";
	public static String PRODUCTSERVICELIST="https://dalinkup.net/Webservice.php";
	public static String PRODUCTIMGSERVICELIST="https://dalinkup.net/WebContent/WebImage_Folder/service_images/";

	public static void showDialog(final Activity ac) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(ac);
        alertbox.setTitle(ac.getResources().getString(R.string.app_name));
        alertbox.setMessage("No Internet Connection!");
        alertbox.setPositiveButton(ac.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        ac.finish();
                    }
                });
        alertbox.show();
    }



	 public static void GlobalDialog(final Activity ac, String msg) {
	        AlertDialog.Builder alertbox = new AlertDialog.Builder(ac);
	        alertbox.setTitle(ac.getResources().getString(R.string.app_name));
	        alertbox.setMessage(msg);
	        alertbox.setPositiveButton(ac.getResources().getString(R.string.ok),
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface arg0, int arg1) {
	                    	ac.finish();

	                    }
	                });
	        alertbox.show();

	    }
}
