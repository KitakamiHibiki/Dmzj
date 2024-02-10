package app.android.dmzj.compose.comic

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import app.android.dmzj.fragment.userInfo.User
import app.android.dmzj.request.comic.Request_Comic
import kotlinx.coroutines.InternalCoroutinesApi

class ComicDetail(val activity: Activity) {
    val bundle: Bundle
    val Data = mutableStateOf("")
    val isInit = mutableStateOf(false)

    init {
        bundle = activity.intent.getBundleExtra("data")!!.getBundle("prams")!!
    }

    @Composable
    fun ComicDetailCompose() {
        Column {
            Text(text = bundle.getString("Comic_Id")!!)
            Text(text = User.user.uid)
            Text(text = Data.value)
        }
        LaunchedEffect(isInit.value){
            loadData(bundle.getString("Comic_Id")!!, Data).start()
            isInit.value=true
        }
    }
}

private class loadData(val comic_id: String, val data: MutableState<String>) : Thread() {
    @OptIn(InternalCoroutinesApi::class)
    override fun run() {
        kotlinx.coroutines.internal.synchronized(data) {
            data.value = Request_Comic.comicDetail(comic_id)
        }
    }
}