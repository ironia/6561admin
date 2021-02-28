package kr.co.administrator6561;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class CityUserActivity extends AppCompatActivity {
    String userId;
    String level;
    InputMethodManager im;
    RadioGroup checkbox_main;
    RadioButton idInsert;
    RadioButton codeList;
    ConstraintLayout idCheckedLayout;
    ConstraintLayout codeCheckedLayout;
    RadioGroup checkIdManager;
    ConstraintLayout insertLayout;
    ConstraintLayout modifyLayout;
    ConstraintLayout deleteLayout;
    //insert text input
    TextInputEditText insertIdInput;
    //modify text input
    TextInputEditText currentPasswordInput;
    TextInputEditText newPasswordInput;
    TextInputEditText newPasswordCheckInput;

    TextInputEditText mainMinInput;
    TextInputEditText mainMaxInput;
    TextInputEditText mainCityCodeInput;


    TextInputEditText deleteCodeInput;
    TextInputEditText deleteIdInput;

    CardView codeLabel;

    Button idSendButton;
    Button codeSendButton;
    Retrofit retrofit;
    String city_code;
    RecyclerView idListRecyclerView;
    RecyclerView codeListRecyclerView;
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
        setContentView(R.layout.activity_city_user);
        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        city_code = intent.getStringExtra("city_code");
        level = intent.getStringExtra("level");
        checkIdManager = findViewById(R.id.check_id_manager_city);
        insertLayout = findViewById(R.id.id_insert_layout_city);
        modifyLayout = findViewById(R.id.id_modify_layout_city);
        deleteLayout = findViewById(R.id.id_delete_layout_city);
        idListRecyclerView = findViewById(R.id.id_list_recyclerview_city);
        codeListRecyclerView = findViewById(R.id.code_list_recyclerview_city);
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        idSendButton = findViewById(R.id.id_send_button_city);
        codeSendButton = findViewById(R.id.code_send_button_city);
        insertIdInput = findViewById(R.id.insert_id_input_city);
        checkbox_main = findViewById(R.id.checkbox_city);
        idInsert = findViewById(R.id.id_select_button_city);
        codeList = findViewById(R.id.code_list_button_city);
        currentPasswordInput = findViewById(R.id.current_password_input_city);
        newPasswordInput = findViewById(R.id.new_password_input_city);
        newPasswordCheckInput = findViewById(R.id.new_password_check_input_city);
        idCheckedLayout = findViewById(R.id.id_checked_layout_city);
        codeCheckedLayout = findViewById(R.id.code_checked_layout_city);
        deleteCodeInput = findViewById(R.id.delete_code_input_city);
        deleteIdInput = findViewById(R.id.delete_id_input_city);
        mainMinInput = findViewById(R.id.min_input_city);
        mainMaxInput = findViewById(R.id.max_input_city);
        mainCityCodeInput = findViewById(R.id.code_input_city);
        codeLabel = findViewById(R.id.code_label_city);
        MyDecoration decoration = new MyDecoration();
        idListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        idListRecyclerView.addItemDecoration(decoration);
        codeListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        codeListRecyclerView.addItemDecoration(decoration);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://mytownadmin.cafe24app.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService loginService = retrofit.create(LoginService.class);
        CodeService codeService = retrofit.create(CodeService.class);
        makeIdList(loginService);

        //CodeService codeService = retrofit.create(CodeService.class);
        checkbox_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.id_select_button_city:
                        //select id;
                        idCheckedLayout.setVisibility(View.VISIBLE);
                        codeCheckedLayout.setVisibility(View.GONE);
                        idListRecyclerView.setVisibility(View.VISIBLE);
                        codeLabel.setVisibility(View.GONE);
                        codeListRecyclerView.setVisibility(View.GONE);
                        makeIdList(loginService);
                        break;
                    case R.id.code_list_button_city:
                        //select code;
                        codeCheckedLayout.setVisibility(View.VISIBLE);
                        idCheckedLayout.setVisibility(View.GONE);
                        idListRecyclerView.setVisibility(View.GONE);
                        codeLabel.setVisibility(View.VISIBLE);
                        codeListRecyclerView.setVisibility(View.VISIBLE);
                        MakeCodeList(codeService);
                        break;
                }

            }
        });



        checkIdManager.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.insert_id_city:
                        Log.d("city", "insert_id");
                        insertLayout.setVisibility(View.VISIBLE);
                        modifyLayout.setVisibility(View.GONE);
                        deleteLayout.setVisibility(View.GONE);
                        idListRecyclerView.setVisibility(View.VISIBLE);
                        codeListRecyclerView.setVisibility(View.GONE);
                        makeIdList(loginService);
                        break;
                    case R.id.modify_pw_city:
                        insertLayout.setVisibility(View.GONE);
                        modifyLayout.setVisibility(View.VISIBLE);
                        deleteLayout.setVisibility(View.GONE);
                        idListRecyclerView.setVisibility(View.VISIBLE);
                        codeListRecyclerView.setVisibility(View.GONE);

                        break;
                    case R.id.delete_id_city:
                        insertLayout.setVisibility(View.GONE);
                        modifyLayout.setVisibility(View.GONE);
                        deleteLayout.setVisibility(View.VISIBLE);
                        idListRecyclerView.setVisibility(View.VISIBLE);
                        codeListRecyclerView.setVisibility(View.GONE);
                        makeIdList(loginService);
                        break;
                }
            }
        });
        idSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (checkIdManager.getCheckedRadioButtonId()){
                    case R.id.insert_id_city:
                        //삽입 구문
                        //adapter = new FindUserAdapter(user, fu);
                        //recyclerView.setAdapter(adapter);
                        String user_id;
                        if(insertIdInput.getText()!=null){
                            user_id =  insertIdInput.getText().toString();
                            InsertId(loginService,user_id);
                        }
                        insertIdInput.setText("");
                        im.hideSoftInputFromWindow(insertIdInput.getWindowToken(), 0);
                        break;
                    case R.id.modify_pw_city:
                        //수정구문
                        //adapter = new FindUserAdapter(user, fu);
                        //recyclerView.setAdapter(adapter);
                        String curPw ="";
                        String newPw ="";
                        String newCheckPw = "";

                        if(currentPasswordInput.getText()!=null)
                            curPw = currentPasswordInput.getText().toString();

                        if(newPasswordInput.getText()!=null)
                            newPw = newPasswordInput.getText().toString();

                        if(newPasswordCheckInput.getText()!=null){
                            newCheckPw = newPasswordCheckInput.getText().toString();
                        }
                        if(newPw.equals(newCheckPw)){
                        ModifyPw(loginService,curPw,newPw,level);
                        currentPasswordInput.setText("");
                        newPasswordCheckInput.setText("");
                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(),"새로운 암호를 확인해주세요.",Toast.LENGTH_LONG);
                            toast.show();
                        }
                        newPasswordInput.setText("");
                        im.hideSoftInputFromWindow(insertIdInput.getWindowToken(), 0);
                        break;
                    case R.id.delete_id_city:
                        //삭제 구문
                        //adapter = new FindUserAdapter(user, fu);
                        //recyclerView.setAdapter(adapter);
                        String code = "";
                        String deleteId = "";
                        if(deleteCodeInput.getText()!=null){
                            code =  deleteCodeInput.getText().toString();
                        }
                        if(deleteIdInput.getText()!=null){
                            deleteId = deleteIdInput.getText().toString();
                        }
                        DeleteId(loginService,code, deleteId);
                        deleteIdInput.setText("");
                        deleteCodeInput.setText("");
                        im.hideSoftInputFromWindow(insertIdInput.getWindowToken(), 0);

                        break;
                }
            }
        });
        codeSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int min = 0;
                int max = 0;
                String cityCode = "0";
                if(mainMinInput.getText()!=null) {
                    if(!mainMinInput.getText().toString().equals("")) {
                        min = Integer.parseInt(mainMinInput.getText().toString());
                    }
                }


                if(mainMaxInput.getText()!=null)
                    if(!mainMaxInput.getText().toString().equals("")) {
                        max = Integer.parseInt(mainMaxInput.getText().toString());
                    }


                if(mainCityCodeInput.getText()!=null)
                    if(!mainCityCodeInput.getText().toString().equals(""))
                        cityCode = mainCityCodeInput.getText().toString();

                if(min>0 && max>0 && !cityCode.equals(""))
                    GiveCode(codeService,min,max,cityCode);
                mainMinInput.setText("");
                mainMaxInput.setText("");
                mainCityCodeInput.setText("");
                im.hideSoftInputFromWindow(insertIdInput.getWindowToken(), 0);
            }
        });




    }
    public void GiveCode(CodeService codeService,int min, int max, String shopCode){
        Call<ResultUser> resultUserCall = codeService.code_city_give(min,max,shopCode);
        resultUserCall.enqueue(new Callback<ResultUser>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                MakeCodeList(codeService);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResultUser> call, Throwable t) {

            }
        });
    }
    public void DeleteId(LoginService loginService, String code, String deleteId){
        Call<ResultUser> result = loginService.shop_delete(code, deleteId,city_code);
        result.enqueue(new Callback<ResultUser>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                ResultUser result = response.body();
                try{
                    assert result != null;
                    Log.d("main", result.getResult() + "");
                }catch (NullPointerException e){
                    Log.d("main","result code = > null");
                }
                makeIdList(loginService);
            }
@EverythingIsNonNull
            @Override
            public void onFailure(Call<ResultUser> call, Throwable t) {

            }
        });
    }
    public void ModifyPw(LoginService loginService, String curPw, String newPw, String level){

        Call<ResultUser> result = loginService.modify_city_user(city_code, userId,curPw,newPw,level);

        result.enqueue(new Callback<ResultUser>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                ResultUser result = response.body();
                try{
                    assert result != null;
                    Log.d("city", result.getResult() + "");
                }catch (NullPointerException e){
                    Log.d("city","result code = > null");
                }

                makeIdList(loginService);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResultUser> call, Throwable t) {
                Log.d("city",t+"");
            }
        });
    }
    public void InsertId(LoginService loginService, String user_id){
        Log.d("city","insertId");
        Call<ResultUser> result = loginService.shop_insert(user_id,city_code);
        result.enqueue(new Callback<ResultUser>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                ResultUser result = response.body();
                try{
                    assert result != null;
                    Log.d("city", result.getResult() + " : 결과값?");
                }catch (NullPointerException e){
                    Log.d("city","result code = > null");
                }
                Toast toast = Toast.makeText(getApplicationContext(),"ID가 정상적으로 생성되었습니다.",Toast.LENGTH_LONG);
                toast.show();
                makeIdList(loginService);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResultUser> call, Throwable t) {
                Log.d("city",t+"");
            }
        });
    }
    public  void MakeCodeList(CodeService codeService){
        Log.d("start", "MakeCodeList");
        Call<GetCodeList> codeListCall = codeService.code_city_show(level,city_code);
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
                        codeListRecyclerView.setAdapter(codeListAdapter);
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
    public void makeIdList(LoginService loginService){
        Log.d("city","start MakeId List~");
        Log.d("city","city_code : "+city_code);

        Call<GetShopList> shopUserCall = loginService.get_shop_users(Integer.parseInt(city_code));
        shopUserCall.enqueue(new Callback<GetShopList>() {
            //ArrayList<CityList> user = new ArrayList<>();

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<GetShopList> call, Response<GetShopList> response) {
                if(response.isSuccessful()){
                    Log.d("dd","");
                    GetShopList list = response.body();
                    if(list!=null) {
                        List<ShopUsersList> shopUsersLists = list.getUser();
                        makeIdListAdapter adapter = new makeIdListAdapter(shopUsersLists);
                        idListRecyclerView.setAdapter(adapter);
                    }


                }else{
                    Log.d("city","fail.");
                }



            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<GetShopList> call, Throwable t) {
                Log.d("mainUser","error"+" : "+t);

            }
        });

    }
    static class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            ViewCompat.setElevation(view, 5.0f);
            outRect.set(20, 10, 20, 10);
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
            View view = LayoutInflater.from(CityUserActivity.this).inflate(R.layout.code_list_city, parent, false);
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
            try{
            codeHolder.codeListLp.setText(code.getLp());}catch (NullPointerException e){
                codeHolder.codeListLp.setText("");
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

    public class makeIdListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<ShopUsersList> list;

        private makeIdListAdapter(List<ShopUsersList> users) {
            this.list = users;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CityUserActivity.this).inflate(R.layout.id_list_city, parent, false);
            return new makeIdListAdapter.MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ShopUsersList user = list.get(position);
            makeIdListAdapter.MyHolder holder1 = (makeIdListAdapter.MyHolder) holder;
            holder1.city_code.setText(user.getShopCode());
            holder1.user_id.setText(user.getUserId());

        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        private class MyHolder extends RecyclerView.ViewHolder {
            TextView city_code;
            TextView user_id;
            private MyHolder(View itemView) {
                super(itemView);
                city_code = itemView.findViewById(R.id.city_code_id_list_city);
                user_id = itemView.findViewById(R.id.user_id_id_list_city);

            }
        }

    }
}