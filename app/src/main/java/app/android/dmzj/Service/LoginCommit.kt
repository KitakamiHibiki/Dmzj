package app.android.dmzj.Service

import app.android.dmzj.Tools.Tools
import java.util.HashMap

fun LoginCommit(nickname: String, password: String): String {
    val HM = HashMap<String, String>()
    HM["nickname"] = nickname
    HM["password"] = password
    val Result = Tools.getContent("http://192.168.1.225:8880/mannga/login", HM)
    return Result
}