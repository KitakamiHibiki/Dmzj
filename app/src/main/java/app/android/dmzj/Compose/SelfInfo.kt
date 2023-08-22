package app.android.dmzj.Compose

import android.app.Activity
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.dmzj.Bean.User
import app.android.dmzj.R
import app.android.dmzj.Tools.Tools
import java.io.File

class SelfInfo(Context: Activity) {
    private val context: Activity
    private val user: User

    init {
        context = Context
        user = User(context)
    }

    @Composable
    fun MainCompose() {
        Column(modifier = Modifier.padding(30.dp, 0.dp)) {
            Head()
            Spacer(modifier = Modifier.height(20.dp))
            MyAction()
            Spacer(modifier = Modifier.height(20.dp))
            MyDownload()
        }
    }

    @Composable
    fun Head() {
        val headImage = BitmapFactory.decodeFile(
            context.cacheDir.path + "/HeadImage.${
                user.photo.split(".")[user.photo.split(".").size - 1]
            }"
        )
        Spacer(modifier = Modifier.height(30.dp))
        Surface(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(10.dp)
                .width(500.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Surface(
                    modifier = Modifier
                        .width(45.dp)
                        .height(45.dp),
                    shape = CircleShape,
                ) {
                    Image(
                        bitmap = headImage.asImageBitmap(),
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(text = user.userName, fontSize = 17.sp)
                    Text(text = user.description, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(110.dp))
                Column {
                    Spacer(modifier = Modifier.height(7.dp))
                    Surface(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable {
                                val loginFile = File(context.filesDir.path + "/User.json")
                                loginFile.delete()
                                context.finish()
                            },
                        shape = CircleShape,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = null,
                        )
                    }
                }
            }

        }

    }

    @Composable
    fun MyAction() {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            //我的漫画订阅
            MessageCard(R.drawable.baseline_favorite_border_24, "我的漫画订阅") {
                println("我的漫画订阅")
                Thread {
                    val HM = HashMap<String, String>()
                    HM["Cookie"] = user.cookie
                    println(Tools.getContent("http://192.168.1.225:8880/Comic/myLoveManga", HM))
                }.start()
            }
            //我的小说订阅
            MessageCard(
                R.drawable.baseline_favorite_border_24,
                "我的小说订阅"
            ) { println("我的小说订阅") }
            //漫画浏览记录
            MessageCard(R.drawable.baseline_history_24, "漫画浏览记录") { println("漫画浏览记录") }
            //小说浏览记录
            MessageCard(R.drawable.baseline_history_24, "小说浏览记录") { println("小说浏览记录") }
        }
    }

    @Composable
    fun MyDownload() {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            //漫画下载
            MessageCard(R.drawable.baseline_download_24, "漫画下载       ") { println("漫画下载") }
            //小说下载
            MessageCard(R.drawable.baseline_download_24, "小说下载       ") { println("小说下载") }
        }
    }

    @Composable
    fun MessageCard(Image: Int, Title: String, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
                .clickable(
                    onClick = { onClick() },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
        ) {
            Image(
                painter = painterResource(id = Image),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = Title, fontSize = 18.sp, textAlign = TextAlign.Left, modifier = Modifier
//                    .width(250.dp)
                    .padding(0.dp, 3.dp)
            )
            Spacer(modifier = Modifier.width(125.dp))
            Column {
                Spacer(modifier = Modifier.height(5.dp))
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(125.dp))
        }
    }

}