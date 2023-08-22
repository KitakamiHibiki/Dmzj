package app.android.dmzj.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.android.dmzj.Compose.Index
import app.android.dmzj.Compose.SelfInfo
import app.android.dmzj.R

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose()
        }
    }

    @Preview(showSystemUi = true, showBackground = true, apiLevel = 29)
    @Composable
    fun Compose() {
        var menuSelect by remember { mutableStateOf(1) }
        //主页面内容
        Column {
            Row(modifier = Modifier.weight(1f)) {
                if (menuSelect == 1) {
                    Index().MainCompose()
                } else if (menuSelect == 2) {
                    SelfInfo(this@Main).MainCompose()

                }
            }
            //底部导航栏
            Row(modifier = Modifier.height(60.dp).background(Color.White)) {
                Image(
                    painter = painterResource(id = if (menuSelect == 1) R.drawable.baseline_menu_book_clicked else R.drawable.baseline_menu_book_unclicked),
                    contentDescription = null,
                    modifier = Modifier
                        .height(60.dp)
                        .padding(0.dp,10.dp)
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            menuSelect = 1
                        }
                )
                Image(
                    painter = painterResource(id = if (menuSelect == 2) R.drawable.baseline_co_present_clicked else R.drawable.baseline_co_present_unclicked),
                    contentDescription = null,
                    modifier = Modifier
                        .height(60.dp)
                        .padding(0.dp,10.dp)
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            menuSelect = 2
                        }
                )
            }
        }
    }
}