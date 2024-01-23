package app.android.dmzj.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import app.android.dmzj.R
import app.android.dmzj.fragment.ErrorFragment
import app.android.dmzj.fragment.comic.ComicRecommendFragment
import java.io.File

class Main : AppCompatActivity() {
    lateinit var fragmentContainerView: FragmentContainerView
    lateinit var comic: ImageButton
    lateinit var light_book: ImageButton
    lateinit var userInfo: ImageButton
    val CHANGE_FRAGMENT_TO_COMIC = 101
    val CHANGE_FRAGMENT_TO_LIGHTBOOK = 102
    val CHANGE_FRAGMENT_TO_USERINFO = 103
    val CHANGE_FRAGMENT_TO_ERROR = 104
    val handler:Handler = Handler(Handler.Callback { message: Message ->
        when(message.what){
            CHANGE_FRAGMENT_TO_COMIC-> {
                val ee = ComicRecommendFragment(this)
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view,ee)
                    .commit()
            }
            CHANGE_FRAGMENT_TO_ERROR->{
                val ee = ErrorFragment()
                val bundle = Bundle()
                bundle.putString("error",message.obj.toString())
                ee.arguments=bundle
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view,ee)
                    .commit()
            }
        }
        return@Callback true
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        val loginFile = File(this.filesDir.path + "/User.json")
        if (!loginFile.exists()) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            this.finish()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainerView = findViewById(R.id.fragment_container_view)
        comic = findViewById(R.id.comic)
        light_book = findViewById(R.id.light_book)
        userInfo = findViewById(R.id.userInfo)

        if (savedInstanceState == null) {
            val ee = ComicRecommendFragment(this)
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view,ee)
                .commit()
            Log.i("asd",supportFragmentManager.fragments::class.java.name)
        }
    }
}