package kr.co.administrator6561;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCodeList {
    @SerializedName("codeLIst")
    @Expose
    private List<CodeList> user = null;

    public List<CodeList> getUser() {
        return user;
    }

    public void setUser(List<CodeList> user) {
        this.user = user;
    }
}
