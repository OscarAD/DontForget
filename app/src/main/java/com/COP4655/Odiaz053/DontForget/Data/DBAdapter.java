package com.COP4655.Odiaz053.DontForget.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;


    //Gets context when created and assigns it to declared Context
    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }


    //OPEN DataBase
    public void openDB() {


        db = helper.getWritableDatabase();


    }

    //CLOSE DataBase
    public void closeDB() {


        helper.close();

    }

    //SAVE (Boolean to return if successful)
    public boolean add(String name) {

        try {

            ContentValues cv = new ContentValues();
            //Key(name) Value(name Entered)
            cv.put(Constants.NAME, name);


            long result = db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);

            if (result > 0) {

                //Successful
                return true;
            }

        } catch (SQLException e) {

            //Something went wrong
            e.printStackTrace();

        }

        return false;


    }

    //SELECT
    //Provides read-write access to the result set returned by a database query.
    public Cursor retrieve() {

        String[] columns = {Constants.ROW_ID, Constants.NAME};


        Cursor c = db.query(Constants.TB_NAME, columns, null, null, null, null, null);

        //Returns cursor
        return c;
    }


    //UPDATE  **EDIT** (Boolean to return if successful)
    //Passing new name and ID
    public boolean update(String newName, int id) {

        try {

            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, newName);

            int result = db.update(Constants.TB_NAME, cv, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if (result > 0) {

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    //DELETE FROM DATABASE
    public boolean delete(int id) {

        try {

            int result = db.delete(Constants.TB_NAME, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if (result > 0) {

                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }



    //DELETE  ALL
    public boolean deleteAll() {

        try {

            int result = db.delete(Constants.TB_NAME,null,null);
            if (result > 0) {

                return true;
            }


        } catch (SQLException e) {

        }

        return false;

    }


}

