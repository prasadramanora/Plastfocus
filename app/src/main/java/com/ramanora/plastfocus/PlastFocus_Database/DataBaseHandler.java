package com.ramanora.plastfocus.PlastFocus_Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.ramanora.plastfocus.PlastFocus_ModelClasess.ExhibitorModel;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.QrcodeModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "plastfocusappdatabase";
    // Contacts table name

    // Contacts Table Columns names
    private static final String KEY_ID = "id";


    public static final String TABLE_NOTIFICATION = "notification";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_Products = "CREATE TABLE " + "Products" + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + "Productnames" + " VARCHAR" + ")";
        db.execSQL(CREATE_CONTACTS_Products);

        String CREATE_TABLE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION +
                "(msg text,type text)";
        db.execSQL(CREATE_TABLE_NOTIFICATION);


        String CREATE_TABLE_ExhibitorList = "CREATE TABLE " + "ExhibitorList" +
                "(Id  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "ehid VARCHAR," +
                "company_name VARCHAR, " +
                "salutation VARCHAR, " +
                "coordinator_name VARCHAR," +
                " coordinator_last_name VARCHAR," +
                " coordinator_designation VARCHAR," +
                " address VARCHAR," +
                " country VARCHAR," +
                " state VARCHAR, " +
                "city VARCHAR," +
                " pincode VARCHAR, " +
                "mobile_number VARCHAR," +
                " alternate_number VARCHAR, " +
                "fax VARCHAR," +
                " email VARCHAR, " +
                "alternate_email VARCHAR," +
                " website VARCHAR," +
                " product_group VARCHAR," +
                " company_profile VARCHAR," +
                " hall VARCHAR, " +
                "stall_number VARCHAR, " +
                "cord_x VARCHAR," +
                " cord_y VARCHAR)";
        db.execSQL(CREATE_TABLE_ExhibitorList);


        String CREATE_TABLE_ExhibitorListMeeting = "CREATE TABLE " + "ExhibitorListMeeting" +
                "(Id  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "mid VARCHAR," +
                "visitor_id VARCHAR," +
                "ehid VARCHAR," +
                "exhibitor_id VARCHAR," +
                "meeting_date VARCHAR," +
                "meeting_time VARCHAR," +
                "meeting_start_date VARCHAR," +
                "meeting_status VARCHAR," +
                "company_name VARCHAR, " +
                "salutation VARCHAR, " +
                "coordinator_name VARCHAR," +
                " coordinator_last_name VARCHAR," +
                " coordinator_designation VARCHAR," +
                " address VARCHAR," +
                " country VARCHAR," +
                " state VARCHAR, " +
                "city VARCHAR," +
                " pincode VARCHAR, " +
                "mobile_number VARCHAR," +
                " alternate_number VARCHAR, " +
                "fax VARCHAR," +
                " email VARCHAR, " +
                "alternate_email VARCHAR," +
                " website VARCHAR," +
                " product_group VARCHAR," +
                " company_profile VARCHAR," +
                " hall VARCHAR, " +
                "stall_number VARCHAR, " +
                "cord_x VARCHAR," +
                " cord_y VARCHAR)";
        db.execSQL(CREATE_TABLE_ExhibitorListMeeting);


        String CREATE_TABLE_VisitorListMeeting = "CREATE TABLE " + "VisitorListMeeting" +
                "(Id  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "mid VARCHAR," +
                "visitor_id VARCHAR," +
                "ehid VARCHAR," +
                "exhibitor_id VARCHAR," +
                "meeting_date VARCHAR," +
                "meeting_time VARCHAR," +
                "meeting_start_date VARCHAR," +
                "meeting_status VARCHAR," +
                "company VARCHAR, " +
                "full_name VARCHAR," +
                "designation VARCHAR," +
                "address VARCHAR," +
                "country VARCHAR," +
                "email VARCHAR," +

                "state VARCHAR, " +
                "city VARCHAR," +
                "zip_code VARCHAR, " +
                "phone_number VARCHAR," +
                "products VARCHAR)";
        db.execSQL(CREATE_TABLE_VisitorListMeeting);

        String CREATE_TABLE_QrcodeData = "CREATE TABLE " + "QrcodeTable" +
                "(Id  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "company_name VARCHAR," +
                "coordinator_name VARCHAR, " +
                "coordinator_designation VARCHAR, " +
                "address VARCHAR," +
                " city VARCHAR," +
                " state VARCHAR," +
                " pincode VARCHAR," +
                " mobile_number VARCHAR," +
                " email VARCHAR, " +
                "website VARCHAR," +
                " exhibitor_ref_id VARCHAR, " +
                "visitor_ref_id VARCHAR," +
                " country VARCHAR, " +
                "updated_at VARCHAR," +
                " created_at VARCHAR, " +
                " qrid VARCHAR)";
        db.execSQL(CREATE_TABLE_QrcodeData);


        String CREATE_TABLE_VisitorList = "CREATE TABLE " + "VisitorList" +
                "(Id  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "company_name VARCHAR," +
                "coordinator_name VARCHAR, " +
                "coordinator_designation VARCHAR, " +
                "address VARCHAR," +
                " city VARCHAR," +
                " state VARCHAR," +
                " pincode VARCHAR," +
                " mobile_number VARCHAR," +
                " email VARCHAR, " +
                "website VARCHAR," +
                " exhibitor_ref_id VARCHAR, " +
                "visitor_ref_id VARCHAR," +
                " country VARCHAR, " +
                "updated_at VARCHAR," +
                " created_at VARCHAR, " +
                " qrid VARCHAR, " +
                " issync VARCHAR)";
        db.execSQL(CREATE_TABLE_VisitorList);

        String CREATE_TABLE_ExhibitorData = "CREATE TABLE " + "Exhibitordata" +
                "(Id  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "exid VARCHAR," +
                "full_name VARCHAR, " +
                "email VARCHAR, " +
                "phone_number VARCHAR," +
                " company VARCHAR," +
                " designation VARCHAR," +
                " address VARCHAR," +
                " city VARCHAR," +
                " state VARCHAR, " +
                "country VARCHAR," +
                " zip_code VARCHAR, " +
                " product VARCHAR)";
        db.execSQL(CREATE_TABLE_ExhibitorData);
        String CREATE_TABLE_ProductGroup = "CREATE TABLE " + "ProductDataTable" +
                "(Id  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "productdata VARCHAR," +
                "city VARCHAR," +
                "state VARCHAR," +
                "country VARCHAR," +
                "hall VARCHAR," +
                "stall VARCHAR," +
                " companyname VARCHAR)";
        db.execSQL(CREATE_TABLE_ProductGroup);

        String CREATE_TABLE_MeetingShedule = "CREATE TABLE " + "MeetingShedule" +
                "(Id  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "datesandmeetingjson VARCHAR," +

                " title VARCHAR)";
        db.execSQL(CREATE_TABLE_MeetingShedule);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + "ExhibitorList");
        db.execSQL("DROP TABLE IF EXISTS " + "QrcodeTable");
        db.execSQL("DROP TABLE IF EXISTS " + "VisitorList");
        db.execSQL("DROP TABLE IF EXISTS " + "Exhibitordata");
        db.execSQL("DROP TABLE IF EXISTS " + "ProductDataTable");
        db.execSQL("DROP TABLE IF EXISTS " + "ExhibitorListMeeting");
        db.execSQL("DROP TABLE IF EXISTS " + "CREATE_TABLE_MeetingShedule");
        db.execSQL("DROP TABLE IF EXISTS " + "VisitorListMeeting");
        db.execSQL("DROP TABLE IF EXISTS " + "CREATE_TABLE_QrcodeDataOfflinesync");
        db.execSQL("DROP TABLE IF EXISTS " + "Products");


        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
