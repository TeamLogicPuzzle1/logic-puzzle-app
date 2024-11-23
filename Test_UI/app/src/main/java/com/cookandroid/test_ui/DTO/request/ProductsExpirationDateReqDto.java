package com.cookandroid.test_ui.DTO.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProductsExpirationDateReqDto {
    @Expose
    @SerializedName("image")
    private MultipartBody.Part imagePart;

    public MultipartBody.Part getImagePart() {
        return imagePart;
    }

    public void setImagePart(File imageFile) {
        if (imageFile != null && imageFile.exists()) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            this.imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
        } else {
            throw new IllegalArgumentException("Invalid file: File does not exist or is null");
        }
    }
}
