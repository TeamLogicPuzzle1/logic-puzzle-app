package com.cookandroid.test_ui.DTO.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoDto {
    @Expose
    @SerializedName("id") private Integer userId;
    @SerializedName("profile_name") private String profileName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
