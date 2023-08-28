package app.android.dmzj

import android.content.Intent
import androidx.activity.ComponentActivity
import app.android.dmzj.Activity.Login.Login
import app.android.dmzj.Activity.Main
import app.android.dmzj.Request.User.getMyHeadImage
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onStart() {
        //判断是否登录
        val loginFile = File(this.filesDir.path + "/User.json")
        if (!loginFile.exists() || loginFile.length() == 0L) {
            val intent = Intent(this, Login::class.java)
            startActivityForResult(intent,1)
        } else {
            getMyHeadImage(this)
            val intent = Intent(this, Main::class.java)
            startActivityForResult(intent,2)
        }
        super.onStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->{if(resultCode!=1)finish()}
            2->{if(resultCode!=1)finish()}
        }
    }
}