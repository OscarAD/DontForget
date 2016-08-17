package com.COP4655.Odiaz053.DontForget.Data;


public class Constants {
    //DB Structure
    static final String ROW_ID = "id";
    static final String NAME = "name";


    //DB Info
    static final String DB_NAME = "DF.db";
    static final String TB_NAME = "hh_TB";
    static final int DB_VERSION = 1;


    //Create Table
    static final String CREATE_TB = "CREATE TABLE " + TB_NAME + " ( "
            + ROW_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT NOT NULL)";


    //Drop Table
    static final String DROP_TB = "DROP TABLE IF EXISTS " + TB_NAME;


}
