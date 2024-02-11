package app.android.dmzj.request.novel;

import java.util.HashMap;

import app.android.dmzj.fragment.userInfo.User;
import app.android.dmzj.request.NetConnection;
import app.android.dmzj.request.api;

public class Request_Novel {

    /// - [page] 页数从0开始
    /// - [subType] 全部=1，未读=2，已读=3，完结=4
    /// - [letter] all=全部
    public static String novelSubscribes(String sub_type,String letter,String page) throws Exception{
        String url = api.BASE_URL_V3+"/UCenter/subscribe";
        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("type","1");
        parameters.put("sub_type",sub_type);
        parameters.put("letter",letter);
        parameters.put("page",page);
        parameters.put("dmzj_token", User.user.dmzj_token);
        parameters.put("uid",User.user.uid);
        return NetConnection.getJson(url,parameters,null);
    }

//   Future<List<UserSubscribeNovelModel>> novelSubscribes(
//       {required int subType, int page = 0, String letter = "all"}) async {
//     var list = <UserSubscribeNovelModel>[];
//     var result = await HttpClient.instance.getJson(
//       '/UCenter/subscribe',
//       queryParameters: {
//         //uid=$uid&sub_type=$subType&letter=$letter&dmzj_token=$token&page=$page&type=$type
//         "type": 1,
//         "sub_type": subType,
//         "letter": letter,
//         "dmzj_token": UserService.instance.dmzjToken,
//         "page": page,
//       },
//       needLogin: true,
//     );
//     for (var item in result) {
//       list.add(UserSubscribeNovelModel.fromJson(item));
//     }
//     return list;
//   }
    
}