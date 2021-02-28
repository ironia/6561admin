package kr.co.administrator6561;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {
    @GET("/login/main")
    Call<Login> main_login(@Query("user_id") String user_id, @Query("user_pw") String user_pw);
    @GET("/login/city")
    Call<Login> city_login(@Query("user_id") String city_id, @Query("user_pw") String city_pw);
    @GET("/login/shop")
    Call<Login> shop_login(@Query("city_code") Integer city_code, @Query("user_id") String shop_id, @Query("user_pw") String shop_pw);
    @GET("/login/main/insert")
    Call<ResultUser> city_insert(@Query("user_id") String city_id);
    @GET("/login/main/delete")
    Call<ResultUser> city_delete(@Query("user_id") String city_id);
    @GET("/login/main/show")
    Call<GetCityList> get_city_users();
    @GET("/login/city/show")
    Call<GetShopList> get_shop_users(@Query("city_code") Integer city_code);
    @GET("/login/city/insert")
    Call<ResultUser> shop_insert(@Query("user_id") String user_id, @Query("city_code") String city_code);
    @GET("/login/city/delete")
    Call<ResultUser> shop_delete(@Query("shop_code") String shop_code, @Query("user_id") String user_id, @Query("city_code") String city_code);

    @GET("/login/modify")
    Call<ResultUser> modify_city_user(@Query("city_code") String city_code, @Query("user_id") String user_id, @Query("user_pw") String user_pw, @Query("user_new_pw") String user_new_pw, @Query("level") String level);
    @GET("/login/modify")
    Call<ResultUser> modify_main_user(@Query("user_id") String user_id, @Query("user_pw") String user_pw, @Query("user_new_pw") String user_new_pw, @Query("level") String level);
    @GET("/login/modify")
    Call<ResultUser> modify_shop_user(@Query("city_code") String city_code, @Query("user_id") String user_id, @Query("user_pw") String user_pw, @Query("user_new_pw") String user_new_pw, @Query("level") String level);

}
