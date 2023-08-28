package app.android.dmzj.Activity.MyInfo

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.dmzj.Compose.Refresh
import app.android.dmzj.Compose.RefreshNestedScrollConnection
import app.android.dmzj.Compose.RefreshState
import app.android.dmzj.Entity.ComicSubscribeItem
import app.android.dmzj.Entity.User
import app.android.dmzj.R
import app.android.dmzj.Request.User.getAllComicSubscribe
import org.json.JSONArray
import org.json.JSONObject

class MyComicSubscribe : AppCompatActivity() {
    private val ALL_CONTENT_IS_LOADED = 0
    private lateinit var user: User
    private var page: String = "0"
    private var letter: String = "all"
    private var sub_type: String = "1"
    private var FirstJA: JSONArray = JSONArray()
    private var _data: Data = Data(FirstJA)
    private val handle = Handler { msg ->
        when (msg.what) {
            ALL_CONTENT_IS_LOADED -> {
                Toast(this).let {
                    it.setText("已加载全部内容")
                    it.show()
                }
            }
        }
        return@Handler false
    }

    class Data(Content: JSONArray) {
        var content by mutableStateOf(Content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = User(this)

        setContent {
            val scope = rememberCoroutineScope()
            val refreshState = RefreshState(scope,
                refreshFunction = {
                    Thread {
                        page = "0"
                        _data.content = getAllComicSubscribe(
                            context = this,
                            page = page,
                            letter = letter,
                            sub_type = sub_type
                        )
                    }.start()
                },
                loadMoreFunction = {
                    Thread {
                        page = "${page.toInt() + 1}"
                        val get = getAllComicSubscribe(
                            context = this,
                            page = page,
                            letter = letter,
                            sub_type = sub_type
                        )
                        for (a in 0 until get.length()) {
                            _data.content.put(get.get(a))
                        }
                        if (get.length() == 0) {
                            handle.sendEmptyMessage(ALL_CONTENT_IS_LOADED)
                            page = "${page.toInt() - 1}"
                        }
                        _data.content = JSONArray(_data.content.toString())
                    }.start()
                })
            val scrollState = rememberScrollState()
            val refreshNestedScrollConnection =
                RefreshNestedScrollConnection(scrollState = scrollState, state = refreshState)
            Column {
                Head()
                Refresh(
                    state = refreshState,
                    nestedScrollConnection = refreshNestedScrollConnection
                ) {
                    Content(scrollState = scrollState, modifier = Modifier.fillMaxHeight())
                }
            }
        }
        val t1 = Thread {
            _data.content = getAllComicSubscribe(
                context = this,
                page = page,
                letter = letter,
                sub_type = sub_type
            )
        }
        t1.start()
    }

    //内容展示组件
    @Composable
    fun MessageCard(jo: JSONObject) {
        //TODO: 添加MessageCard 点击事件，进入具体页面
        val cI = ComicSubscribeItem(jo, this)
        Log.i("Show MCSI", jo.toString())
        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .shadow(1.dp, RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                Box {
                    Image(
                        bitmap = cI.imageBitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(1.dp, 0.dp)
                            .height(160.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.matchParentSize()) {
                        if (cI.status == "已完结") {
                            Row {
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "已完结",
                                    color = Color.White,
                                    modifier = Modifier
                                        .background(
                                            Color(254, 87, 34),
                                            RoundedCornerShape(2.dp)
                                        )
                                        .padding(2.dp),
                                    fontSize = 13.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        if (cI.status == "连载中") {
                            Row {
                                Text(
                                    text = "连载中",
                                    color = Color.White,
                                    modifier = Modifier
                                        .background(
                                            Color(33, 150, 243),
                                            RoundedCornerShape(2.dp)
                                        )
                                        .padding(2.dp),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
                Text(
                    text = cI.name,
                    maxLines = 1,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(8.dp, 0.dp),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "更新 ${cI.sub_update}",
                    maxLines = 1,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp, 0.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    //使用内容展示组件，展示全部数据
    @Composable
    fun Content(scrollState: ScrollState = rememberScrollState(), modifier: Modifier = Modifier) {
        Column(modifier = modifier.verticalScroll(scrollState)) {
            for (a in 0 until _data.content.length() / 3) {
                Row(modifier = Modifier.padding(10.dp, 5.dp)) {
                    for (b in 0..2) {
                        Row(modifier = Modifier.weight(1f)) {
                            MessageCard(_data.content.getJSONObject(a * 3 + b))
                        }
                        if (b != 2) {
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
            }
            if (_data.content.length() % 3 != 0) {
                Row(modifier = Modifier.padding(10.dp, 5.dp)) {
                    for (b in 0 until _data.content.length() % 3) {
                        Row(modifier = Modifier.weight(1f)) {
                            MessageCard(_data.content.getJSONObject(_data.content.length() / 3 + b))
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }
        }
    }

    //顶部导航栏
    @Composable
    fun Head() {
        Box(
            modifier = Modifier
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { finish() })
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    Text("我的漫画订阅", fontSize = 20.sp)
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .shadow(1.dp)
        )
    }

}