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

public class MainUserActivity extends AppCompatActivity {

    String userId;
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
    TextInputEditText findCodeInput;
    TextInputEditText mainMinInput;
    TextInputEditText mainMaxInput;
    TextInputEditText mainCityCodeInput;
String level;
    ConstraintLayout findCodeLayout;
    CardView idListLabel;
    CardView codeListLabel;

    TextInputEditText deleteCodeInput;
    TextInputEditText deleteIdInput;

    Button idSendButton;
    Button codeSendButton;
    Button codeResetButton;
    Button userResetButton;
    Retrofit retrofit;
    Retrofit resetRetrofit;
    Button findCodeButton;
    RecyclerView idListRecyclerView;
    RecyclerView codeListRecyclerView;
    @Override
    public void onBackPressed() {
        //안전한 종료 버튼

        AlertDialog.Builder programEndBuilder = new AlertDialog.Builder(this);
        programEndBuilder.setTitle(R.string.exit);
        programEndBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        programEndBuilder.setMessage(R.string.exitMessage);
        programEndBuilder.setNegativeButton(R.string.confirm, (dialogInterface, i) -> finish());
        programEndBuilder.setPositiveButton(R.string.cancel, null);
        programEndBuilder.show();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        level="1";
        findCodeLayout = findViewById(R.id.find_code);
        findCodeButton = findViewById(R.id.find_code_button);
        findCodeInput = findViewById(R.id.find_code_input);
        userResetButton = findViewById(R.id.user_reset_button);
        codeResetButton = findViewById(R.id.code_reset_button);
        codeListLabel  = findViewById(R.id.code_label_main);
        idListLabel = findViewById(R.id.id_list_label_main);
        checkIdManager = findViewById(R.id.check_id_manager);
        insertLayout = findViewById(R.id.id_insert_layout);
        modifyLayout = findViewById(R.id.id_modify_layout);
        deleteLayout = findViewById(R.id.id_delete_layout);
        idListRecyclerView = findViewById(R.id.id_list_recyclerview);
        codeListRecyclerView = findViewById(R.id.code_list_recyclerview);
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        idSendButton = findViewById(R.id.id_send_button);
        codeSendButton = findViewById(R.id.code_send_button);
        insertIdInput = findViewById(R.id.insert_id_input);
        checkbox_main = findViewById(R.id.checkbox_main);
        idInsert = findViewById(R.id.id_select_button);
        codeList = findViewById(R.id.code_list_button);
        currentPasswordInput = findViewById(R.id.current_password_input);
        newPasswordInput = findViewById(R.id.new_password_input);
        newPasswordCheckInput = findViewById(R.id.new_password_check_input);
        idCheckedLayout = findViewById(R.id.id_checked_layout);
        codeCheckedLayout = findViewById(R.id.code_checked_layout);
        deleteCodeInput = findViewById(R.id.delete_code_input);
        deleteIdInput = findViewById(R.id.delete_id_input);
        mainMinInput = findViewById(R.id.main_min_input);
        mainMaxInput = findViewById(R.id.max_input_city);
        mainCityCodeInput = findViewById(R.id.main_code_input);
        MyDecoration decoration = new MyDecoration();
        idListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        idListRecyclerView.addItemDecoration(decoration);
        codeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        codeListRecyclerView.addItemDecoration(decoration);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://mytownadmin.cafe24app.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resetRetrofit = new Retrofit.Builder()
                .baseUrl("http://mytown.cafe24app.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService = retrofit.create(LoginService.class);
        CodeService codeService = retrofit.create(CodeService.class);
        ResetService resetService = resetRetrofit.create(ResetService.class);
        makeIdList(loginService);


        findCodeButton.setOnClickListener(view -> {
            if(findCodeInput.getText()!=null){
                codeListRecyclerView.scrollToPosition(Integer.parseInt(findCodeInput.getText().toString()));
            }
                });
        //CodeService codeService = retrofit.create(CodeService.class);
        userResetButton.setOnClickListener(view -> {
            Call<ResultUser> resetCode =  resetService.reset_users("dajuri1234");
            resetCode.enqueue(new Callback<ResultUser>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                    Log.d("main","리셋?");
                    ResultUser result = response.body();
                    if(result != null) {
                        if(result.getResult().equals(202)){
                            //reset
                            Toast toast = Toast.makeText(getApplicationContext(),"Server reset",Toast.LENGTH_SHORT);
                            toast.show();
                        }else if(result.getResult().equals(404)){
                            Toast toast = Toast.makeText(getApplicationContext(),"Server reset denied ",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<ResultUser> call, Throwable t) {
                    Log.d("MainUser",t+"");
                    Toast toast = Toast.makeText(getApplicationContext(),"서버 초기화 중입니다. 잠시후에 확인해주세요.",Toast.LENGTH_LONG);
                    toast.show();
                }
            });

        });
        codeResetButton.setOnClickListener(view -> {
            Call<ResultUser> resetCode =  resetService.reset_codes("doenda6561");
            resetCode.enqueue(new Callback<ResultUser>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                    Log.d("main","코드");
                    ResultUser result = response.body();
                    if(result != null) {
                        if(result.getResult().equals(202)){
                            //reset
                            Toast toast = Toast.makeText(getApplicationContext(),"Server reset",Toast.LENGTH_SHORT);
                            toast.show();
                            MakeCodeList(codeService);

                        }else if(result.getResult().equals(404)){
                            Toast toast = Toast.makeText(getApplicationContext(),"Server reset denied ",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<ResultUser> call, Throwable t) {
                    Log.d("MainUser",t+"");
                    Toast toast = Toast.makeText(getApplicationContext(),"서버 초기화 중입니다. 잠시후에 확인해주세요.",Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        });
        checkbox_main.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.id_select_button:
                    //select id;
                    idCheckedLayout.setVisibility(View.VISIBLE);
                    codeCheckedLayout.setVisibility(View.GONE);
                    idListLabel.setVisibility(View.VISIBLE);
                    idListRecyclerView.setVisibility(View.VISIBLE);
                    codeListLabel.setVisibility(View.GONE);
                    codeListRecyclerView.setVisibility(View.GONE);
                    findCodeLayout.setVisibility(View.GONE);
                    makeIdList(loginService);
                    break;
                case R.id.code_list_button:
                    //select code;
                    codeCheckedLayout.setVisibility(View.VISIBLE);
                    idCheckedLayout.setVisibility(View.GONE);
                    idListLabel.setVisibility(View.GONE);
                    idListRecyclerView.setVisibility(View.GONE);
                    codeListLabel.setVisibility(View.VISIBLE);
                    findCodeLayout.setVisibility(View.VISIBLE);
                    codeListRecyclerView.setVisibility(View.VISIBLE);
                    MakeCodeList(codeService);
                    break;
            }

        });



        checkIdManager.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.insert_id:
                    Log.d("main", "insert_id");
                    insertLayout.setVisibility(View.VISIBLE);
                    modifyLayout.setVisibility(View.GONE);
                    deleteLayout.setVisibility(View.GONE);
                    makeIdList(loginService);
                    break;
                case R.id.modify_pw:
                    insertLayout.setVisibility(View.GONE);
                    modifyLayout.setVisibility(View.VISIBLE);
                    deleteLayout.setVisibility(View.GONE);

                    break;
                case R.id.delete_id:
                    insertLayout.setVisibility(View.GONE);
                    modifyLayout.setVisibility(View.GONE);
                    deleteLayout.setVisibility(View.VISIBLE);
                    makeIdList(loginService);
                    break;
            }
        });
        idSendButton.setOnClickListener(view -> {
            switch (checkIdManager.getCheckedRadioButtonId()){
                case R.id.insert_id:
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
                case R.id.modify_pw:
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
                    if(newPw.equals(newCheckPw)) {
                        ModifyPw(loginService, curPw, newPw, level);
                        currentPasswordInput.setText("");
                        newPasswordCheckInput.setText("");
                        newPasswordInput.setText("");
                    }else{
                        Toast.makeText(this, "비밀번호가 위와 다릅니다.", Toast.LENGTH_SHORT).show();
                    }
                    im.hideSoftInputFromWindow(insertIdInput.getWindowToken(), 0);
                    break;
                case R.id.delete_id:
                    //삭제 구문
                    //adapter = new FindUserAdapter(user, fu);
                    //recyclerView.setAdapter(adapter);
                    String deleteId = "";

                    if(deleteIdInput.getText()!=null){
                        deleteId = deleteIdInput.getText().toString();
                    }
                    ResetId(loginService, deleteId);
                    deleteIdInput.setText("");
                    deleteCodeInput.setText("");
                    im.hideSoftInputFromWindow(insertIdInput.getWindowToken(), 0);

                    break;
            }
        });
        codeSendButton.setOnClickListener(view -> {
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
        });




    }
    public void GiveCode(CodeService codeService,int min, int max, String cityCode){
        Call<ResultUser> resultUserCall = codeService.code_main_give(min,max,cityCode);
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
    public void ResetId(LoginService loginService, String resetId){
        Call<ResultUser> result = loginService.city_delete(resetId);
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
    public void ModifyPw(LoginService loginService, String curPw, String newPw, String newCheckPw){
        Call<ResultUser> result = loginService.modify_main_user(userId,curPw,newPw,newCheckPw);
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
    public void InsertId(LoginService loginService, String user_id){
        Log.d("main","insertId");
        Call<ResultUser> result = loginService.city_insert(user_id);
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
                Toast toast = Toast.makeText(getApplicationContext(),"ID가 정상적으로 생성되었습니다.",Toast.LENGTH_LONG);
                toast.show();
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResultUser> call, Throwable t) {

            }
        });
    }
    public  void MakeCodeList(CodeService codeService){
        Log.d("start", "MakeCodeList");
        Call<GetCodeList> codeListCall = codeService.code_main_show("1");
        codeListCall.enqueue(new Callback<GetCodeList>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<GetCodeList> call, Response<GetCodeList> response) {
                if(response.isSuccessful()){
                    GetCodeList codes = response.body();
                    if(codes!=null) {
                        List<CodeList> codeLists = codes.getUser();
                        MakeCodeListAdapter codeListAdapter = new MakeCodeListAdapter(codeLists);
                        codeListAdapter.notifyDataSetChanged();
                        codeListRecyclerView.setAdapter(codeListAdapter);
                        //showListRecyclerView.setAdapter(codeListAdapter);
                    }
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<GetCodeList> call, Throwable t) {

            }
        });

    }

