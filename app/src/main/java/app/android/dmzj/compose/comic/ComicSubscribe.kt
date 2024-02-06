package app.android.dmzj.compose.comic

import android.app.Activity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.android.dmzj.request.user.comicSubscribes
import org.json.JSONArray
import org.json.JSONObject

class ComicSubscribeClass(val activity: Activity) {
    val NowPage = 0
    var list = mutableStateListOf<JSONObject>()

    init {
        Thread {
            var jsonArray = JSONArray(comicSubscribes(1, NowPage, "all"))
            for (a in 0 until jsonArray.length()) {
                list.add(jsonArray.getJSONObject(a))
            }
        }.start()
    }

    @Composable
    fun ComicSubScribeCompose() {
        Column {
            TopBar()
            Column(Modifier.verticalScroll(ScrollState(0))) {
                for (a in list) {
                    Text(text = a.toString())
                }
            }
        }

    }

    @Composable
    fun TopBar() {
        Row(
            modifier = Modifier
                .height(50.dp)
                .background(app.android.dmzj.ui.theme.Violet)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "asdd")
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
