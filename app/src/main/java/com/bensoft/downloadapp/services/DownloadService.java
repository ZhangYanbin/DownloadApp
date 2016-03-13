package com.bensoft.downloadapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bensoft.downloadapp.entities.FileInfor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_START = "com.bensoft.downloadapp.services.action.start";
    private static final String ACTION_STOP = "com.bensoft.downloadapp.services.action.stop";
    private static final String DOWNLOAD_DIRECTORY_NAME = "/Download/";
    private static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOAD_DIRECTORY_NAME;

    private static final int MSG_INIT = 0X01;

    private static final String EXTRA_FILEINFOR = "com.bensoft.downloadapp.services.extra.FILEINFOR";
    private static final String EXTRA_PARAM2 = "com.bensoft.downloadapp.services.extra.PARAM2";
    private static final String TAG = "DownloadService";

    public DownloadService() {
        super(TAG);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startFileDownloadAction(Context context, FileInfor fileInfor, String param2) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_START);
        intent.putExtra(EXTRA_FILEINFOR, fileInfor);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void stopFileDownloadAction(Context context, FileInfor fileInfor, String param2) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_STOP);
        intent.putExtra(EXTRA_FILEINFOR, fileInfor);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                final FileInfor fileInfor = (FileInfor) intent.getSerializableExtra(EXTRA_FILEINFOR);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleStartFileDownloadAction(fileInfor, param2);
            } else if (ACTION_STOP.equals(action)) {
                final FileInfor fileInfor = (FileInfor) intent.getSerializableExtra(EXTRA_FILEINFOR);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleStopFileDownloadAction(fileInfor, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleStartFileDownloadAction(FileInfor infor, String param2) {
        Log.e(TAG, "handle start file download action for : \n" + infor.toString());
        new InitThread(infor).start();
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleStopFileDownloadAction(FileInfor infor, String param2) {
        Log.e(TAG, "handle stop file download action for : \n" + infor.toString());
    }

    Handler mMainHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfor fileInfor = (FileInfor) msg.obj;
                    Log.e(TAG, "init file + \n" + fileInfor.toString());
                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };

    /**
     * init thread
     */
    class InitThread extends Thread {
        FileInfor mFileinfor = null;

        public InitThread(FileInfor fileinfor) {
            mFileinfor = fileinfor;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            try {
                URL url = new URL(mFileinfor.getmUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(1000);
                connection.setRequestMethod("GET");

                int fileLength = -1;
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    fileLength = connection.getContentLength();
                }

                if (fileLength <= 0) {
                    return;
                }

                File downloadDirectory = new File(DOWNLOAD_PATH);
                if (!downloadDirectory.exists()) {
                    downloadDirectory.mkdir();
                }

                File downloadFile = new File(downloadDirectory, mFileinfor.getmFileName());
                if (!downloadFile.exists()) {
                    downloadFile.createNewFile();
                }

                randomAccessFile = new RandomAccessFile(downloadFile, "rwd");
                randomAccessFile.setLength(fileLength);
                mFileinfor.setmLength(fileLength);
                mMainHanlder.obtainMessage(MSG_INIT, mFileinfor).sendToTarget();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                try {
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            super.run();
        }
    }

}
