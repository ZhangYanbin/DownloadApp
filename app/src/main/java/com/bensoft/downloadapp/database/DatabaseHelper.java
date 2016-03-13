package com.bensoft.downloadapp.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.design.widget.TabLayout;

/**
 * Created by 炎宾 on 2016/3/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final int DB_VERSION = 0x001;

    public static class TABLE {
        public static final String TABLE_NAME_THREADINFOR = "thread_infor";
        public static final String TABLE_NAME_FILEINFOR = "file_infor";
    }

    public static class BaseColumn {
        public static final String ID = "_id";
    }

    public static class TableThreadInfoColumn extends BaseColumn {
        public static final String THREAD_ID = "thread_id";
        public static final String FILE_URL = "url";
        public static final String FILE_ID = "file_id";
        public static final String FILE_START_INDEX = "start_index";
        public static final String FILE_END_INDEX = "end_index";
        public static final String FILE_DOWNLOAD_LENGTH = "download_length";
        public static final String TABLE_CREATE_SQL = "create table " +
                TABLE.TABLE_NAME_THREADINFOR + "(" +
                ID + " integer primary key autoincrement," +
                THREAD_ID + " integer," +
                FILE_URL + " text" +
                FILE_ID + " integer" +
                FILE_START_INDEX +" integer" +
                FILE_END_INDEX + " integer" +
                FILE_DOWNLOAD_LENGTH + "integer" +
                ");";

        public static final String TABLE_DROP_SQL = "drop table if exist "
                + TABLE.TABLE_NAME_THREADINFOR;

        public static final String VALUE_INSERT_SQL = "insert into " + TABLE.TABLE_NAME_THREADINFOR
                + "("+ THREAD_ID +"," +
                FILE_URL +"," +
                FILE_ID +"," +
                FILE_START_INDEX +"," +
                FILE_END_INDEX +"," +
                FILE_DOWNLOAD_LENGTH +"," +
                ")"
                + "with values(?,?,?,?,?,?)";

        public static final String DOWNLOAD_PROGRESS_UPDATE_SQL = "update " + TABLE.TABLE_NAME_THREADINFOR
                + " set " +
                FILE_DOWNLOAD_LENGTH + "= ?"+
                " where " +
                FILE_URL +" = ? and " +
                THREAD_ID + " = ?;";

        public static final String THREAD_LIST_QUERY_SQL = "select * from " + TABLE.TABLE_NAME_THREADINFOR
                + " where " + FILE_URL +" =? ";

        public static SQLiteStatement tableCreateSqlStatement(SQLiteDatabase db) {
            SQLiteStatement sqLiteStatement = db.compileStatement(TABLE_CREATE_SQL);
            return sqLiteStatement;
        }

        public static SQLiteStatement tableDropSqlStatement(SQLiteDatabase db) {
            SQLiteStatement sqLiteStatement = db.compileStatement(TABLE_DROP_SQL);
            return sqLiteStatement;
        }

        /**
         * String array sort like,( thread_id (integer) , url (String) ,file_id (integer),
         * start_index (integer), end_index(integer), download_length(integer)
         * @param db
         * @param values
         * @return
         */
        public static SQLiteStatement valueInsertSqlStatement(SQLiteDatabase db, String [] values) {
            SQLiteStatement sqLiteStatement = db.compileStatement(VALUE_INSERT_SQL);
            sqLiteStatement.bindAllArgsAsStrings(values);
            return  sqLiteStatement;
        }

        /**
         *
         * @param db
         * @param url
         * @param threadId
         * @param progress
         * @return
         */
        public static SQLiteStatement downloadProgressUpdateSqlStatement(SQLiteDatabase db,String url, int threadId,int progress) {
            SQLiteStatement sqLiteStatement = db.compileStatement(DOWNLOAD_PROGRESS_UPDATE_SQL);
            sqLiteStatement.bindAllArgsAsStrings(new String[]{url,threadId+"",progress+""});
            return sqLiteStatement;
        }

    }

    public static class TableFileInforColumn extends BaseColumn {
        public static final String URL = "url";
        public static final String FILE_NAME = "name";
        public static final String FILE_LENGTH = "length";
        public static final String TABLE_CREATE_SQL = "create table " +
                TABLE.TABLE_NAME_FILEINFOR + "(" +
                ID + " integer primary key autoincrement," +
                URL + " text," +
                FILE_NAME + " text" +
                FILE_LENGTH +" integer" +
                ");";

        public static final String TABLE_DROP_SQL = "drop table if exist "
                + TABLE.TABLE_NAME_FILEINFOR;
        public static final String VALUE_INSERT_SQL = "insert into " + TABLE.TABLE_NAME_FILEINFOR
                + " (?,?,?)";

        public static SQLiteStatement tableCreateSqlStatement(SQLiteDatabase db) {
            SQLiteStatement sqLiteStatement = db.compileStatement(TABLE_CREATE_SQL);
            return sqLiteStatement;
        }

        public static SQLiteStatement tableDropSqlStatement(SQLiteDatabase db) {
            SQLiteStatement sqLiteStatement = db.compileStatement(TABLE_DROP_SQL);
            return sqLiteStatement;
        }

        /**
         * String array sort like,( url (String) ,name (String),
         * length (integer)
         * @param db
         * @param values  url (String) ,name (String), length (integer)
         * @return
         */
        public static SQLiteStatement valueInsertSqlStatement(SQLiteDatabase db, String [] values) {
            SQLiteStatement sqLiteStatement = db.compileStatement(VALUE_INSERT_SQL);
            sqLiteStatement.bindAllArgsAsStrings(values);
            return  sqLiteStatement;
        }
    }


    /*
    private int mId;
    private FileInfor mFile;
    private int mStart;
    private int mEnd;
    private int mLengthDownload;
     */

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        TableThreadInfoColumn.tableCreateSqlStatement(db).execute();
        TableFileInforColumn.tableCreateSqlStatement(db).execute();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TableThreadInfoColumn.tableDropSqlStatement(db).execute();
        TableThreadInfoColumn.tableCreateSqlStatement(db).execute();

        TableFileInforColumn.tableDropSqlStatement(db).execute();
        TableFileInforColumn.tableCreateSqlStatement(db).execute();
    }
}
