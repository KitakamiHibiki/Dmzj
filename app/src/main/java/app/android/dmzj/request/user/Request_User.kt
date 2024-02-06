package app.android.dmzj.request.user

import app.android.dmzj.fragment.userInfo.User
import app.android.dmzj.request.NetConnection
import app.android.dmzj.request.api
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils
import org.json.JSONArray
import java.lang.Exception

//登录并获取个人消息
fun login(nickname: String, pwd: String): String {
//    nickname:kitakamihibiki
//    pwd:359vbhg+
    val url = api.BASE_URL_USER + "/loginV2/m_confirm"
    val HM = HashMap<String, String>()
    HM["nickname"] = nickname
    HM["pwd"] = DigestUtils.md5Hex(pwd).uppercase()
    return NetConnection.postJson(url, HM, null)
}

fun getUserProfile(): String {
    val url = "${api.BASE_URL_V3}/UCenter/comicsv2/${User.user.uid}.json"
    val parameters = HashMap<String, String>()
    parameters["dmzj_token"] = User.user.dmzj_token
    return NetConnection.getJson(url, parameters, null)
}

/// - [page] 页数从0开始
/// - [subType] 全部=1，未读=2，已读=3，完结=4
/// - [letter] all=全部，a-zA-Z0-9漫画名首字母
fun comicSubscribes(subType: Int, page: Int = 0, letter: String = "all"): String {
    val url = api.BASE_URL_V3 + "/UCenter/subscribe"
    val params = HashMap<String, String>()
    var data = ""
    params["type"] = "0"
    params["sub_type"] = subType.toString()
    params["letter"] = letter
    params["dmzj_token"] = User.user.dmzj_token
    params["page"] = page.toString()
    params["uid"] = User.user.uid
    Thread {
        while (data==""){
            try {
                data = NetConnection.getJson(url, params, null)
            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }
    }.let {
        it.start()
        it.join()
    }
    return JSONArray(data).toString()
}