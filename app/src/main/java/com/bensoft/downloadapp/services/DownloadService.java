package com.bensoft.downloadapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.bensoft.downloadapp.entities.FileInfor;

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

    // TODO: Rename parameters
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
    // TODO: Customize helper method
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
    // TODO: Customize helper method
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
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleStopFileDownloadAction(FileInfor infor, String param2) {
        Log.e(TAG, "handle stop file download action for : \n" + infor.toString());
    }
}
