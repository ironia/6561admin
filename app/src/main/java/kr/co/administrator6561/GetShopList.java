package kr.co.administrator6561;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShopList {
    @SerializedName("shop_users")
    @Expose
    private List<ShopUsersList> user = null;

    public List<ShopUsersList> getUser() {
        return user;
    }

    public void setUser(List<ShopUsersList> user) {
        this.user = user;
    }
}
