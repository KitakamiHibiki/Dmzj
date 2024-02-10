package app.android.dmzj.request.comic;

import com.google.protobuf.util.JsonFormat;

import org.json.JSONObject;

import java.util.HashMap;

import app.android.dmzj.fragment.userInfo.User;
import app.android.dmzj.proto.Comic;
import app.android.dmzj.request.NetConnection;
import app.android.dmzj.request.RSA_Decode;
import app.android.dmzj.request.api;

public class Request_Comic {
    public static String requestComicRecommend() throws Exception{
        String url = api.BASE_URL_V3+"/recommend_new.json";
        return NetConnection.getJson(url,null,null);
    }

    public static String comicDetail(String comic_id) throws Exception{
        //    https://v4api.idmzj.com/comic/detail/+Comic_id
        String url = api.BASE_URL_V4+"/comic/detail/"+comic_id;
        HashMap<String,String> HM = new HashMap<>();
        HM.put("uid", User.user.uid);
        //获取内容
        String content = NetConnection.getJson(url,HM,null);
        //解密
        byte[] bytes = new RSA_Decode().decryptByPrivateKey(content);
        //反序列化
        Comic.ComicDetailResponseProto a = Comic.ComicDetailResponseProto.parseFrom(bytes);
        //JSON化
        JSONObject jsonObject = new JSONObject(JsonFormat.printer().print(a));
        return jsonObject.toString();
    }
}
