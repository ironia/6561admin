package kr.co.administrator6561;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class Login {

        @SerializedName("result")
        @Expose
        private String result;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("city_code")
        @Expose
        private String cityCode;
        @SerializedName("shop_code")
        @Expose
        private String shopCode;
        @SerializedName("user_id")
        @Expose
        private String userId;


        public String getShopCode() {
            return shopCode;
        }

        public void setShopCode(String shopCode) {
            this.shopCode = shopCode;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

    }

