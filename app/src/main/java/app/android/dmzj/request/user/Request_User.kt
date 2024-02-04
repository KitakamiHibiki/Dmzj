package app.android.dmzj.request.user

import app.android.dmzj.fragment.userInfo.User
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

fun getUserProfile():String{
    val url = "${api.BASE_URL_V3}/UCenter/comicsv2/${User.user.uid}.json"
    val parameters = HashMap<String,String>()
    parameters["dmzj_token"] = User.user.dmzj_token
    return NetConnection.getJson(url,parameters,null)
}