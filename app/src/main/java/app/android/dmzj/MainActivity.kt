package app.android.dmzj

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import app.android.dmzj.Bean.User
import app.android.dmzj.Login.Login
import app.android.dmzj.Main.Main
import app.android.dmzj.Tools.Tools
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onStart() {
        //判断是否登录
        val loginFile = File(this.filesDir.path + "/User.json")
        if (!loginFile.exists() || loginFile.length() == 0L) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        } else {
            Thread {
                //获取用户头像
                val user = User(this)

                val file =
                    File(this.cacheDir.path + "/HeadImage.${user.photo.split(".")[user.photo.split(".").size - 1]}")
                Tools.getFile(user.photo, file.path)
            }.start()
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
        }
        super.onStart()
    }
}