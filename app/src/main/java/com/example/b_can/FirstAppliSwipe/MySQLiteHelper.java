package com.example.b_can.FirstAppliSwipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.b_can.FirstAppliSwipe.Model.Message;
import com.example.b_can.FirstAppliSwipe.Model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by b_can on 20/02/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SwipeDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create user table
        String CREATE_USER_TABLE = "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pseudo TEXT, "+
                "password TEXT, "+
                "distance INTEGER )";

        // create users table
        db.execSQL(CREATE_USER_TABLE);
        ContentValues values = new ContentValues();
        values.put(KEY_PSEUDO, ""); // get pseudo
        values.put(KEY_PASSWORD, ""); // get password
        values.put(KEY_DISTANCE, 49); // get distance

        // 3. insert
        db.insert(TABLE_USERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // SQL statement to create messages table
        String CREATE_MESSAGE_TABLE = "CREATE TABLE messages ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pseudo TEXT, "+
                "civilite TEXT, "+
                "date TEXT, "+
                "path TEXT )";

        // create messages table
        db.execSQL(CREATE_MESSAGE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older user table if existed
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS messages");
        // create fresh users table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * USERS
     * CRUD operations (create "add", read "get", update, delete) user + get all users + delete all users
     */

    // Users table name
    private static final String TABLE_USERS = "Users";

    // Users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PSEUDO = "pseudo";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DISTANCE = "distance";

    private static final String[] COLUMNS = {KEY_ID, KEY_PSEUDO, KEY_PASSWORD, KEY_DISTANCE};

    public void addUser(User user){
        Log.d("addUser", user.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_PSEUDO, user.getPseudo()); // get pseudo
        values.put(KEY_PASSWORD, user.getPassword()); // get password
        values.put(KEY_DISTANCE, user.getDistance()); // get distance

        // 3. insert
        db.insert(TABLE_USERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public User getUser(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_USERS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build user object
        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setPseudo(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setDistance(Integer.parseInt(cursor.getString(3)));

        Log.d("getUser("+id+")", user.toString());

        // 5. return user
        return user;
    }

    // Get All Users
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USERS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setPseudo(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setDistance(Integer.parseInt(cursor.getString(3)));

                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUsers()", users.toString());

        // return users
        return users;
    }

    // Updating single user
    public int updateUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("pseudo", user.getPseudo()); // get pseudo
        values.put("password", user.getPassword()); // get password
        values.put("distance", user.getDistance()); // get distance

        // 3. updating row
        int i = db.update(TABLE_USERS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(user.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single user
    public void deleteUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USERS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(user.getId()) });

        // 3. close
        db.close();

        Log.d("deleteUser", user.toString());

    }

    /**
     * MESSAGES
     * CRUD operations (create "add", read "get", update, delete) message + get all messages + delete all messages
     */

    // Messages table name
    private static final String TABLE_MESSAGES = "Messages";

    // Messages Table Columns names
    private static final String KEY_ID2 = "id";
    private static final String KEY_PSEUDO2 = "pseudo";
    private static final String KEY_CIVILITE = "civilite";
    private static final String KEY_DATE = "date";
    private static final String KEY_PATH = "path";

    private static final String[] COLUMNS2 = {KEY_ID2, KEY_PSEUDO2, KEY_CIVILITE, KEY_DATE, KEY_PATH};

    public void addMessage(Message message){
        Log.d("addMessage", message.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_PSEUDO2, message.getPseudo()); // get pseudo
        values.put(KEY_CIVILITE, message.getCivilite()); // get civilite
        values.put(KEY_DATE, message.getDate()); // get date
        values.put(KEY_PATH, message.getPath()); // get path

        // 3. insert
        db.insert(TABLE_MESSAGES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Message getMessage(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MESSAGES, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build message object
        Message message = new Message();
        message.setId(Integer.parseInt(cursor.getString(0)));
        message.setPseudo(cursor.getString(1));
        message.setCivilite(cursor.getString(2));
        message.setDate(cursor.getString(3));
        message.setPath(cursor.getString(4));

        Log.d("getMessage("+id+")", message.toString());

        // 5. return message
        return message;
    }

    // Get All Messages
    public List<Message> getAllMessages() {
        ArrayList<Message> messages = new ArrayList<Message>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_MESSAGES + " ORDER BY " + KEY_ID2 + " DESC";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build message and add it to list
        Message message = null;
        if (cursor.moveToFirst()) {
            do {
                message = new Message();
                message.setId(Integer.parseInt(cursor.getString(0)));
                message.setPseudo(cursor.getString(1));
                message.setCivilite(cursor.getString(2));
                message.setDate(cursor.getString(3));
                message.setPath(cursor.getString(4));

                // Add message to messages
                messages.add(message);
            } while (cursor.moveToNext());
        }

        Log.d("getAllMessages()", messages.toString());

        // return messages
        return messages;
    }

    // Updating single message
    public int updateMessage(Message message) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("pseudo", message.getPseudo()); // get pseudo
        values.put("civilite", message.getCivilite()); // get civilite
        values.put("date", message.getDate()); // get date
        values.put("path", message.getPath()); // get path

        // 3. updating row
        int i = db.update(TABLE_MESSAGES, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(message.getId()) }); //selection args

        // 4. close
        db.close();
        return i;
    }

    // Deleting single message
    public void deleteMessage(Message message) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_MESSAGES,
                KEY_ID+" = ?",
                new String[] { String.valueOf(message.getId()) });

        // 3. close
        db.close();

        Log.d("deleteMessage", message.toString());

    }
}
