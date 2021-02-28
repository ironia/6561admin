package kr.co.administrator6561;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ResetService {
    @GET("/users/reset/")
    Call<ResultUser> reset_users(@Query("password") String password);
    @GET("/codes/reset")
    Call<ResultUser> reset_codes(@Query("pw") String password);
}
