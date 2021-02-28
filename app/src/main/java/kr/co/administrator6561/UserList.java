package kr.co.administrator6561;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserList {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("city_code")
    @Expose
    private int cityCode;

    @SerializedName("user_pw")
    @Expose
    private String userPw;

    @SerializedName("shop_code")
    @Expose
    private int shopCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public int getShopCode() {
        return shopCode;
    }

    public void setShopCode(int shopCode) {
        this.shopCode = shopCode;
    }
}
