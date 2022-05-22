package com.example.daftarpencarianorang.Model;

import android.net.Uri;

public class ImageDetailFile {
    private String mName;
    private Uri mImageUri;

    public ImageDetailFile() {
    }

    public ImageDetailFile(String name, Uri imageUri) {
        mName = name;
        mImageUri = imageUri;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Uri getImageUri() {
        return mImageUri;
    }

    public void setImageUri(Uri imageUri) {
        mImageUri = imageUri;
    }
}
