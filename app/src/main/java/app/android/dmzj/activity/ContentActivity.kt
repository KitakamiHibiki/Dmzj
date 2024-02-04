package app.android.dmzj.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import app.android.dmzj.compose.comic.ComicCommentCompose
import app.android.dmzj.compose.comic.ComicDownloadCompose
import app.android.dmzj.compose.comic.ComicHistoryCompose
import app.android.dmzj.compose.comic.ComicSubScribeCompose
import app.android.dmzj.compose.novel.NovelCommentCompose
import app.android.dmzj.compose.novel.NovelDownloadCompose
import app.android.dmzj.compose.novel.NovelHistoryCompose
import app.android.dmzj.compose.novel.NovelSubScribeCompose

class ContentActivity : AppCompatActivity() {
    companion object {
        val TestCompose = "100"
        val Comic = "101"
        val Novel = "102"
        val ComicSubscribe = Comic + "1"
        val ComicHistory = Comic + "2"
        val ComicComment = Comic + "3"
        val ComicDownLoad = Comic + "4"
        val NovelSubscribe = Novel + "1"
        val NovelHistory = Novel + "2"
        val NovelComment = Novel + "3"
        val NovelDownload = Novel + "4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.getBundleExtra("data")
        val whichCompose = bundle?.getString("whichCompose")
        setContent {
            when (whichCompose) {
                TestCompose -> Test()
                ComicSubscribe -> ComicSubScribeCompose(activity = this)
                ComicHistory-> ComicHistoryCompose(activity = this)
                ComicComment-> ComicCommentCompose(activity = this)
                ComicDownLoad-> ComicDownloadCompose(activity = this)
                NovelSubscribe-> NovelSubScribeCompose(activity = this)
                NovelHistory-> NovelHistoryCompose(activity = this)
                NovelComment-> NovelCommentCompose(activity = this)
                NovelDownload-> NovelDownloadCompose(activity = this)
            }
        }
    }

    @Composable
    fun Test() {
        Text(text = "这是测试Activity")
    }
}