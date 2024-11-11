package com.cookandroid.test_ui.DTO.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthReqLoginDto {
    @Expose
    @SerializedName("member_id") private Integer memberId;
    @SerializedName("pin_num") private Integer pinNum;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getPinNum() {
        return pinNum;
    }

    public void setPinNum(Integer pinNum) {
        this.pinNum = pinNum;
    }
}
