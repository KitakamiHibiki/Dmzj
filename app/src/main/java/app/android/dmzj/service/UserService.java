package app.android.dmzj.service;

import static app.android.dmzj.service.Service.sendMessage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import app.android.dmzj.fragment.userInfo.User;
import app.android.dmzj.fragment.userInfo.UserProfile;
import app.android.dmzj.request.WriteFiles;
import app.android.dmzj.request.user.Request_UserKt;

public class UserService {
    public static class Login extends Thread {
        String nickname, pwd;
        Handler handler;
        Activity activity;

        public Login(String nickname, String pwd,Activity activity, Handler handler) {
            this.nickname = nickname;
            this.pwd = pwd;
            this.handler = handler;
            this.activity = activity;
        }

        @Override
        public void run() {
            if (nickname == null || nickname.isEmpty()) {
                sendMessage(handler, 10001, "用户名不能为空");
                return;
            }
            if (pwd == null || pwd.isEmpty()) {
                sendMessage(handler, 10001, "密码不能为空");
                return;
            }
            String userInfo = Request_UserKt.login(nickname, pwd);
            try {
                JSONObject jsonObject = new JSONObject(userInfo);
                if(jsonObject.getString("result").equals("0")){
                    sendMessage(handler,10001,jsonObject.getString("msg"));
                    return;
                }
                WriteFiles.writeJson(jsonObject.toString(), activity.getFilesDir().getPath() + "/User.json");
                //创建文件夹
                //comic
                //novel
                //userInfo
                new File(activity.getFilesDir().getPath()+"/comic").mkdir();
                new File(activity.getFilesDir().getPath()+"/novel").mkdir();
                new File(activity.getFilesDir().getPath()+"/userInfo").mkdir();
                User.CreateUser(activity);
                UserProfile.writeToFile(Request_UserKt.getUserProfile(),activity.getFilesDir().getPath());
                sendMessage(handler,10002,null);
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(handler, 10001, "返回值解析失败:["+e.getClass().getName()+"] "+e.getMessage());
            }
        }
    }


}
