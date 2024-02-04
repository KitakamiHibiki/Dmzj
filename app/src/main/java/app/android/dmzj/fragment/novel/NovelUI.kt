package app.android.dmzj.fragment.novel

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.dmzj.request.WriteFiles
import org.json.JSONArray
import org.json.JSONObject

class NovelUI(context: Context) {
    var context: Context

    companion object {
        const val RecommendCompose = 101
    }

    var content57: String = ""
    var content58: String = ""
    var content60: String = ""
    var content62: String = ""
    var content63: String = ""

    init {
        this.context = context
    }

    fun setCompose(view: ComposeView, type: Int, content: String) {
        when (type) {
            RecommendCompose -> view.setContent { RecommendCompose(content) }
        }
    }

    @Composable
    fun RecommendCompose(content: String) {
        val ja = JSONArray(content)
        for (a in 0 until ja.length()) {
            when (ja.getJSONObject(a).getString("category_id")) {
                "57" -> content57 = ja.getJSONObject(a).toString()
                "58" -> content58 = ja.getJSONObject(a).toString()
                "60" -> content60 = ja.getJSONObject(a).toString()
                "62" -> content62 = ja.getJSONObject(a).toString()
                "63" -> content63 = ja.getJSONObject(a).toString()
            }
        }
        Column {
            Compose58(content = content58)
            Spacer(modifier = Modifier.height(10.dp))
            Compose60(content = content60)
            Spacer(modifier = Modifier.height(10.dp))
            Compose62(content = content62)
            Spacer(modifier = Modifier.height(10.dp))
            Compose63(content = content63)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    data class Data58(
        val ID: Int,
        val id: String,
        val title: String,
        val cover: String,
        val status: String,
        val url: String,
        val obj_id: String,
        val type: String
    )

    private fun convert58(jo: JSONObject, ID: Int): Data58 {
        return Data58(
            ID,
            jo.getString("id"),
            jo.getString("title"),
            jo.getString("cover"),
            jo.getString("status"),
            jo.getString("url"),
            jo.getString("obj_id"),
            jo.getString("type")
        )
    }

    @Composable
    fun Compose58(content: String) {
        Surface(
            modifier = Modifier.padding(horizontal = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            val data = JSONObject(content).getJSONArray("data")
            Column {
                Text(
                    text = "最新更新",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    val weight = 10f
                    val spaceWeight = 1f
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose58Child(
                        data58 = convert58(data.getJSONObject(0), 0),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose58Child(
                        data58 = convert58(data.getJSONObject(1), 1),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose58Child(
                        data58 = convert58(data.getJSONObject(2), 2),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                }
            }
        }
    }

    @Composable
    fun Compose58Child(data58: Data58, modifier: Modifier = Modifier) {
        val split = data58.cover.split(".")
        val path = "${context.filesDir}/novel/recommend_58_${data58.ID}.${split[split.size - 1]}"
        Column(modifier = modifier) {
            Row {
                Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) {
                    Image(
                        bitmap = WriteFiles.getBitMap(data58.cover, path).asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(text = data58.title, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                text = data58.status,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    data class Data60(
        val ID: Int,
        val id: String,
        val obj_id: String,
        val title: String,
        val cover: String,
        val url: String,
        val type: String,
        val sub_title: String,
        val status: String
    )

    private fun convert60(jo: JSONObject, ID: Int): Data60 {
        return Data60(
            ID,
            jo.getString("id"),
            jo.getString("obj_id"),
            jo.getString("title"),
            jo.getString("cover"),
            jo.getString("url"),
            jo.getString("type"),
            jo.getString("sub_title"),
            jo.getString("status")
        )
    }

    @Composable
    fun Compose60(content: String) {
        Surface(
            modifier = Modifier.padding(horizontal = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            val data = JSONObject(content).getJSONArray("data")
            Column {
                Text(
                    text = "动画进行时",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    val weight = 10f
                    val spaceWeight = 1f
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose60Child(
                        data60 = convert60(data.getJSONObject(0), 0),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose60Child(
                        data60 = convert60(data.getJSONObject(1), 1),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose60Child(
                        data60 = convert60(data.getJSONObject(2), 2),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                }
            }
        }
    }

    @Composable
    fun Compose60Child(data60: Data60, modifier: Modifier = Modifier) {
        val split = data60.cover.split(".")
        val path = "${context.filesDir}/novel/recommend_60_${data60.ID}.${split[split.size - 1]}"
        Column(modifier = modifier) {
            Row {
                Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) {
                    Image(
                        bitmap = WriteFiles.getBitMap(data60.cover, path).asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(text = data60.title, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                text = data60.status,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    data class Data62(
        val ID: Int,
        val id: String,
        val obj_id: String,
        val title: String,
        val cover: String,
        val url: String,
        val type: String,
        val sub_title: String,
        val status: String
    )

    private fun convert62(jo: JSONObject, ID: Int): Data62 {
        return Data62(
            ID,
            jo.getString("id"),
            jo.getString("obj_id"),
            jo.getString("title"),
            jo.getString("cover"),
            jo.getString("url"),
            jo.getString("type"),
            jo.getString("sub_title"),
            jo.getString("status")
        )
    }

    @Composable
    fun Compose62(content: String) {
        Surface(
            modifier = Modifier.padding(horizontal = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            val data = JSONObject(content).getJSONArray("data")
            Column {
                Text(
                    text = "即将动画化",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    val weight = 10f
                    val spaceWeight = 1f
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose62Child(
                        data62 = convert62(data.getJSONObject(0), 0),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose62Child(
                        data62 = convert62(data.getJSONObject(1), 1),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose62Child(
                        data62 = convert62(data.getJSONObject(2), 2),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                }
            }
        }
    }

    @Composable
    fun Compose62Child(data62: Data62, modifier: Modifier = Modifier) {
        val split = data62.cover.split(".")
        val path = "${context.filesDir}/novel/recommend_62_${data62.ID}.${split[split.size - 1]}"
        Column(modifier = modifier) {
            Row {
                Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) {
                    Image(
                        bitmap = WriteFiles.getBitMap(data62.cover, path).asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(text = data62.title, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                text = data62.status,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    data class Data63(
        val ID: Int,
        val id: String,
        val obj_id: String,
        val title: String,
        val cover: String,
        val url: String,
        val type: String,
        val sub_title: String,
        val status: String
    )

    private fun convert63(jo: JSONObject, ID: Int): Data63 {
        return Data63(
            ID,
            jo.getString("id"),
            jo.getString("obj_id"),
            jo.getString("title"),
            jo.getString("cover"),
            jo.getString("url"),
            jo.getString("type"),
            jo.getString("sub_title"),
            jo.getString("status")
        )
    }

    @Composable
    fun Compose63(content: String) {
        Surface(
            modifier = Modifier.padding(horizontal = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            val data = JSONObject(content).getJSONArray("data")
            Column {
                Text(
                    text = "即将动画化",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    val weight = 10f
                    val spaceWeight = 1f
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose63Child(
                        data63 = convert63(data.getJSONObject(0), 0),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose63Child(
                        data63 = convert63(data.getJSONObject(1), 1),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose63Child(
                        data63 = convert63(data.getJSONObject(2), 2),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    val weight = 10f
                    val spaceWeight = 1f
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose63Child(
                        data63 = convert63(data.getJSONObject(3), 3),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose63Child(
                        data63 = convert63(data.getJSONObject(4), 4),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                    Compose63Child(
                        data63 = convert63(data.getJSONObject(5), 5),
                        modifier = Modifier.weight(weight)
                    )
                    Spacer(modifier = Modifier.weight(spaceWeight))
                }
            }
        }
    }

    @Composable
    fun Compose63Child(data63: Data63, modifier: Modifier = Modifier) {
        val split = data63.cover.split(".")
        val path = "${context.filesDir}/novel/recommend_63_${data63.ID}.${split[split.size - 1]}"
        Column(modifier = modifier) {
            Row {
                Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) {
                    Image(
                        bitmap = WriteFiles.getBitMap(data63.cover, path).asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(text = data63.title, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                text = data63.status,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}
