package app.android.dmzj.compose.novel

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.dmzj.activity.ContentActivity
import app.android.dmzj.compose.comic.ComicSubscribe
import app.android.dmzj.compose.comic.TopBar
import app.android.dmzj.request.WriteFiles
import app.android.dmzj.request.novel.Request_Novel.novelSubscribes
import app.android.dmzj.service.Service
import app.android.dmzj.ui.theme.Orange
import kotlinx.coroutines.InternalCoroutinesApi
import org.json.JSONArray
import org.json.JSONObject

class NovelSubscribe(val activity: Activity) : Runnable {
    var nowPage = -1
    var list = mutableStateListOf<JSONObject>()
    var onLoad = false
    var isThreadAlive = mutableStateOf(false)
    var noMoreData = false
    var state = @Composable { rememberLazyGridState() }

    init {
        Thread(this).start()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun NovelSubScribeCompose() {
        val lazyGridState = state()
        var refreshing by remember { mutableStateOf(false) }
//        val scope = rememberCoroutineScope()
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { reset(); Thread(this).start() })

        Column {
            TopBar(title = "小说订阅", activity = activity)
            Box(Modifier.pullRefresh(pullRefreshState)) {
                DataShow(list = list, activity, lazyGridState)
                if (lazyGridState.isScrollInProgress && lazyGridState.firstVisibleItemScrollOffset == 0)
                    PullRefreshIndicator(
                        refreshing,
                        pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
            }
        }
        LaunchedEffect(isThreadAlive.value) {
            if (!isThreadAlive.value)
                Thread(LoadThread(this@NovelSubscribe, lazyGridState)).start()
            isThreadAlive.value = true
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun run() {
        //Load More Data
        kotlinx.coroutines.internal.synchronized(this) {
            addIntoList(loadNextPage())
        }
    }

    fun addIntoList(jsonArray: JSONArray?) {
        if (jsonArray == null) return
        for (a in 0 until jsonArray.length()) {
            list.add(jsonArray.getJSONObject(a))
        }
        onLoad = false
    }

    fun loadNextPage(): JSONArray? {
        if (onLoad) return null
        if (noMoreData) return null
        onLoad = true
        nowPage += 1
        val ja = JSONArray(novelSubscribes("1", "all", nowPage.toString()))
        if (ja.length() == 0) noMoreData = true
        return ja
    }

    fun reset() {
        nowPage = -1
        list.clear()
        onLoad = false
        isThreadAlive.value = false
        noMoreData = false
    }

}

private class LoadThread(val novelSubscribe: NovelSubscribe, val state: LazyGridState) :
    Runnable {
    override fun run() {
        val kClass = state.javaClass
        val fields = kClass.declaredFields
        while (!novelSubscribe.activity.isDestroyed) {
            if (state.isScrollInProgress) {
                for (field in fields) {
                    field.isAccessible = true
                    if (field.name == "canScrollBackward") {
                        if (field.get(state) == true)
                            if (!novelSubscribe.onLoad)
                                Thread(novelSubscribe).start()
                        break
                    }
                }
            }
        }
        novelSubscribe.reset()
    }
}

@Composable
fun DataShow(
    list: SnapshotStateList<JSONObject>,
    context: Activity,
    state: LazyGridState,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = state,
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(list.size) { index ->
            DataCard(data = list[index], context)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataCard(data: JSONObject, context: Activity) {
    val split = data.getString("sub_img").split(".")
    val path = "${context.filesDir.path}/novel/${data.getString("id")}.${split[split.size - 1]}"
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(120.dp)
            .height(200.dp)
            .padding(horizontal = 5.dp, vertical = 10.dp),
        shadowElevation = 2.dp,
        onClick = {
//            val bundle = Bundle()
//            bundle.putString("Comic_Id", data.getString("id"))
//            Service.startActivity(
//                context,
//                ContentActivity::class.java,
//                ContentActivity.ComicDetail,
//                bundle
//            )
        }
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
                            modifier = Modifier.background(Orange),
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