    public void makeIdList(LoginService loginService){
        Log.d("city","start makeIdList");
        Call<GetCityList> city_users = loginService.get_city_users();
        city_users.enqueue(new Callback<GetCityList>() {
            //ArrayList<CityList> user = new ArrayList<>();

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<GetCityList> call, Response<GetCityList> response) {
                if(response.isSuccessful()){
                    GetCityList list = response.body();
                    if(list!=null) {
                        List<CityUsersList> cityUsersLists = list.getUser();
                        Log.d("city", "success call");
                        makeIdListAdapter adapter = new makeIdListAdapter(cityUsersLists);
                        adapter.notifyDataSetChanged();
                        idListRecyclerView.setAdapter(adapter);

                    }


                }



            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<GetCityList> call, Throwable t) {
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
            View view = LayoutInflater.from(MainUserActivity.this).inflate(R.layout.code_list_main, parent, false);
            return new CodeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            CodeList code = codeLists.get(position);
            //Log.d("main",code.getId()+"");
            CodeHolder codeHolder = (CodeHolder) holder;
            String data = String.valueOf(position+1);
            codeHolder.codeListId.setText(data);
            codeHolder.codeListCode.setText(code.getCode());
            codeHolder.codeListCityCode.setText(code.getCityCode());
            codeHolder.codeListShopCode.setText(code.getShopCode());
            codeHolder.codeListUsed.setText(code.getUsed());

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
            private CodeHolder(View itemView) {
                super(itemView);
                codeListId = itemView.findViewById(R.id.code_list_id);
                codeListCode = itemView.findViewById(R.id.code_list_code);
                codeListCityCode = itemView.findViewById(R.id.code_list_city_code);
                codeListShopCode = itemView.findViewById(R.id.code_list_shop_code);
                codeListUsed = itemView.findViewById(R.id.code_list_used);

            }
        }

    }

    public class makeIdListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<CityUsersList> list;

        private makeIdListAdapter(List<CityUsersList> users) {
            this.list = users;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainUserActivity.this).inflate(R.layout.id_list_main, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            CityUsersList user = list.get(position);
            MyHolder holder1 = (MyHolder) holder;

            holder1.city_code.setText(user.getCityCode());
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
                city_code = itemView.findViewById(R.id.city_code_id_list_main);
                user_id = itemView.findViewById(R.id.user_id_id_list_main);

            }
        }

    }
}
