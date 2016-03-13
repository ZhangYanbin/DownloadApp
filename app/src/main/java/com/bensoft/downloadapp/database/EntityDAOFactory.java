package com.bensoft.downloadapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.bensoft.downloadapp.database.DatabaseHelper.TableThreadInfoColumn;
import com.bensoft.downloadapp.database.DatabaseHelper.TableFileInforColumn;
import com.bensoft.downloadapp.entities.Entity;
import com.bensoft.downloadapp.entities.FileInfor;
import com.bensoft.downloadapp.entities.ThreadInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by 炎宾 on 2016/3/13.
 */
public class EntityDAOFactory {

    public static class ThreadInfoDAOImpl implements EntityDAO<ThreadInfo> {
        private DatabaseHelper mDBHelper;
        private ThreadInfoDAOImpl(Context context) {
            mDBHelper = new DatabaseHelper(context);
        }

        @Override
        public long insertEntity(ThreadInfo entity) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            String [] entityValues = new String[] {entity.getmId()+"",
                    entity.getmFile().getmUrl(),entity.getmStart()+"",
                    entity.getmEnd()+"",entity.getmLengthDownload()+""};

            long count = TableThreadInfoColumn.valueInsertSqlStatement(db, entityValues )
                    .executeInsert();
            db.close();
            return count;
        }

        @Override
        public long deleteEntity(ThreadInfo entity) {
            return 0;
        }

        @Override
        public long updateEntity(ThreadInfo entity) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            long count = TableThreadInfoColumn.downloadProgressUpdateSqlStatement(db,
                    entity.getmFile().getmUrl(),entity.getmId(), entity.getmLengthDownload() )
                    .executeInsert();
            db.close();
            return count;
        }

        @Override
        public List<ThreadInfo> getEntities(String url) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(TableThreadInfoColumn.THREAD_LIST_QUERY_SQL, new String[]{url});
            ArrayList<ThreadInfo> threadInfosList = new ArrayList<ThreadInfo>();
            while(cursor.moveToLast()) {
                ThreadInfo info = new ThreadInfo();
                info.setmId(cursor.getInt(cursor.getColumnIndex(TableThreadInfoColumn.THREAD_ID)));

                FileInfor fileInfor = new FileInfor();
                fileInfor.setmUrl(cursor.getString(cursor.getColumnIndex(TableThreadInfoColumn.FILE_URL)));
                fileInfor.setmId(cursor.getInt(cursor.getColumnIndex(
                        TableThreadInfoColumn.FILE_ID
                )));
                info.setmFile(fileInfor);
                info.setmStart(cursor.getInt(cursor.getColumnIndex(TableThreadInfoColumn.FILE_START_INDEX)));
                info.setmEnd(cursor.getInt(cursor.getColumnIndex(TableThreadInfoColumn.FILE_END_INDEX)));
                info.setmLengthDownload(cursor.getInt(cursor.getColumnIndex(TableThreadInfoColumn.FILE_DOWNLOAD_LENGTH)));
                threadInfosList.add(info);
            }
            return threadInfosList;
        }

        @Override
        public boolean isEntityExist(String url, int id) {
            return false;
        }
    }

    public static class FileInforDAOImpl implements  EntityDAO<FileInfor> {
        private    DatabaseHelper mDBHelper;
        private FileInforDAOImpl(Context context) {
            mDBHelper = new DatabaseHelper(context);
        }

        @Override
        public long insertEntity(FileInfor entity) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            String [] entityValues = new String[] {entity.getmUrl(),entity.getmFileName(),
                    entity.getmLength() + ""};

            long count = TableFileInforColumn.valueInsertSqlStatement(db, entityValues )
                    .executeInsert();
            db.close();
            return count;
        }

        @Override
        public long deleteEntity(FileInfor entity) {
            return 0;
        }

        @Override
        public long updateEntity(FileInfor entity) {
            return 0;
        }

        @Override
        public List<FileInfor> getEntities(String url) {
            return null;
        }

        @Override
        public boolean isEntityExist(String url, int id) {
            return false;
        }
    }
}
