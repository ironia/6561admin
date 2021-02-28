package kr.co.administrator6561;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityUsersList {

    @SerializedName("city_code")
    @Expose
    private Integer cityCode;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_pw")
    @Expose
    private String userPw;

    public String getCityCode() {
        return cityCode+"";
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

}