package kr.co.administrator6561;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    Retrofit retrofit;
    Spinner spinner;
    String level;
    int city_code;
    Button login_button;
    TextInputEditText idEdit;
    TextInputEditText pwEdit;
    TextInputEditText cityCodeEdit;
    TextInputLayout city_codeLayout;
    TextInputLayout passwordInputLayout;
    InputMethodManager im;
    @Override
    public void onBackPressed() {
        //안전한 종료 버튼

        AlertDialog.Builder programEndBuilder = new AlertDialog.Builder(this);
        programEndBuilder.setTitle(R.string.exit);
        programEndBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        programEndBuilder.setMessage(R.string.exitMainMessage);
        programEndBuilder.setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        programEndBuilder.setPositiveButton(R.string.cancel, null);
        programEndBuilder.show();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://mytownadmin.cafe24app.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            LoginService loginService = retrofit.create(LoginService.class);

            spinner = findViewById(R.id.select_level);
        ArrayList<String> levelList = new ArrayList<>();
        levelList.add("본사");
        levelList.add("지점");
        levelList.add("가맹점");
        ArrayAdapter selectLevel = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,levelList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v =  super.getView(position,convertView,parent);
                ((TextView)v).setTextSize(16);
                return v;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = getView(position,convertView,parent);
                ((TextView)v).setGravity(Gravity.CENTER);
                return v;
            }
        };
        city_codeLayout = findViewById(R.id.TextInputLayout_city);
        cityCodeEdit = findViewById(R.id.input_city_code);
        spinner.setAdapter(selectLevel);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level = spinner.getSelectedItem().toString();
                if(position==2){
                    city_codeLayout.setVisibility(View.VISIBLE);
                    cityCodeEdit.requestFocus();
                }else{
                    city_codeLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                level = "0";
            }
        }));

        idEdit = findViewById(R.id.input_user_id);
        pwEdit = findViewById(R.id.input_user_pw);

        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(view -> {
            String user_id= "";
            String user_pw = "";
            switch (spinner.getSelectedItem().toString()){
                case "본사":


                    if(idEdit.getText()!=null){
                        user_id = idEdit.getText().toString();}
                    Log.d("Main","user_id :"+user_id);
                    if(pwEdit.getText()!=null)
                        user_pw = pwEdit.getText().toString();
                    Log.d("Main","user_pw :"+user_pw);


                    Call<Login> login = loginService.main_login(user_id, user_pw);
                    String finalUser_id = user_id;
                    login.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            Log.d("main","ok");

                                Login login_respond = response.body();
                                if(login_respond!=null) {
                                    if(login_respond.getResult().equals("true")) {

                                        level = login_respond.getLevel().toString();

                                                Intent main_intent = new Intent(MainActivity.this,MainUserActivity.class);
                                                main_intent.putExtra("level",level);
                                                main_intent.putExtra("user_id", finalUser_id);
                                        im.hideSoftInputFromWindow(pwEdit.getWindowToken(), 0);
                                        startActivity(main_intent);
                                        }else{
                                        Log.d("main","fail");
                                        Toast toast = Toast.makeText(getApplicationContext(),R.string.login_fail,Toast.LENGTH_LONG);
                                        toast.show();
                                        pwEdit.setText("");
                                        im.hideSoftInputFromWindow(pwEdit.getWindowToken(), 0);


                                    }
                                        //로그인 다음 으로 이동
                                    }else{
                                        //오류 메세지 송출
                                    Log.d("main","error");
                                    }


                            }


                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            // 전송 오류
                            Log.d("Main", t+"잘못됨.");
                            //

                        }
                    });
                    break;
                case "지점" :

                    if(idEdit.getText()!=null){
                        user_id = idEdit.getText().toString();}
                    Log.d("Main","user_id :"+user_id);
                    if(pwEdit.getText()!=null)
                        user_pw = pwEdit.getText().toString();

                    Call<Login> cityLogin = loginService.city_login(user_id, user_pw);

                    String finalUser_id1 = user_id;
                    cityLogin.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if(response.isSuccessful()){
                                Login login_respond = response.body();
                                if(login_respond!=null) {
                                    if(login_respond.getResult().equals("true")) {
                                        level = login_respond.getLevel().toString();
                                                Intent city_intent = new Intent(MainActivity.this,CityUserActivity.class);
                                                city_intent.putExtra("level",level);
                                                city_intent.putExtra("user_id", login_respond.getUserId());
                                                city_intent.putExtra("city_code",login_respond.getCityCode());
                                                startActivity(city_intent);

                                        //로그인 다음 으로 이동
                                    }else{
                                        //오류 메세지 송출
                                        Toast toast = Toast.makeText(getApplicationContext(),R.string.login_fail,Toast.LENGTH_LONG);
                                        toast.show();
                                        pwEdit.setText("");
                                        im.hideSoftInputFromWindow(pwEdit.getWindowToken(), 0);
                                    }

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            // 전송 오류
                            Log.d("Main", "로그인 콜 오류");
                            //

                        }
                    });
                    break;
                case "가맹점" :
                    if(idEdit.getText()!=null){
                        user_id = idEdit.getText().toString();}
                    Log.d("Main","user_id :"+user_id);
                    if(pwEdit.getText()!=null)
                        user_pw = pwEdit.getText().toString();
                    if(cityCodeEdit.getText()!=null)
                    city_code = Integer.parseInt(cityCodeEdit.getText().toString());
                    Call<Login> shop_login = loginService.shop_login(city_code, user_id, user_pw);
                    shop_login.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if(response.isSuccessful()){
                                Log.d("main","여기?");
                                Login login_respond = response.body();
                                if(login_respond!=null) {
                                    if(login_respond.getResult().equals("true")) {
                                        level = login_respond.getLevel().toString();

                                        Intent shop_intent = new Intent(MainActivity.this,ShopUserActivity.class);
                                        shop_intent.putExtra("level",level);
                                        shop_intent.putExtra("user_id", login_respond.getUserId());
                                        shop_intent.putExtra("city_code",login_respond.getCityCode());
                                        shop_intent.putExtra("shop_code",login_respond.getShopCode());
                                        startActivity(shop_intent);
                                        //로그인 다음 으로 이동
                                    }else{
                                        //오류 메세지 송출
                                        Toast toast = Toast.makeText(getApplicationContext(),R.string.login_fail,Toast.LENGTH_LONG);
                                        toast.show();
                                        pwEdit.setText("");
                                        im.hideSoftInputFromWindow(pwEdit.getWindowToken(), 0);
                                    }

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            // 전송 오류
                            Log.d("Main", "로그인 콜 오류");
                            //

                        }
                    });
                    break;
                default:
                    break;
            }
        });
    }


}