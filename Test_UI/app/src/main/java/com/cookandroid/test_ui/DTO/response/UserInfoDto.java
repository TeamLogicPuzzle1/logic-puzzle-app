package com.cookandroid.test_ui.DTO.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoDto {
    @Expose
    @SerializedName("id") private Integer id;
    @SerializedName("user_id") private String userId;
    @SerializedName("profile_name") private String profileName;
    @SerializedName("leaderYn") private Boolean leaderYn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Boolean getLeaderYn() {
        return leaderYn;
    }

    public void setLeaderYn(Boolean leaderYn) {
        this.leaderYn = leaderYn;
    }
}
