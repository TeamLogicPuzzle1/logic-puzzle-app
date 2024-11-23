package com.cookandroid.test_ui.DTO.response;

import com.cookandroid.test_ui.DTO.common.TokenDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResLoginDto {
    @Expose
    @SerializedName("user") private UserInfoDto userInfoDto;
    @SerializedName("token") private TokenDto tokenDto;

    public UserInfoDto getUserInfoDto() {
        return userInfoDto;
    }

    public void setUserInfoDto(UserInfoDto userInfoDto) {
        this.userInfoDto = userInfoDto;
    }

    public TokenDto getTokenDto() {
        return tokenDto;
    }

    public void setTokenDto(TokenDto tokenDto) {
        this.tokenDto = tokenDto;
    }
}
