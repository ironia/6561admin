package kr.co.administrator6561;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CodeService {
    @GET("/code/show")
    Call<GetCodeList> code_main_show(@Query("level") String level);
    @GET("/code/main/give")
    Call<ResultUser> code_main_give(@Query("min_id") int min, @Query("max_id") int max, @Query("city_code") String city_code);
    @GET("/code/show")
    Call<GetCodeList> code_city_show(@Query("level") String level, @Query("city_code") String city_code);
    @GET("/code/city/give")
    Call<ResultUser> code_city_give(@Query("min_id") int min, @Query("max_id") int max, @Query("shop_code") String shop_code);
    @GET("/code/show")
    Call<GetCodeList> code_shop_show(@Query("level") String level, @Query("city_code") String city_code, @Query("shop_code") String shop_code);
}
