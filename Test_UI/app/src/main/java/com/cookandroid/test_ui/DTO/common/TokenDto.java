package com.cookandroid.test_ui.DTO.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenDto {
    @Expose
    @SerializedName("access") private Object access;
    @SerializedName("refresh") private Object refresh;

    public TokenDto(String refresh) {
        this.refresh = refresh;
    }

    public Object getAccess() {
        return access;
    }

    public void setAccess(Object access) {
        this.access = access;
    }

    public Object getRefresh() {
        return refresh;
    }

    public void setRefresh(Object refresh) {
        this.refresh = refresh;
    }
}
