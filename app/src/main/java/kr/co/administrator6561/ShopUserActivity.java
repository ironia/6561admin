package kr.co.administrator6561;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class ShopUserActivity extends AppCompatActivity {

ConstraintLayout idLayoutShop;
ConstraintLayout codeLayoutShop;
RadioGroup checkboxShop;
TextInputEditText currentPasswordInputShop;
TextInputEditText newPasswordInputShop;
TextInputEditText newPasswordCheckInputShop;

Button idSendButtonShop;
Button codeCheckButtonShop;
RecyclerView codeListRecyclerviewShop;

Retrofit retrofit;

String level;
String cityCode;
String shopCode;
String userId;


    @Override
    public void onBackPressed() {
        //안전한 종료 버튼

        AlertDialog.Builder programEndBuilder = new AlertDialog.Builder(this);
        programEndBuilder.setTitle(R.string.exit);
        programEndBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        programEndBuilder.setMessage(R.string.exitMessage);
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
        setContentView(R.layout.activity_shop_user);

        Intent intent = getIntent();
        level = intent.getStringExtra("level");
        cityCode = intent.getStringExtra("city_code");
        shopCode = intent.getStringExtra("shop_code");
        userId = intent.getStringExtra("user_id");
        idLayoutShop = findViewById(R.id.id_layout_shop);
        codeLayoutShop = findViewById(R.id.code_checked_layout_shop);
        checkboxShop = findViewById(R.id.checkbox_shop);
        currentPasswordInputShop = findViewById(R.id.current_password_input_shop);
        newPasswordInputShop = findViewById(R.id.new_password_input_shop);
        newPasswordCheckInputShop = findViewById(R.id.new_password_check_input_shop);
        codeListRecyclerviewShop = findViewById(R.id.code_list_recyclerview_shop);
        codeListRecyclerviewShop.setLayoutManager(new LinearLayoutManager(this));
        MyDecoration decoration1 = new MyDecoration();
        codeListRecyclerviewShop.addItemDecoration(decoration1);
        idSendButtonShop = findViewById(R.id.id_select_button_shop);
        codeCheckButtonShop = findViewById(R.id.code_send_button_shop);
        //레트로핏 기능 구현
        retrofit = new Retrofit.Builder()
                .baseUrl("http://mytownadmin.cafe24app.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService loginService = retrofit.create(LoginService.class);
        CodeService codeService = retrofit.create(CodeService.class);


        checkboxShop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.id_select_button_shop :
                        idLayoutShop.setVisibility(View.VISIBLE);
                        codeLayoutShop.setVisibility(View.GONE);
                        codeListRecyclerviewShop.setVisibility(View.GONE);
                        break;
                    case R.id.code_list_button_shop :
                        idLayoutShop.setVisibility(View.GONE);
                        codeLayoutShop.setVisibility(View.VISIBLE);
                        codeListRecyclerviewShop.setVisibility(View.VISIBLE);
                        MakeCodeList(codeService);
                        break;
                }
            }
        });
        codeCheckButtonShop.setOnClickListener(view -> {
            MakeCodeList(codeService);
        });
        idSendButtonShop.setOnClickListener(view -> {
            //modify(기능 넣기)
            String currentPw="";
            String newPw="";
            String newCheckPw="";
           if (currentPasswordInputShop.getText()!=null){
               currentPw = currentPasswordInputShop.getText().toString();
            }
            if(newPasswordInputShop.getText()!=null){
                newPw = newPasswordInputShop.getText().toString();
            }
            if(newPasswordCheckInputShop.getText()!=null){
             newCheckPw = newPasswordCheckInputShop.getText().toString();
            }
            //변경 실행
            if(newPw.equals(newCheckPw) && newPw.equals("")) {
                ModifyPw(loginService, currentPw, newPw, level);
            }else{
                Log.d("shop", "password wrong");
            }

        });





    }
    public  void MakeCodeList(CodeService codeService){
        Log.d("start", "MakeCodeList");
        Call<GetCodeList> codeListCall = codeService.code_shop_show(level,cityCode,shopCode);
        codeListCall.enqueue(new Callback<GetCodeList>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<GetCodeList> call, Response<GetCodeList> response) {
                if(response.isSuccessful()){
                    Log.d("city","MakeCodeList Success");
                    GetCodeList codes = response.body();
                    if(codes!=null) {
                        List<CodeList> codeLists = codes.getUser();
                        MakeCodeListAdapter codeListAdapter = new MakeCodeListAdapter(codeLists);
                        codeListRecyclerviewShop.setAdapter(codeListAdapter);
                    }
                    //showListRecyclerView.setAdapter(codeListAdapter);
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<GetCodeList> call, Throwable t) {
                Log.d("city",t+"");
            }
        });

    }
    static class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            ViewCompat.setElevation(view, 5.0f);
            outRect.set(10, 10, 10, 10);
        }
    }


    public class MakeCodeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<CodeList> codeLists;
        private MakeCodeListAdapter(List<CodeList> codes) {
            this.codeLists = codes;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ShopUserActivity.this).inflate(R.layout.code_list_city, parent, false);
            return new MakeCodeListAdapter.CodeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            CodeList code = codeLists.get(position);
            //Log.d("main",code.getId()+"");
            MakeCodeListAdapter.CodeHolder codeHolder = (MakeCodeListAdapter.CodeHolder) holder;
            codeHolder.codeListId.setText(code.getId());
            codeHolder.codeListCode.setText(code.getCode());
            codeHolder.codeListCityCode.setText(code.getCityCode());
            codeHolder.codeListShopCode.setText(code.getShopCode());
            codeHolder.codeListUsed.setText(code.getUsed());

            if(!code.getLp().equals("null")){
                codeHolder.codeListLp.setText(code.getLp());
            }else{
                codeHolder.codeListLp.setText("0");
            }

        }


        @Override
        public int getItemCount() {
            return codeLists.size();
        }

        private class CodeHolder extends RecyclerView.ViewHolder {
            TextView codeListId;
            TextView codeListCode;
            TextView codeListCityCode;
            TextView codeListShopCode;
            TextView codeListUsed;
            TextView codeListLp;
            private CodeHolder(View itemView) {
                super(itemView);
                codeListId = itemView.findViewById(R.id.code_list_id);
                codeListCode = itemView.findViewById(R.id.code_list_code);
                codeListCityCode = itemView.findViewById(R.id.code_list_city_code);
                codeListShopCode = itemView.findViewById(R.id.code_list_shop_code);
                codeListUsed = itemView.findViewById(R.id.code_list_used);
                codeListLp = itemView.findViewById(R.id.code_list_lp);

            }
        }

    }

    public void ModifyPw(LoginService loginService, String curPw, String newPw, String level){
        Log.d("shop",cityCode);
        Log.d("shop",userId);
        Log.d("shop",curPw);
        Log.d("shop",newPw);
        Log.d("shop",level);


        Call<ResultUser> result = loginService.modify_shop_user(cityCode, userId,curPw,newPw,level);
        result.enqueue(new Callback<ResultUser>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                ResultUser result = response.body();
                try{
                    assert result != null;
                    Log.d("shop", result.getResult() + "");
                }catch (NullPointerException e){
                    Log.d("shop","result code = > null");
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResultUser> call, Throwable t) {
                Log.d("shop",t+"");
            }
        });
    }

}