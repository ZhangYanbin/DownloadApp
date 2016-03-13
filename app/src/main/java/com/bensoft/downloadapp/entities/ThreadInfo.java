package com.bensoft.downloadapp.entities;

/**
 * Created by 炎宾 on 2016/3/11.
 */
public class ThreadInfo implements Entity {
    private int mId;
    private FileInfor mFile;
    private int mStart;
    private int mEnd;
    private int mLengthDownload;

    public ThreadInfo() {
    }

    public ThreadInfo(int mId, FileInfor mFile, int mStart, int mEnd, int mLengthDownload) {

        this.mId = mId;
        this.mFile = mFile;
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mLengthDownload = mLengthDownload;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public FileInfor getmFile() {
        return mFile;
    }

    public void setmFile(FileInfor mFile) {
        this.mFile = mFile;
    }

    public int getmStart() {
        return mStart;
    }

    public void setmStart(int mStart) {
        this.mStart = mStart;
    }

    public int getmEnd() {
        return mEnd;
    }

    public void setmEnd(int mEnd) {
        this.mEnd = mEnd;
    }

    public int getmLengthDownload() {
        return mLengthDownload;
    }

    public void setmLengthDownload(int mLengthDownload) {
        this.mLengthDownload = mLengthDownload;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "mId=" + mId +
                ", mFile=" + mFile +
                ", mStart=" + mStart +
                ", mEnd=" + mEnd +
                ", mLengthDownload=" + mLengthDownload +
                '}';
    }
}
