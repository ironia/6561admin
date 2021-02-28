package kr.co.administrator6561;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultUser {

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("city_code")
    @Expose
    private String cityCode;
    @SerializedName("shop_code")
    @Expose
    private String shopCode;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
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

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
}