// Adding new contact
    public void AddProductNames(PlastFocusModelClass contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Productnames", contact.getProductnames()); // CameraPojo Phone

        // Inserting Row
        db.insert("Products", null, values);
        db.close(); // Closing database connection
    }


    public void AddMeetigDatesScheduleInfo(PlastFocusModelClass contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("datesandmeetingjson", contact.getMeetingdates());
        values.put("title", contact.getMeetingtitle());// CameraPojo Phone

        // Inserting Row
        db.insert("MeetingShedule", null, values);
        db.close(); // Closing database connection
    }


    public List<PlastFocusModelClass> getMyDatesANdTimeMeeting() {
        List<PlastFocusModelClass> getMeetingTimeList = new ArrayList<PlastFocusModelClass>();

        String selectQuery = "SELECT  * FROM MeetingShedule";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {


                PlastFocusModelClass pojo = new PlastFocusModelClass();
                pojo.setMeetingdates(cursor.getString(1));
                pojo.setMeetingtitle(cursor.getString(2));

                getMeetingTimeList.add(pojo);
            } while (cursor.moveToNext());

        }


        cursor.close();

        db.close();

        return getMeetingTimeList;

    }


    public void AddProductData(PlastFocusModelClass contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productdata", contact.getProduct_group());
        values.put("companyname", contact.getCompany_name());
        values.put("city", contact.getCity());
        values.put("state", contact.getState());
        values.put("country", contact.getCountry());
        values.put("hall", contact.getHall());
        values.put("stall", contact.getStall_number());
        db.insert("ProductDataTable", null, values);
        db.close(); // Closing database connection
    }

    public void InserExhibitorDataMeeting(PlastFocusModelClass pojoHomePojo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("mid", pojoHomePojo.getMid());
        values.put("visitor_id", pojoHomePojo.getId());
        values.put("exhibitor_id", pojoHomePojo.getExhibitor_id());
        values.put("meeting_date", pojoHomePojo.getMeeting_date());
        values.put("meeting_time", pojoHomePojo.getMeeting_time());
        values.put("meeting_start_date", pojoHomePojo.getMeeting_start_date());
        values.put("meeting_status", pojoHomePojo.getMeeting_status());

        values.put("ehid", pojoHomePojo.getId());
        values.put("company_name", pojoHomePojo.getCompany_name()); // CameraPojo Phone

        // Inserting Row
        values.put("salutation", pojoHomePojo.getSalutation());

        values.put("coordinator_name", pojoHomePojo.getCoordinator_name());

        values.put("coordinator_last_name", pojoHomePojo.getCoordinator_last_name());

        values.put("coordinator_designation", pojoHomePojo.getCoordinator_designation());

        values.put("address", pojoHomePojo.getAddress());

        values.put("country", pojoHomePojo.getCountry());
        values.put("state", pojoHomePojo.getState());
        values.put("city", pojoHomePojo.getCity());

        values.put("pincode", pojoHomePojo.getPincode());
        values.put("mobile_number", pojoHomePojo.getMobile_number());

        values.put("alternate_number", pojoHomePojo.getAlternate_number());
        values.put("fax", pojoHomePojo.getFax());

        values.put("email", pojoHomePojo.getEmail());

        values.put("alternate_email", pojoHomePojo.getAlternate_email());
        values.put("website", pojoHomePojo.getWebsite());
        values.put("product_group", pojoHomePojo.getProduct_group());
        values.put("company_profile", pojoHomePojo.getCompany_profile());


        values.put("hall", pojoHomePojo.getHall());
        values.put("stall_number", pojoHomePojo.getStall_number());

        values.put("cord_x", pojoHomePojo.getCord_x());
        values.put("cord_y", pojoHomePojo.getCord_y());


        db.insert("ExhibitorListMeeting", null, values);
        db.close();
    }

    public void InserVisitorDataMeeting(PlastFocusModelClass pojoHomePojo) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("mid", pojoHomePojo.getMid());
        values.put("visitor_id", pojoHomePojo.getId());
        values.put("exhibitor_id", pojoHomePojo.getExhibitor_id());
        values.put("meeting_date", pojoHomePojo.getMeeting_date());
        values.put("meeting_time", pojoHomePojo.getMeeting_time());
        values.put("meeting_start_date", pojoHomePojo.getMeeting_start_date());
        values.put("meeting_status", pojoHomePojo.getMeeting_status());

        values.put("company", pojoHomePojo.getCompany_name());
        values.put("full_name", pojoHomePojo.getCoordinator_name()); // CameraPojo Phone

        // Inserting Row
        values.put("designation", pojoHomePojo.getCoordinator_designation());

        values.put("address", pojoHomePojo.getCoordinator_name());

        values.put("country", pojoHomePojo.getCoordinator_last_name());
        values.put("email", pojoHomePojo.getEmail());
        values.put("state", pojoHomePojo.getCoordinator_designation());

        values.put("address", pojoHomePojo.getAddress());

        values.put("country", pojoHomePojo.getCountry());
        values.put("state", pojoHomePojo.getState());
        values.put("city", pojoHomePojo.getCity());

        values.put("zip_code", pojoHomePojo.getPincode());
        values.put("phone_number", pojoHomePojo.getMobile_number());


        values.put("products", pojoHomePojo.getProduct_group());


        db.insert("VisitorListMeeting", null, values);
        db.close();
    }

    public void InserExhibitorData(PlastFocusModelClass pojoHomePojo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ehid", pojoHomePojo.getId());
        values.put("company_name", pojoHomePojo.getCompany_name()); // CameraPojo Phone

        // Inserting Row
        values.put("salutation", pojoHomePojo.getSalutation());

        values.put("coordinator_name", pojoHomePojo.getCoordinator_name());

        values.put("coordinator_last_name", pojoHomePojo.getCoordinator_last_name());

        values.put("coordinator_designation", pojoHomePojo.getCoordinator_designation());

        values.put("address", pojoHomePojo.getAddress());

        values.put("country", pojoHomePojo.getCountry());
        values.put("state", pojoHomePojo.getState());
        values.put("city", pojoHomePojo.getCity());

        values.put("pincode", pojoHomePojo.getPincode());
        values.put("mobile_number", pojoHomePojo.getMobile_number());

        values.put("alternate_number", pojoHomePojo.getAlternate_number());
        values.put("fax", pojoHomePojo.getFax());

        values.put("email", pojoHomePojo.getEmail());

        values.put("alternate_email", pojoHomePojo.getAlternate_email());
        values.put("website", pojoHomePojo.getWebsite());
        values.put("product_group", pojoHomePojo.getProduct_group());
        values.put("company_profile", pojoHomePojo.getCompany_profile());


        values.put("hall", pojoHomePojo.getHall());
        values.put("stall_number", pojoHomePojo.getStall_number());

        values.put("cord_x", pojoHomePojo.getCord_x());
        values.put("cord_y", pojoHomePojo.getCord_y());

        db.insert("ExhibitorList", null, values);
        db.close();
    }

    public SQLiteDatabase updateQrcodeData(ContentValues contentValues, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.update(
                "VisitorList",
                contentValues,
                "email=?",
                new String[]{email});
        db.close();
        return db;
    }

    public void InserVisitorList(QrcodeModel pojoHomePojo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("company_name", pojoHomePojo.getCompany_name());
        values.put("coordinator_name", pojoHomePojo.getCoordinator_name()); // CameraPojo Phone
        values.put("coordinator_designation", pojoHomePojo.getCoordinator_designation());
        values.put("address", pojoHomePojo.getAddress());

        values.put("city", pojoHomePojo.getCity());
        values.put("state", pojoHomePojo.getState()); // CameraPojo Phone
        values.put("pincode", pojoHomePojo.getPincode());
        values.put("mobile_number", pojoHomePojo.getMobile_number());

        values.put("email", pojoHomePojo.getEmail());
        // values.put("website", pojoHomePojo.getWebsite()); // CameraPojo Phone
        values.put("exhibitor_ref_id", pojoHomePojo.getExhibitor_ref_id());
        values.put("visitor_ref_id", pojoHomePojo.getVisitor_ref_id());

        values.put("country", pojoHomePojo.getCountry());
        values.put("updated_at", pojoHomePojo.getUpdated_at()); // CameraPojo Phone
        values.put("created_at", pojoHomePojo.getCreated_at());
        values.put("qrid", pojoHomePojo.getQrid());
        values.put("issync", pojoHomePojo.getIssync());


        db.insert("VisitorList", null, values);
        db.close();
    }

    public void InsertExhibitorData(ExhibitorModel pojoHomePojo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("exid", pojoHomePojo.getId());
        values.put("full_name", pojoHomePojo.getFull_name()); // CameraPojo Phone
        values.put("email", pojoHomePojo.getEmail());
        values.put("phone_number", pojoHomePojo.getPhone_number());

        values.put("company", pojoHomePojo.getCompany());
        values.put("designation", pojoHomePojo.getDesignation()); // CameraPojo Phone
        values.put("address", pojoHomePojo.getAddress());
        values.put("city", pojoHomePojo.getCity());

        values.put("state", pojoHomePojo.getState());
        values.put("country", pojoHomePojo.getCountry()); // CameraPojo Phone
        values.put("zip_code", pojoHomePojo.getZip_code());
        values.put("product", pojoHomePojo.getProducts());


        db.insert("Exhibitordata", null, values);
        db.close();
    }

    public void InserQECodeTable(QrcodeModel pojoHomePojo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("company_name", pojoHomePojo.getCompany_name());
        values.put("coordinator_name", pojoHomePojo.getCoordinator_name()); // CameraPojo Phone
        values.put("coordinator_designation", pojoHomePojo.getCoordinator_designation());
        values.put("address", pojoHomePojo.getAddress());

        values.put("city", pojoHomePojo.getCity());
        values.put("state", pojoHomePojo.getState()); // CameraPojo Phone
        values.put("pincode", pojoHomePojo.getPincode());
        values.put("mobile_number", pojoHomePojo.getMobile_number());

        values.put("email", pojoHomePojo.getEmail());
        values.put("website", pojoHomePojo.getWebsite()); // CameraPojo Phone
        values.put("exhibitor_ref_id", pojoHomePojo.getExhibitor_ref_id());
        values.put("visitor_ref_id", pojoHomePojo.getVisitor_ref_id());

        values.put("country", pojoHomePojo.getCountry());
        values.put("updated_at", pojoHomePojo.getUpdated_at()); // CameraPojo Phone
        values.put("created_at", pojoHomePojo.getCreated_at());
        values.put("qrid", pojoHomePojo.getQrid());


        db.insert("QrcodeTable", null, values);
        db.close();
    }


    //add notification
    public void addNotification(String msg, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("msg", msg);
        values.put("type", type);

        long q = db.insert(TABLE_NOTIFICATION, null, values);


        db.close();
    }

    public List<PlastFocusModelClass> getExhibitorData() {
        List<PlastFocusModelClass> getExhibitorList = new ArrayList<PlastFocusModelClass>();

        String selectQuery = "SELECT  * FROM ExhibitorList ORDER BY id";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                PlastFocusModelClass pojo = new PlastFocusModelClass();
                pojo.setId(cursor.getString(1));

                pojo.setCompany_name(cursor.getString(2));
                pojo.setCoordinator_name(cursor.getString(4));
                pojo.setCoordinator_last_name(cursor.getString(5));
                pojo.setSalutation(cursor.getString(3));
                pojo.setAddress(cursor.getString(7));
                pojo.setCoordinator_designation(cursor.getString(6));
                pojo.setCountry(cursor.getString(8));
                pojo.setState(cursor.getString(9));
                pojo.setCity(cursor.getString(10));
                pojo.setPincode(cursor.getString(11));
                pojo.setWebsite(cursor.getString(17));
                pojo.setProduct_group(cursor.getString(18));
                pojo.setCompany_profile(cursor.getString(19));
                pojo.setHall(cursor.getString(20));
                pojo.setStall_number(cursor.getString(21));
                pojo.setMobile_number(cursor.getString(12));
                pojo.setAlternate_number(cursor.getString(13));
                pojo.setFax(cursor.getString(14));
                pojo.setEmail(cursor.getString(15));
                pojo.setAlternate_email(cursor.getString(16));


                pojo.setCord_x(cursor.getString(22));
                pojo.setCord_y(cursor.getString(23));

                getExhibitorList.add(pojo);
            } while (cursor.moveToNext());

        }

        cursor.close();

        db.close();

        // return contact list
        return getExhibitorList;

    }

    public List<PlastFocusModelClass> getExhibitorMeetingData() {
        List<PlastFocusModelClass> getExhibitorMeetingList = new ArrayList<PlastFocusModelClass>();

        String selectQuery = "SELECT  * FROM VisitorListMeeting";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                PlastFocusModelClass pojo = new PlastFocusModelClass();
                pojo.setMid(cursor.getString(0));

                pojo.setMeeting_date(cursor.getString(5));
                pojo.setMeeting_time(cursor.getString(6));

                pojo.setCoordinator_name(cursor.getString(10));
                pojo.setCoordinator_designation(cursor.getString(11));
                pojo.setAddress(cursor.getString(12));
                pojo.setCompany_name(cursor.getString(9));
                pojo.setCountry(cursor.getString(13));
                pojo.setEmail(cursor.getString(14));
                pojo.setState(cursor.getString(15));
                pojo.setCity(cursor.getString(16));
                pojo.setPincode(cursor.getString(17));
                pojo.setProduct_group(cursor.getString(19));
                pojo.setMobile_number(cursor.getString(18));
                getExhibitorMeetingList.add(pojo);
            } while (cursor.moveToNext());

        }

        cursor.close();

        db.close();

        // return contact list
        return getExhibitorMeetingList;

    }

    public List<PlastFocusModelClass> getVisitorMeetingData() {
        List<PlastFocusModelClass> visitormeetingList = new ArrayList<PlastFocusModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM ExhibitorListMeeting";
            SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                PlastFocusModelClass pojo = new PlastFocusModelClass();
                pojo.setId(cursor.getString(0));

                pojo.setMeeting_date(cursor.getString(5));
                pojo.setMeeting_time(cursor.getString(6));

                pojo.setCompany_name(cursor.getString(9));
                pojo.setCoordinator_name(cursor.getString(11));
                pojo.setCoordinator_last_name(cursor.getString(12));
                pojo.setSalutation(cursor.getString(10));
                pojo.setAddress(cursor.getString(14));
                pojo.setCoordinator_designation(cursor.getString(13));
                pojo.setCountry(cursor.getString(15));
                pojo.setState(cursor.getString(16));
                pojo.setCity(cursor.getString(17));
                pojo.setPincode(cursor.getString(18));
                pojo.setWebsite(cursor.getString(24));
                pojo.setProduct_group(cursor.getString(18));
                pojo.setCompany_profile(cursor.getString(26));
                pojo.setHall(cursor.getString(27));
                pojo.setStall_number(cursor.getString(28));
                pojo.setMobile_number(cursor.getString(20));
                pojo.setAlternate_number(cursor.getString(13));
                pojo.setFax(cursor.getString(14));
                pojo.setEmail(cursor.getString(22));
                pojo.setAlternate_email(cursor.getString(23));

                visitormeetingList.add(pojo);
            } while (cursor.moveToNext());

        }

        cursor.close();

        db.close();

        // return contact list
        return visitormeetingList;

    }

    public List<PlastFocusModelClass> getExhibitorDatafilterbtproduct(String productname) {
        List<PlastFocusModelClass> getProductList = new ArrayList<PlastFocusModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM ExhibitorList ORDER BY id";
              SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                PlastFocusModelClass pojo = new PlastFocusModelClass();
                pojo.setId(cursor.getString(1));

                pojo.setCompany_name(cursor.getString(2));
                pojo.setCoordinator_name(cursor.getString(4));
                pojo.setCoordinator_last_name(cursor.getString(5));
                pojo.setSalutation(cursor.getString(3));
                pojo.setAddress(cursor.getString(7));
                pojo.setCoordinator_designation(cursor.getString(6));
                pojo.setCountry(cursor.getString(8));
                pojo.setState(cursor.getString(9));
                pojo.setCity(cursor.getString(10));
                pojo.setPincode(cursor.getString(11));
                pojo.setWebsite(cursor.getString(17));
                pojo.setProduct_group(cursor.getString(18));
                pojo.setCompany_profile(cursor.getString(19));
                pojo.setHall(cursor.getString(20));
                pojo.setStall_number(cursor.getString(21));
                pojo.setMobile_number(cursor.getString(12));
                pojo.setAlternate_number(cursor.getString(13));
                pojo.setFax(cursor.getString(14));
                pojo.setEmail(cursor.getString(15));
                pojo.setAlternate_email(cursor.getString(16));
                pojo.setCord_x(cursor.getString(22));
                pojo.setCord_y(cursor.getString(23));
                // Adding contact to list
                System.out.println("mArrayListCompany country :" + cursor.getString(18));
                // Log.e("CheckDatabse100", cursor.getString(18));
                if (cursor.getString(18).replace("\\/", "/").contains(productname)) {
                    getProductList.add(pojo);
                }

            } while (cursor.moveToNext());

        }


        cursor.close();

        db.close();

        // return contact list
        return getProductList;

    }


    public List<QrcodeModel> getVisitorDataissync(String issyncno) {
        List<QrcodeModel> getVisitorDataList = new ArrayList<QrcodeModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + "VisitorList" + " WHERE " + " issync='" + issyncno + "'";
        //  String selectQuery = "SELECT DISTINCT * FROM VisitorList "+ " WHERE " + "issync" + " = " + issyncno + "";
          SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                QrcodeModel pojo = new QrcodeModel();
                pojo.setCompany_name(cursor.getString(1));

                pojo.setCoordinator_name(cursor.getString(2));
                pojo.setCoordinator_designation(cursor.getString(3));
                pojo.setAddress(cursor.getString(4));
                pojo.setMobile_number(cursor.getString(8));
                pojo.setEmail(cursor.getString(9));
                pojo.setQrid(cursor.getString(0));
                getVisitorDataList.add(pojo);
            } while (cursor.moveToNext());

        }

        // close inserting data from database
        cursor.close();

        db.close();

        // return contact list
        return getVisitorDataList;

    }

    public List<QrcodeModel> getVisitorData() {
        List<QrcodeModel> getVisitorList = new ArrayList<QrcodeModel>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT * FROM VisitorList ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                QrcodeModel pojo = new QrcodeModel();
                pojo.setCompany_name(cursor.getString(1));

                pojo.setCoordinator_name(cursor.getString(2));
                pojo.setCoordinator_designation(cursor.getString(3));
                pojo.setAddress(cursor.getString(4));
                pojo.setMobile_number(cursor.getString(8));
                pojo.setEmail(cursor.getString(9));
                pojo.setQrid(cursor.getString(0));


                // Adding contact to list
                getVisitorList.add(pojo);
            } while (cursor.moveToNext());

        }

        // close inserting data from database
        cursor.close();

        db.close();

        // return contact list
        return getVisitorList;

    }

    public List<ExhibitorModel> getExisitorData() {
        List<ExhibitorModel> getExhibitorList = new ArrayList<ExhibitorModel>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT * FROM Exhibitordata ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                ExhibitorModel pojo = new ExhibitorModel();
                pojo.setCompany(cursor.getString(5));

                pojo.setFull_name(cursor.getString(2));
                pojo.setDesignation(cursor.getString(6));
                pojo.setAddress(cursor.getString(7));
                pojo.setPhone_number(cursor.getString(4));
                pojo.setEmail(cursor.getString(3));
                pojo.setId(cursor.getString(0));
                pojo.setProducts(cursor.getString(12));

                pojo.setCity(cursor.getString(8));
                pojo.setState(cursor.getString(9));
                pojo.setCountry(cursor.getString(10));
                pojo.setZip_code(cursor.getString(11));


                // Adding contact to list
                getExhibitorList.add(pojo);
            } while (cursor.moveToNext());

        }

        // close inserting data from database
        cursor.close();

        db.close();

        // return contact list
        return getExhibitorList;

    }


    public List<PlastFocusModelClass> getFilterExhibitorDatabycountry(String country) {
        List<PlastFocusModelClass> getCountryList = new ArrayList<PlastFocusModelClass>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM ExhibitorList ORDER BY id";
        String selectQuery = "SELECT * FROM " + "ExhibitorList" + " WHERE country='" + country + "'";
             SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                PlastFocusModelClass pojo = new PlastFocusModelClass();
                pojo.setId(cursor.getString(1));

                pojo.setCompany_name(cursor.getString(2));
                pojo.setCoordinator_name(cursor.getString(4));
                pojo.setCoordinator_last_name(cursor.getString(5));
                pojo.setSalutation(cursor.getString(3));
                pojo.setAddress(cursor.getString(7));
                pojo.setCoordinator_designation(cursor.getString(6));
                pojo.setCountry(cursor.getString(8));
                pojo.setState(cursor.getString(9));
                pojo.setCity(cursor.getString(10));
                pojo.setPincode(cursor.getString(11));
                pojo.setWebsite(cursor.getString(17));
                pojo.setProduct_group(cursor.getString(18));
                pojo.setCompany_profile(cursor.getString(19));
                pojo.setHall(cursor.getString(20));
                pojo.setStall_number(cursor.getString(21));
                pojo.setMobile_number(cursor.getString(12));
                pojo.setAlternate_number(cursor.getString(13));
                pojo.setFax(cursor.getString(14));
                pojo.setEmail(cursor.getString(15));
                pojo.setAlternate_email(cursor.getString(16));
                pojo.setCord_x(cursor.getString(22));
                pojo.setCord_y(cursor.getString(23));
                // Adding contact to list
                getCountryList.add(pojo);
            } while (cursor.moveToNext());

        }


        // close inserting data from database
        cursor.close();

        db.close();

        // return contact list
        return getCountryList;

    }

    public List<PlastFocusModelClass> getFilterExhibitorDatabyHall(String hall) {
        List<PlastFocusModelClass> getHallList = new ArrayList<PlastFocusModelClass>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM ExhibitorList ORDER BY id";
        String selectQuery = "SELECT * FROM " + "ExhibitorList" + " WHERE hall='" + hall + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                PlastFocusModelClass pojo = new PlastFocusModelClass();
                pojo.setId(cursor.getString(1));

                pojo.setCompany_name(cursor.getString(2));
                pojo.setCoordinator_name(cursor.getString(4));
                pojo.setCoordinator_last_name(cursor.getString(5));
                pojo.setSalutation(cursor.getString(3));
                pojo.setAddress(cursor.getString(7));
                pojo.setCoordinator_designation(cursor.getString(6));
                pojo.setCountry(cursor.getString(8));
                pojo.setState(cursor.getString(9));
                pojo.setCity(cursor.getString(10));
                pojo.setPincode(cursor.getString(11));
                pojo.setWebsite(cursor.getString(17));
                pojo.setProduct_group(cursor.getString(18));
                pojo.setCompany_profile(cursor.getString(19));
                pojo.setHall(cursor.getString(20));
                pojo.setStall_number(cursor.getString(21));
                pojo.setMobile_number(cursor.getString(12));
                pojo.setAlternate_number(cursor.getString(13));
                pojo.setFax(cursor.getString(14));
                pojo.setEmail(cursor.getString(15));
                pojo.setAlternate_email(cursor.getString(16));
                pojo.setCord_x(cursor.getString(22));
                pojo.setCord_y(cursor.getString(23));
                // Adding contact to list
                getHallList.add(pojo);
            } while (cursor.moveToNext());

        }


        // close inserting data from database
        cursor.close();

        db.close();

        // return contact list
        return getHallList;

    }


    @SuppressLint("Range")
    public ArrayList<PlastFocusModelClass> getCountrylist() {
        ArrayList<PlastFocusModelClass> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT country " +
                "FROM ExhibitorList  ORDER BY country ASC", null);
        //Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PlastFocusModelClass listPojo = new PlastFocusModelClass();

            listPojo.setCountry(cursor.getString(cursor.getColumnIndex("country")));

            list.add(listPojo);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<PlastFocusModelClass> getHalllist() {
        ArrayList<PlastFocusModelClass> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT hall " +
                "FROM ExhibitorList  ORDER BY hall ASC", null);
        //Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PlastFocusModelClass listPojo = new PlastFocusModelClass();

            listPojo.setHall(cursor.getString(cursor.getColumnIndex("hall")));

            list.add(listPojo);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public static String getStringFromCursor(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return (columnIndex != -1) ? cursor.getString(columnIndex) : null;
    }


    public ArrayList<PlastFocusModelClass> ProductNamelIst() {
        ArrayList<PlastFocusModelClass> csvInfoslist = new ArrayList<PlastFocusModelClass>();

        String selectQuery = "SELECT * FROM " + "Products" + "";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(selectQuery, null); // looping through all rows and adding to list


        if (c.moveToFirst()) {
            do {

                PlastFocusModelClass csv = new PlastFocusModelClass();
                csv.setProductnames((getStringFromCursor(c, "Productnames")));
                //Log.e("mypnames", getStringFromCursor(c, "Productnames"));


                csvInfoslist.add(csv);
            } while (c.moveToNext());
        }


        c.close();
        db.close();
        Log.d("test_getall", "getAll: " + csvInfoslist.size());

        return csvInfoslist;
    }


    public int getNotificationCount() {
        String countQuery = "SELECT * FROM " + TABLE_NOTIFICATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        Log.d("notificationcount", String.valueOf(cnt));
        cursor.close();
        return cnt;
    }


}
