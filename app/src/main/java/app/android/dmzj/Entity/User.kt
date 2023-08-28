package app.android.dmzj.Entity

import android.content.Context
import org.json.JSONObject
import java.io.FileInputStream
import java.lang.StringBuilder

class User(context: Context) {
    val nickname: String
    val password: String
    val uid: String
    val cookie_val: String
    val dmzj_token: String
    var userName: String = ""
    var photo: String = ""
    var description: String = ""

    init {
        val fIn = FileInputStream(context.filesDir.path + "/User.json")
        val stringBuilder = StringBuilder()
        var a: Int
        a = fIn.read()
        while (a != -1) {
            stringBuilder.append(a.toChar())
            a = fIn.read()
        }
        val json = JSONObject(stringBuilder.toString())
        nickname = json.getString("nickname")
        password = json.getString("password")
        uid = json.getString("uid")
        cookie_val = json.getString("cookie_val")
        dmzj_token = json.getString("dmzj_token")
        try {
            userName = json.getString("userName")
            photo = json.getString("photo")
            description = json.getString("description")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}