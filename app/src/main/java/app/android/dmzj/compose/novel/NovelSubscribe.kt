package app.android.dmzj.compose.novel

import android.app.Activity
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import app.android.dmzj.request.novel.Request_Novel
import app.android.dmzj.request.novel.Request_Novel.novelSubscribes
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
    var data = mutableStateOf("")

    init {
        Thread(this).start()
    }

    @Composable
    fun NovelSubScribeCompose() {
        Text(text = data.value)
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun run() {
        //Load More Data
        kotlinx.coroutines.internal.synchronized(this) {
//            addIntoList(loadNextPage())
            data.value = loadNextPage().toString()
        }
    }

    fun addIntoList(jsonArray: JSONArray) {

    }

    fun loadNextPage(): JSONArray? {
        if (onLoad) return null
        if (noMoreData) return null
        onLoad = true
        nowPage += 1
        val ja = JSONArray(novelSubscribes("1","all",nowPage.toString()))
        if (ja.length() == 0) noMoreData = true
        return JSONArray()
    }

}

