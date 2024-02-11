package app.android.dmzj.fragment.userInfo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.dmzj.R
import app.android.dmzj.activity.ContentActivity
import app.android.dmzj.activity.Login
import app.android.dmzj.activity.Main
import app.android.dmzj.request.WriteFiles
import app.android.dmzj.service.Service

class UserUI(val activity: Main) {

    fun setCompose(view: ComposeView) {
        view.setContent {
            UserInterFace()
        }
    }

    @Composable
    fun UserInterFace() {
        var isComic by remember { mutableStateOf(true) }
        val mainScrollState = rememberScrollState()
        val split = User.user.photo.split(".")
        val userHeadImagePath =
            "${activity.filesDir.path}/userInfo/user_head.${split[split.size - 1]}"
        val comicAnimationState = animateDpAsState(
            targetValue = if (isComic) 100.dp else 0.dp,
            animationSpec = tween(1000)
        )
        val novelAnimationState = animateDpAsState(
            targetValue = if (isComic) 0.dp else 100.dp,
            animationSpec = tween(1000)
        )
        Column(modifier = Modifier.verticalScroll(mainScrollState)) {
            Row(modifier = Modifier.height(150.dp)) {
                Surface(modifier = Modifier) {
                    Column {
                        Spacer(modifier = Modifier.weight(1f))
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    bitmap = WriteFiles.getBitMap(
                                        User.user.photo,
                                        userHeadImagePath
                                    ).asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(50.dp)
                                )
                                Text(text = UserProfile.profile.getString("nickname"))
                                Text(text = UserProfile.profile.getString("description"))
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Image(
                        painter = painterResource(id = R.drawable.p_1),
                        contentDescription = null,
                        modifier = Modifier.alpha(0.3f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .shadow(1.dp)
                )
            }
            Row(modifier = Modifier.background(Color.White)) {
                Column(modifier = Modifier
                    .weight(1f)
                    .clickable {
                        isComic = true
                    }) {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "漫画", fontSize = 23.sp)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Spacer(
                            modifier = Modifier
                                .width(comicAnimationState.value)
                                .height(5.dp)
                                .background(Color.Red)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Column(modifier = Modifier
                    .weight(1f)
                    .clickable {
                        isComic = false
                    }) {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "小说", fontSize = 23.sp)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Spacer(
                            modifier = Modifier
                                .width(novelAnimationState.value)
                                .height(5.dp)
                                .background(Color.Red)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Column {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .shadow(1.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Crossfade(targetState = isComic, animationSpec = tween(1000)) {
                if (it)
                    ComicInfo()
                else
                    NovelInfo()
            }
            Spacer(modifier = Modifier.height(10.dp))
            UserInfo()
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    @Composable
    fun ComicInfo() {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Row(modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .clickable {
                    Service.startActivity(
                        activity,
                        ContentActivity::class.java,
                        ContentActivity.ComicSubscribe
                    )
                }
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "漫画订阅",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "浏览历史",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_comment_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "我的评论",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_download_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "漫画下载",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

    @Composable
    fun NovelInfo() {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .clickable {
                        Service.startActivity(
                            activity,
                            ContentActivity::class.java,
                            ContentActivity.NovelSubscribe
                        )
                    }) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "小说订阅",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "浏览历史",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_comment_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "我的评论",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_download_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "小说下载",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

    @Composable
    fun UserInfo() {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Row(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_co_present_unclicked),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "个人信息",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Row(modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .clickable { logout() }) {
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_logout_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "登出账号",
                        fontSize = 23.sp
                    )
                }
                Spacer(modifier = Modifier.width(0.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

    private fun logout() {
        //清除Userprofile.json
        UserProfile.deleteUserProfile(activity.filesDir.path)
        //清除User.json
        User.deleteUser(activity.filesDir.path)
        //跳转至Login_Activity
        Service.startActivity(activity, Login::class.java)
    }

}