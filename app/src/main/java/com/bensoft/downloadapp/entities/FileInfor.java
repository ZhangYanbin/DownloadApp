package com.bensoft.downloadapp.entities;

import java.io.Serializable;

/**
 * File information container
 * Created by 炎宾 on 2016/3/11.
 */
public class FileInfor implements Serializable {
    private int mId;
    private String mUrl;
    private long mLength;
    private String mFileName;
    private int mProgress;

    public FileInfor() {
    }

    /**
     * FileInfo constructor
     *
     * @param mId       id of this file in database;
     * @param mUrl      URL for this file in server
     * @param mFileName name of this file
     * @param mLength   length of this file
     * @param mProgress currently download progress,
     */
    public FileInfor(int mId, String mUrl, String mFileName, long mLength, int mProgress) {
        this.mProgress = mProgress;
        this.mId = mId;
        this.mUrl = mUrl;
        this.mLength = mLength;
        this.mFileName = mFileName;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public long getmLength() {
        return mLength;
    }

    public void setmLength(long mLength) {
        this.mLength = mLength;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public int getmProgress() {
        return mProgress;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    @Override
    public String toString() {
        return "FileInfor{" +
                "mId=" + mId +
                ", mUrl='" + mUrl + '\'' +
                ", mLength=" + mLength +
                ", mFileName='" + mFileName + '\'' +
                ", mProgress=" + mProgress +
                '}';
    }
}
