package com.evs.dalinkup.net.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sarps on 3/1/2017.
 */
public class DBHanlder extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Dalinkup.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TBL_PRODUCT = "tblProducts";
    public static final String KEY_ID = "id";
    public static final String KEY_PRODUCTID = "productid";
    public static final String KEY_PNAME = "productname";
    public static final String KEY_PIMG = "productimgurl";
    public static final String KEY_PPRICE = "price";
    public static final String KEY_QTY = "quantity";

    public DBHanlder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DISHES = "CREATE TABLE " + TBL_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PRODUCTID + " TEXT, " + KEY_PNAME + " TEXT, " + KEY_PIMG + " TEXT, " +
                KEY_PPRICE + " TEXT, " + KEY_QTY + " TEXT " +")";
        sqLiteDatabase.execSQL(CREATE_DISHES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  void delete_id(String id){
        //Open the database
        SQLiteDatabase database = DBHanlder.this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + TBL_PRODUCT + " WHERE " + KEY_ID + "= '" + id + "'");

        //Close the database
        database.close();
    }
    public  void delete_all_data(){
        //Open the database
        SQLiteDatabase database = DBHanlder.this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + TBL_PRODUCT);

        //Close the database
        database.close();
    }
}
