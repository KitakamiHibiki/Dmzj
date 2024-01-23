package app.android.dmzj.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.dmzj.service.UserService
import app.android.dmzj.ui.theme.DmzjTheme
import java.util.Random

class Login : AppCompatActivity() {
    private val randomBackgroundNumber: Int
    private val randomTool: Random = Random()
    private var randomBackgroundString: String = ""
    private val SET_ERROR_MESSAGE=10001
    private val LOGIN_SUCCSESS=10002
    var userName = mutableStateOf("")
    var password = mutableStateOf("")
    var errorText = mutableStateOf("")
    private val handler = Handler(Handler.Callback { message: Message ->
        when(message.what){
            SET_ERROR_MESSAGE->errorText.value=message.obj.toString()
            LOGIN_SUCCSESS->{
                val intent = Intent(this,Main::class.java)
                startActivity(intent)
                finish()
            }
        }
        return@Callback true
    })


    init {
        randomBackgroundNumber = randomTool.nextInt(35)
        randomBackgroundString = if (randomBackgroundNumber < 10) {
            "0$randomBackgroundNumber"
        } else {
            randomBackgroundNumber.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Greeting() {
        val userNameScroll = rememberScrollState()
        val passwordScroll = rememberScrollState()
        DmzjTheme {
            Box {
                Image(
                    painter = painterResource(
                        id = baseContext.resources.getIdentifier(
                            "m_" + randomBackgroundString + "_aos",
                            "drawable",
                            packageName
                        )
                    ),
//                    painter = painterResource(id = R.drawable.m_01_aos),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column {
                    Spacer(modifier = Modifier.height(100.dp))
                    //登录 两个字
                    Row {
                        Text(
                            text = "登 录",
                            maxLines = 1,
                            modifier = Modifier
                                .height(50.dp)
                                .width(1000.dp),
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                    //User_Name
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        TextField(
                            value = userName.value,
                            label = { Text(text = "User_Name") },
                            onValueChange = {
                                userName.value = it
                                if (userName.value.contains("\n"))
                                    userName.value =
                                        userName.value.substring(0, userName.value.length - 1)
                            },
                            maxLines = 1,
                            modifier = Modifier
                                .alpha(0.7f)
                                .width(300.dp)
                                .horizontalScroll(userNameScroll),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Cyan,
                                unfocusedIndicatorColor = Color.Cyan,
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    //Password
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        TextField(
                            value = password.value,
                            label = { Text(text = "Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            onValueChange = {
                                password.value = it
                                if (password.value.contains("\n"))
                                    password.value =
                                        password.value.substring(0, password.value.length - 1)
                            },
                            maxLines = 1,
                            modifier = Modifier
                                .alpha(0.7f)
                                .width(300.dp)
                                .horizontalScroll(passwordScroll),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Cyan,
                                unfocusedIndicatorColor = Color.Cyan,
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    //登录按钮
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = Color.Transparent,
                            modifier = Modifier
                                .border(2.dp, Color.Cyan, MaterialTheme.shapes.medium)
                                .height(40.dp)
                                .width(100.dp)
                                .clickable {
                                    //登录按钮点击事件
                                    UserService
                                        .Login(userName.value, password.value,this@Login, handler)
                                        .start()
                                }
                        ) {
                            Text(
                                text = "Login",
                                modifier = Modifier
                                    .padding(all = 4.dp),
                                maxLines = 1,
                                fontSize = 23.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Cyan
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = errorText.value, color = Color.Red, fontSize = 23.sp)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}