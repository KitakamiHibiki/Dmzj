package app.android.dmzj.fragment.comic

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import app.android.dmzj.R
import app.android.dmzj.activity.Main
import app.android.dmzj.request.WriteFiles
import app.android.dmzj.service.ComicService
import app.android.dmzj.service.Service
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class ComicRecommendFragment(activity: Main) : Fragment() {
    private val activity: Main
    private val handler: Handler
    private val selfHandler: Handler
    private val ERROR = 101
    private val REQUEST_COMMEND_SUCCESS = 102
    private val CHANGE_VIEWPAGER_ITEM = 103
    lateinit var composeView: ComposeView
    lateinit var viewPager:ViewPager
    var context = mutableStateOf("")
    private lateinit var category_46: JSONObject
    private lateinit var category_47: JSONObject
    private lateinit var category_48: JSONObject
    private lateinit var category_51: JSONObject
    private lateinit var category_52: JSONObject
    private lateinit var category_53: JSONObject
    private lateinit var category_54: JSONObject
    private lateinit var category_55: JSONObject
    private lateinit var category_56: JSONObject
    var isAlive = false

    init {
        this.activity = activity
        this.handler = activity.handler
        this.selfHandler = Handler(Handler.Callback { message ->
            when (message.what) {
                ERROR -> {
                    //异常
                    Service.sendMessage(handler, activity.CHANGE_FRAGMENT_TO_ERROR, message.obj)
                }
                REQUEST_COMMEND_SUCCESS -> {
                    //获取完整Comic_Recommend成功
                    val a = message.obj.toString()
                    commendSplit(a)
                    context.value = a
                }
                CHANGE_VIEWPAGER_ITEM->{
                    viewPager.currentItem = (viewPager.currentItem+1)%viewPager.adapter!!.count
                }
            }
            return@Callback true
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comic_recommend, container, false)
        ComicService.getComicRecommend(this.selfHandler).start()
        composeView = view.findViewById(R.id.compose)
        viewPager = view.findViewById(R.id.viewPager)
        composeView.setContent {
            if (context.value == "")
                return@setContent
            val ja46 = category_46.getJSONArray("data")
            val picList = ArrayList<JSONObject>()
            for(a in 0 until ja46.length()){
                picList.add(ja46.getJSONObject(a))
            }
            val adapter =
                ComicRecommendViewPagerAdapter(
                    this.getContext(),
                    picList
                )
            isAlive=true
            ComicRecommendViewPagerAdapter.setAdapter(viewPager,adapter)
            thread_1(viewPager,selfHandler).start()

            Column {
                Surface(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column {
                        Text(
                            text = "近期必看",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        val ja = category_47.getJSONArray("data")
                        Row(
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Column {
                                val jo = ja.getJSONObject(0)
                                val cover = jo.getString("cover")
                                val title = jo.getString("title")
                                val sub_title = jo.getString("sub_title")
                                val type = jo.getString("type")
                                val url = jo.getString("url")
                                val obj_id = jo.getString("obj_id")
                                val status = jo.get("status")
                                val split = cover.split(".")
                                val path =
                                    activity.filesDir.path.toString() + "/comic/recommend_47_0." + split[split.size - 1]
                                Surface(
                                    modifier = Modifier
                                        .width(115.dp)
                                        .height(170.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Image(
                                        bitmap = WriteFiles.getBitMap(cover, path).asImageBitmap(),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Text(text = title, fontSize = 14.sp,maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(text = sub_title, fontSize = 13.sp, color = Color.Gray,maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Column {
                                val jo = ja.getJSONObject(1)
                                val cover = jo.getString("cover")
                                val title = jo.getString("title")
                                val sub_title = jo.getString("sub_title")
                                val type = jo.getString("type")
                                val url = jo.getString("url")
                                val obj_id = jo.getString("obj_id")
                                val status = jo.get("status")
                                val split = cover.split(".")
                                val path =
                                    activity.filesDir.path.toString() + "/comic/recommend_47_1." + split[split.size - 1]
                                Surface(
                                    modifier = Modifier
                                        .width(115.dp)
                                        .height(170.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Image(
                                        bitmap = WriteFiles.getBitMap(cover, path).asImageBitmap(),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Text(text = title, fontSize = 14.sp,maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(text = sub_title, fontSize = 13.sp, color = Color.Gray,maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Column {
                                val jo = ja.getJSONObject(2)
                                val cover = jo.getString("cover")
                                val title = jo.getString("title")
                                val sub_title = jo.getString("sub_title")
                                val type = jo.getString("type")
                                val url = jo.getString("url")
                                val obj_id = jo.getString("obj_id")
                                val status = jo.get("status")
                                val split = cover.split(".")
                                val path =
                                    activity.filesDir.path.toString() + "/comic/recommend_47_2." + split[split.size - 1]
                                Surface(
                                    modifier = Modifier
                                        .width(115.dp)
                                        .height(170.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Image(
                                        bitmap = WriteFiles.getBitMap(cover, path).asImageBitmap(),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Text(text = title, fontSize = 14.sp,maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(text = sub_title, fontSize = 13.sp, color = Color.Gray,maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column {
                        Text(
                            text = "火热专题",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        val ja = category_48.getJSONArray("data")
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose48(jo = ja.getJSONObject(0), 0)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose48(jo = ja.getJSONObject(1), 1)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose48(jo = ja.getJSONObject(2), 2)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose48(jo = ja.getJSONObject(3), 3)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column {
                        Text(
                            text = "大师级作者怎能不看",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        val ja = category_51.getJSONArray("data")
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose51(jo = ja.getJSONObject(0), count = 0)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose51(jo = ja.getJSONObject(1), count = 1)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose51(jo = ja.getJSONObject(2), count = 2)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column {
                        Text(
                            text = "热门连载",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        val ja = category_54.getJSONArray("data")
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose54(jo = ja.getJSONObject(0), count = 0)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose54(jo = ja.getJSONObject(1), count = 1)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose54(jo = ja.getJSONObject(2), count = 2)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose54(jo = ja.getJSONObject(3), count = 3)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose54(jo = ja.getJSONObject(4), count = 4)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose54(jo = ja.getJSONObject(5), count = 5)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column {
                        Text(
                            text = "条漫专区",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        val ja = category_55.getJSONArray("data")
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose55(jo = ja.getJSONObject(0), 0)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose55(jo = ja.getJSONObject(1), 1)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose55(jo = ja.getJSONObject(2), 2)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose55(jo = ja.getJSONObject(3), 3)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column {
                        Text(
                            text = "最新上架",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        val ja = category_56.getJSONArray("data")
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose56(jo = ja.getJSONObject(0), count = 0)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose56(jo = ja.getJSONObject(1), count = 1)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose56(jo = ja.getJSONObject(2), count = 2)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Compose56(jo = ja.getJSONObject(3), count = 3)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose56(jo = ja.getJSONObject(4), count = 4)
                            Spacer(modifier = Modifier.weight(1f))
                            Compose56(jo = ja.getJSONObject(5), count = 5)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
        return view
    }

    private fun commendSplit(str: String) {
        val ja = JSONArray(str)
        for (a in 0 until ja.length()) {
            val jo = ja.getJSONObject(a)
            when (jo.getString("category_id")) {
                "46" -> category_46 = jo
                "47" -> category_47 = jo
                "48" -> category_48 = jo
                "51" -> category_51 = jo
                "52" -> category_52 = jo
                "53" -> category_53 = jo
                "54" -> category_54 = jo
                "55" -> category_55 = jo
                "56" -> category_56 = jo
            }
        }
    }

    @Composable
    fun Compose48(jo: JSONObject, count: Int) {
        val cover = jo.getString("cover")
        val title = jo.getString("title")
        val sub_title = jo.getString("sub_title")
        val type = jo.getString("type")
        val url = jo.getString("url")
        val obj_id = jo.getString("obj_id")
        val status = jo.getString("status")
        val split = cover.split(".")
        val path =
            activity.filesDir.path.toString() + "/comic/recommend_48_" + count + "." + split[split.size - 1]
        Column(Modifier.width(175.dp)) {
            Surface(
                Modifier
                    .width(175.dp)
                    .height(80.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    bitmap = WriteFiles.getBitMap(cover, path).asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = title, fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }

    @Composable
    fun Compose55(jo: JSONObject, count: Int) {
        val cover = jo.getString("cover")
        val title = jo.getString("title")
        val sub_title = jo.getString("sub_title")
        val type = jo.getString("type")
        val url = jo.getString("url")
        val obj_id = jo.getString("obj_id")
        val status = jo.getString("status")
        val split = cover.split(".")
        val path =
            activity.filesDir.path.toString() + "/comic/recommend_55_" + count + "." + split[split.size - 1]
        Column(Modifier.width(175.dp)) {
            Surface(
                Modifier
                    .width(175.dp)
                    .height(80.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    bitmap = WriteFiles.getBitMap(cover, path).asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = title, fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }

    @Composable
    fun Compose51(jo: JSONObject, count: Int) {
        val cover = jo.getString("cover")
        val title = jo.getString("title")
        val sub_title = jo.getString("sub_title")
        val type = jo.getString("type")
        val url = jo.getString("url")
        val obj_id = jo.getString("obj_id")
        val status = jo.getString("status")
        val split = cover.split(".")
        val path =
            activity.filesDir.path.toString() + "/comic/recommend_51_" + count + "." + split[split.size - 1]
        Column(Modifier.width(70.dp)) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Surface {
                    Image(
                        bitmap = WriteFiles.getBitMap(cover, path).asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = title, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    @Composable
    fun Compose54(jo: JSONObject, count: Int) {
        val cover = jo.getString("cover")
        val title = jo.getString("title")
        val sub_title = jo.getString("sub_title")
        val type = jo.getString("type")
        val url = jo.getString("url")
        val obj_id = jo.getString("obj_id")
        val status = jo.getString("status")
        val split = cover.split(".")
        val path =
            activity.filesDir.path.toString() + "/comic/recommend_54_" + count + "." + split[split.size - 1]

        Column(modifier = Modifier.width(115.dp)) {
            Surface(
                modifier = Modifier
                    .width(115.dp)
                    .height(170.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    bitmap = WriteFiles.getBitMap(cover, path).asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = title, fontSize = 14.sp,maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = sub_title, fontSize = 13.sp, color = Color.Gray,maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }

    @Composable
    fun Compose56(jo: JSONObject, count: Int) {
        val id = jo.getString("id")
        val title = jo.getString("title")
        val authors = jo.getString("authors")
        val status = jo.getString("status")
        val cover = jo.getString("cover")
        val split = cover.split(".")
        val path =
            activity.filesDir.path.toString() + "/comic/recommend_56_" + count + "." + split[split.size - 1]

        Column(modifier = Modifier.width(115.dp)) {
            Surface(
                modifier = Modifier
                    .width(115.dp)
                    .height(170.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    bitmap = WriteFiles.getBitMapOverWriteFile(cover, path).asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = title, fontSize = 14.sp,maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = authors, fontSize = 13.sp, color = Color.Gray,maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }

    override fun onDestroy() {
        isAlive=false
        super.onDestroy()
    }

    private class thread_1(viewPager: ViewPager,handler: Handler): Thread() {
        val viewPager:ViewPager
        val handler:Handler

        init {
            this.viewPager=viewPager
            this.handler = handler
        }

        override fun run() {
            while (isAlive) {
                sleep(3000)
                Service.sendMessage(handler,103,null)
            }
        }
    }

}