package kr.co.administrator6561;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodeList {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("city_code")
    @Expose
    private String cityCode;
    @SerializedName("shop_code")
    @Expose
    private String shopCode;
    @SerializedName("used")
    @Expose
    private Integer used;
    @SerializedName("lp")
    @Expose
    private Integer lp;

    public String getId() {
        return id+"";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getUsed() {
        return used+"";
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public String getLp() {
        if(lp==null){
            return "0";
        }else{
            return lp+"";
        }
    }

    public void setLp(Integer lp) {
        this.lp = lp;
    }
}
