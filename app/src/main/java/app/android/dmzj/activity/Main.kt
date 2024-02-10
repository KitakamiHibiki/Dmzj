package app.android.dmzj.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import app.android.dmzj.R
import app.android.dmzj.fragment.ErrorFragment
import app.android.dmzj.fragment.comic.ComicRecommendFragment
import app.android.dmzj.fragment.novel.NovelRecommendFragment
import app.android.dmzj.fragment.userInfo.User
import app.android.dmzj.fragment.userInfo.UserInfoFragment
import app.android.dmzj.fragment.userInfo.UserProfile
import app.android.dmzj.service.Service

class Main : AppCompatActivity() {
    companion object{
        const val CHANGE_FRAGMENT_TO_COMIC = 101
        const val CHANGE_FRAGMENT_TO_LIGHTBOOK = 102
        const val CHANGE_FRAGMENT_TO_USERINFO = 103
        const val CHANGE_FRAGMENT_TO_ERROR = 104
        var nowFragment:Int = 0
    }

    lateinit var fragmentContainerView: FragmentContainerView
    lateinit var comic: ImageButton
    lateinit var light_book: ImageButton
    lateinit var userInfo: ImageButton
    val handler:Handler = Handler(Handler.Callback { message: Message ->
        when(message.what){
            CHANGE_FRAGMENT_TO_COMIC-> {
                if(nowFragment==0)return@Callback true
                val ee = ComicRecommendFragment(this)
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view,ee)
                    .commit()
                comic.setImageResource(R.drawable.icon_comic_selected)
                light_book.setImageResource(R.drawable.icon_elebook)
                userInfo.setImageResource(R.drawable.icon_userinfo)
                nowFragment=0
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
                nowFragment = -1;
            }
            CHANGE_FRAGMENT_TO_LIGHTBOOK-> {
            if(nowFragment==1)return@Callback true
            val ee = NovelRecommendFragment(this)
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view,ee)
                .commit()
                comic.setImageResource(R.drawable.icon_comic)
                light_book.setImageResource(R.drawable.icon_elebook_selected)
                userInfo.setImageResource(R.drawable.icon_userinfo)
            nowFragment=1
        }
            CHANGE_FRAGMENT_TO_USERINFO-> {
                if(nowFragment==2)return@Callback true
                val ee = UserInfoFragment(this)
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view,ee)
                    .commit()
                comic.setImageResource(R.drawable.icon_comic)
                light_book.setImageResource(R.drawable.icon_elebook)
                userInfo.setImageResource(R.drawable.icon_userinfo_selected)
                nowFragment=2
            }
        }
        return@Callback true
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            User.CreateUser(this)
            UserProfile.setProfile(filesDir.path)
        }catch (exception:Exception){
            exception.printStackTrace()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            this.finish()
        }
        setContentView(R.layout.activity_main)
        fragmentContainerView = findViewById(R.id.fragment_container_view)
        comic = findViewById(R.id.comic)
        light_book = findViewById(R.id.light_book)
        userInfo = findViewById(R.id.userInfo)

        //设置按钮单击事件
        comic.setOnClickListener {
            Service.sendMessage(handler,CHANGE_FRAGMENT_TO_COMIC,null)
        }
        light_book.setOnClickListener {
            Service.sendMessage(handler,CHANGE_FRAGMENT_TO_LIGHTBOOK,null)
        }
        userInfo.setOnClickListener {
            Service.sendMessage(handler,CHANGE_FRAGMENT_TO_USERINFO,null)
        }

        //启动comic Fragment
        if (savedInstanceState == null) {
            val ee = ComicRecommendFragment(this)
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view,ee)
                .commit()
            nowFragment=0
            comic.setImageResource(R.drawable.icon_comic_selected)
            light_book.setImageResource(R.drawable.icon_elebook)
            userInfo.setImageResource(R.drawable.icon_userinfo)
        }
    }
}