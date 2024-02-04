package app.android.dmzj.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import app.android.dmzj.R
import app.android.dmzj.compose.Pager
import app.android.dmzj.compose.comic.ComicCommentCompose
import app.android.dmzj.compose.comic.ComicDownloadCompose
import app.android.dmzj.compose.comic.ComicHistoryCompose
import app.android.dmzj.compose.comic.ComicSubScribeCompose
import app.android.dmzj.compose.novel.NovelCommentCompose
import app.android.dmzj.compose.novel.NovelDownloadCompose
import app.android.dmzj.compose.novel.NovelHistoryCompose
import app.android.dmzj.compose.novel.NovelSubScribeCompose
import kotlinx.coroutines.launch
import java.util.ArrayList

class ContentActivity : AppCompatActivity() {

    companion object {
        const val TestCompose = "100"
        private const val Comic = "101"
        private const val Novel = "102"
        const val ComicSubscribe = Comic + "1"
        const val ComicHistory = Comic + "2"
        const val ComicComment = Comic + "3"
        const val ComicDownLoad = Comic + "4"
        const val NovelSubscribe = Novel + "1"
        const val NovelHistory = Novel + "2"
        const val NovelComment = Novel + "3"
        const val NovelDownload = Novel + "4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.getBundleExtra("data")
        val whichCompose = bundle?.getString("whichCompose")
        setContent {
            when (whichCompose) {
                TestCompose -> Test()
                ComicSubscribe -> ComicSubScribeCompose(activity = this)
                ComicHistory -> ComicHistoryCompose(activity = this)
                ComicComment -> ComicCommentCompose(activity = this)
                ComicDownLoad -> ComicDownloadCompose(activity = this)
                NovelSubscribe -> NovelSubScribeCompose(activity = this)
                NovelHistory -> NovelHistoryCompose(activity = this)
                NovelComment -> NovelCommentCompose(activity = this)
                NovelDownload -> NovelDownloadCompose(activity = this)
            }
        }
    }

    @Composable
    fun Test() {
        val scope = rememberCoroutineScope()
        Column {
            Text("Test")
            val list: ArrayList<ImageBitmap> = ArrayList()
            list.add(BitmapFactory.decodeResource(resources, R.drawable.p_1).asImageBitmap())
            list.add(BitmapFactory.decodeResource(resources, R.drawable.p_2).asImageBitmap())
            list.add(BitmapFactory.decodeResource(resources, R.drawable.p_3).asImageBitmap())
            list.add(BitmapFactory.decodeResource(resources, R.drawable.p_4).asImageBitmap())
            val pa = Pager(list)
            pa.PagerView(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(200.dp)
            )
            Button(onClick = { scope.launch { pa.addView() } }) {
                Text(text = "addView")
            }
            Button(onClick = { scope.launch { pa.subView() } }) {
                Text(text = "subView")
            }
        }
    }
}

