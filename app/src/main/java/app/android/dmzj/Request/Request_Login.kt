package app.android.dmzj.Request

import android.content.Context
import app.android.dmzj.Entity.User
import app.android.dmzj.Request.Tools.Tools
import java.net.URLEncoder
import java.util.HashMap

fun LoginCommit(nickname: String, password: String): String {
    val HM = HashMap<String, String>()
    HM["nickname"] = nickname
    HM["pwd"] = password
    return Tools.getContent("http://192.168.1.225:8880/Comic/Login", HM)
}

fun GetUserInfo(context: Context): String {
    val user = User(context)
    val HM = HashMap<String, String>()
    HM["uid"] = URLEncoder.encode(user.uid)
    return Tools.getContent("http://192.168.1.225:8880/Comic/userInfo", HM)
}