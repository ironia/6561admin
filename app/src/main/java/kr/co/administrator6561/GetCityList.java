package kr.co.administrator6561;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCityList {
    @SerializedName("city_users")
    @Expose
    private List<CityUsersList> user = null;

    public List<CityUsersList> getUser() {
        return user;
    }

    public void setUser(List<CityUsersList> user) {
        this.user = user;
    }
}
