package app.android.dmzj.request.user

import app.android.dmzj.request.NetConnection
import app.android.dmzj.request.api
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils

//登录并获取个人消息
fun login(nickname:String,pwd:String):String{
//    nickname:kitakamihibiki
//    pwd:359vbhg+
    val url = api.BASE_URL_USER+"/loginV2/m_confirm"
    val HM = HashMap<String, String>()
    HM["nickname"] = nickname
    HM["pwd"] = DigestUtils.md5Hex(pwd).uppercase()
    return NetConnection.postJson(url,HM,null)
}

//获取我的头像
//fun getMyHeadImage(context: Context) {
//    Thread {
//        val user = User(context)
//        val file =
//            File(context.cacheDir.path + "/HeadImage.${user.photo.split(".")[user.photo.split(".").size - 1]}")
//        NetConnection.getFile(user.photo, file.path)
//    }.start()
//}

//fun getAllComicSubscribe(
//    context: Context,
//    page: String,
//    letter: String,
//    sub_type: String
//): JSONArray {
//    val user = User(context)
//    val hM = HashMap<String, String>()
//    hM["dmzj_token"] = user.dmzj_token
//    hM["uid"] = user.uid
//    hM["letter"] = letter
//    hM["sub_type"] = sub_type
//    hM["page"] = page
//    var result = ""
//    try {
//        result = NetConnection.getContent(
//            NetConnection.BaseUrl+"/Comic/ComicSubscribeAll", hM)
//    } catch (ex: Exception) {
//        ex.printStackTrace()
//        result = "Error"
//    }
//    while (result.contains("Error")) {
//        try {
//            Thread.sleep(3000)
//            result = NetConnection.getContent(
//                NetConnection.BaseUrl+"/Comic/ComicSubscribeAll", hM)
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            result = "Error"
//        }
//    }
//    if(result.contains("Error")) {
//        return JSONArray()
//    }
//    return JSONObject(result).getJSONArray("Result")
//}

fun getAllNovelSubscribe(

){

}