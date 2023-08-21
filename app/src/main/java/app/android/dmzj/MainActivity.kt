package app.android.dmzj

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import app.android.dmzj.Login.Login
import app.android.dmzj.Main.Main
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        //判断是否登录
        val loginFile = File(this.filesDir.path+"/User.json")
        if(!loginFile.exists()){
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this,Main::class.java)
            startActivity(intent)
        }
        super.onStart()
    }
}