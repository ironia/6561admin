package kr.co.administrator6561;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopUsersList {


    @SerializedName("shop_code")
    @Expose
    private String shopCode;
    @SerializedName("city_code")
    @Expose
    private String cityCode;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_pw")
    @Expose
    private String userPw;

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
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