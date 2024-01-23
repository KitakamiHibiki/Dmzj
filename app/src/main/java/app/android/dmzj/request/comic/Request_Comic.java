package app.android.dmzj.request.comic;

import app.android.dmzj.request.NetConnection;
import app.android.dmzj.request.api;

public class Request_Comic {
    public static String requestComicRecommend() throws Exception{
        String url = api.BASE_URL_V3+"/recommend_new.json";
        return NetConnection.getJson(url,null,null);
    }
}
