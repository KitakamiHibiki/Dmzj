package app.android.dmzj.Request.User

import android.content.Context
import app.android.dmzj.Entity.User
import app.android.dmzj.Request.Tools.Tools
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.lang.Exception

//获取我的头像
fun getMyHeadImage(context: Context) {
    Thread {
        val user = User(context)
        val file =
            File(context.cacheDir.path + "/HeadImage.${user.photo.split(".")[user.photo.split(".").size - 1]}")
        Tools.getFile(user.photo, file.path)
    }.start()
}

fun getAllComicSubscribe(
    context: Context,
    page: String,
    letter: String,
    sub_type: String
): JSONArray {
    val user = User(context)
    val hM = HashMap<String, String>()
    var count =0
    hM["dmzj_token"] = user.dmzj_token
    hM["uid"] = user.uid
    hM["letter"] = letter
    hM["sub_type"] = sub_type
    hM["page"] = page
    var result = ""
    try {
        count+=1
        result = Tools.getContent("http://192.168.1.225:8880/Comic/ComicSubscribeAll", hM)
    } catch (ex: Exception) {
        ex.printStackTrace()
        result = "Error"
    }
    while (count<3 && result.contains("Error")) {
        try {
            count+=1
            result = Tools.getContent("http://192.168.1.225:8880/Comic/ComicSubscribeAll", hM)
        } catch (ex: Exception) {
            ex.printStackTrace()
            result = "Error"
        }
    }
    if(result.contains("Error")) {
        return JSONArray()
    }
    return JSONObject(result).getJSONArray("Result")
}