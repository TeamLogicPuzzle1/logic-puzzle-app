package com.cookandroid.test_ui.DTO.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSignupDto {
    @Expose
    @SerializedName("user_id") private String userId;   // 사용자 아이디
    @SerializedName("id_check") private boolean idCheck;    // 아이디 중복 체크
    @SerializedName("user_id") private String password;     // 비밀번호
    @SerializedName("user_id") private String passwordCheck;    // 비밀번호 확인
    @SerializedName("email") private String email;              // 이메일
    @SerializedName("verify_check") private boolean verifyCheck;    // 중복체크

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isIdCheck() {
        return idCheck;
    }

    public void setIdCheck(boolean idCheck) {
        this.idCheck = idCheck;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerifyCheck() {
        return verifyCheck;
    }

    public void setVerifyCheck(boolean verifyCheck) {
        this.verifyCheck = verifyCheck;
    }
}
