package app.android.dmzj.fragment.userInfo;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.File;

import app.android.dmzj.request.ReadFiles;

public class User {
    public static User user = null;
    public String uid;
    public String nickname;
    public String dmzj_token;
    public String photo;
    public String bind_phone;
    public String email;
    public String passwd;
    public String cookie_val;


    public User(@NonNull Activity activity) throws Exception {
        File file = new File(activity.getFilesDir().getPath() + "/User.json");
        JSONObject jsonObject = new JSONObject(ReadFiles.readJson(file)).getJSONObject("data");
        uid = jsonObject.getString("uid");
        nickname = jsonObject.getString("nickname");
        dmzj_token = jsonObject.getString("dmzj_token");
        photo = jsonObject.getString("photo");
        bind_phone = jsonObject.getString("bind_phone");
        email = jsonObject.getString("email");
        passwd = jsonObject.getString("passwd");
        cookie_val = jsonObject.getString("cookie_val");
    }

    public static void CreateUser(Activity activity) throws Exception {
        User.user = new User(activity);
    }

    public static boolean isUserEmpty(){
        return User.user != null && !User.user.uid.isEmpty();
    }

    public static Boolean deleteUser(String dir){
        User.user=null;
        return new File(dir + "/User.json").delete();
    }
}
