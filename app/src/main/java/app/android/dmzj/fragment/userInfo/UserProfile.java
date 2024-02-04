package app.android.dmzj.fragment.userInfo;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import app.android.dmzj.request.ReadFiles;
import app.android.dmzj.request.WriteFiles;

public class UserProfile {
    public static JSONObject profile = null;

    public static void setProfile(String dir)throws Exception{
        if(profile!=null)return;
        File file = new File(dir+"/userInfo/userProfile.json");
        profile = new JSONObject(ReadFiles.readJson(file));
    }

    public static void writeToFile(String data,String dir)throws Exception{
        WriteFiles.writeJson(data,dir+"/userInfo/userProfile.json");
    }

    public static boolean deleteUserProfile(String dir){
        profile = null;
        return new File(dir+"/userInfo/userProfile.json").delete();
    }
}
