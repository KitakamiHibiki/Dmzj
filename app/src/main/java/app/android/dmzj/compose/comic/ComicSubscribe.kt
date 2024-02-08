package app.android.dmzj.compose.comic

import android.app.Activity
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.dmzj.R
import app.android.dmzj.request.WriteFiles
import app.android.dmzj.request.user.comicSubscribes
import org.json.JSONArray
import org.json.JSONObject

class ComicSubscribeClass(activity_: Activity) {
    companion object{
        private var nowPage = 0
        private var list = mutableStateListOf<JSONObject>()
        private var onLoad = false
        private var activity:Activity? = null
        private var isThreadAlive = mutableStateOf(false)
        private var noMoreData = false

        private fun loadNextPage():JSONArray?{
            if(onLoad)return null
            if(noMoreData)return null
            nowPage+=1
            val ja = JSONArray(comicSubscribes(1, nowPage, "all"))
            if(ja.length()==0) noMoreData=true
            return ja
        }

        private fun addIntoList(jsonArray:JSONArray?){
            if(jsonArray==null)return
            for (a in 0 until jsonArray.length()) {
                list.add(jsonArray.getJSONObject(a))
            }
            onLoad=false
        }
    }

    private var state = @Composable{ rememberLazyGridState()}

    init {
        activity = activity_
        Thread {
            onLoad=true
            val jsonArray = JSONArray(comicSubscribes(1, nowPage, "all"))
            for (a in 0 until jsonArray.length()) {
                list.add(jsonArray.getJSONObject(a))
            }
            onLoad=false
        }.start()
    }

    @Composable
    fun ComicSubScribeCompose() {
        val state = this.state()
        Column {
            TopBar("漫画订阅", activity!!)
            DataShow(list = list, activity!!, state)
        }
        LaunchedEffect(isThreadAlive.value){
            if(!isThreadAlive.value){
                Thread(LoadThread(state)).start()
                isThreadAlive.value=true
            }
        }
    }

    private class LoadThread(val state: LazyGridState):Runnable{
        override fun run() {
            while (!activity!!.isDestroyed) {
                if (state.isScrollInProgress) {
                    val kClass = state.javaClass
                    val fields = kClass.declaredFields
                    for (field in fields) {
                        field.isAccessible = true
                        if (field.name == "canScrollBackward") {
                            if (field.get(state) == true)
                                addIntoList(loadNextPage())
                            break
                        }
                    }
                }
            }
            activity=null
            isThreadAlive.value=false
            nowPage = 0
            list.clear()
            onLoad = false
            noMoreData = false
        }
    }
}

@Composable
fun TopBar(title: String,activity: Activity) {
    Column {
        Box {
            Column(Modifier.size(50.dp).clickable {
                activity.finish()
            }) {
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = null,
                        Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .height(50.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = title, fontSize = 25.sp)
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Composable
fun DataShow(list: SnapshotStateList<JSONObject>, context: Activity,state: LazyGridState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = state,
        modifier = Modifier.padding(10.dp)
    ) {
        items(list.size) { index ->
            DataCard(data = list[index], context)
        }
    }
}

@Composable
fun DataCard(data: JSONObject, context: Activity) {
    val split = data.getString("sub_img").split(".")
    val path = "${context.filesDir.path}/comic/${data.getString("id")}.${split[split.size - 1]}"
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(120.dp)
            .height(200.dp)
            .padding(horizontal = 5.dp, vertical = 10.dp),
        shadowElevation = 2.dp
    ) {
        Column {
            Box(modifier = Modifier.weight(1f)) {
                Image(
                    bitmap = WriteFiles.getBitMap(data.getString("sub_img"), path).asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(1f)
                )
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = " " + data.getString("status") + " ",
                            color = Color.White,
                            modifier = Modifier.background(app.android.dmzj.ui.theme.Orange),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)) {
                Text(text = data.getString("name"), maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(
                    text = "更新 ${data.getString("sub_update")}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}