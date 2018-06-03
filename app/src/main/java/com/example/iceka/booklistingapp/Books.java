package com.example.iceka.booklistingapp;

public class Books {

    private String mTitle;
    private String mAuthor;
    private String mPublishedDate;
    private String mImage;
    private String mSubTitle;

    public Books(String mTitle, String mAuthor, String mPublishedDate, String mImage, String mSubTitle) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mPublishedDate = mPublishedDate;
        this.mImage = mImage;
        this.mSubTitle = mSubTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmSubTitle() {
        return mSubTitle;
    }
}
