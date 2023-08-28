package app.android.dmzj.Activity.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.dmzj.Request.GetUserInfo
import app.android.dmzj.Request.LoginCommit
import app.android.dmzj.ui.theme.DmzjTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.Random

class Login : AppCompatActivity() {
    private val randomBackgroundNumber: Int
    private val randomTool: Random = Random()
    private var randomBackgroundString: String = ""

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
        var userName by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
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
                            value = userName,
                            label = { Text(text = "User_Name") },
                            onValueChange = {
                                userName = it
                                if (userName.contains("\n"))
                                    userName = userName.substring(0, userName.length - 1)
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
                            value = password,
                            label = { Text(text = "Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            onValueChange = {
                                password = it
                                if (password.contains("\n"))
                                    password = password.substring(0, password.length - 1)
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
                                    val a = Observable
                                        .create<String> { emitter ->
                                            run {
                                                try {
                                                    //获取基本内容: nickname,password,uid
                                                    val result: String =
                                                        LoginCommit(userName, password)
                                                    val json = JSONObject(result)
                                                    if (result.contains("error")) {
                                                        throw Exception(json.getString("error"))
                                                    } else {
                                                        var file =
                                                            File(filesDir.path + "/User.json")
                                                        var fOut = FileOutputStream(file)
                                                        json.put("nickname", userName)
                                                        json.put("password", password)
                                                        fOut.write(
                                                            json
                                                                .toString()
                                                                .toByteArray()
                                                        )
                                                        fOut.close()
                                                        //获取用户具体信息
                                                        val jo = JSONObject(GetUserInfo(this@Login))

                                                        json.put(
                                                            "userName",
                                                            jo.getString("nickname")
                                                        )
                                                        json.put(
                                                            "photo",
                                                            jo.getString("cover")
                                                        )
                                                        json.put(
                                                            "description",
                                                            jo.getString("description")
                                                        )
                                                        fOut = FileOutputStream(file)
                                                        fOut.write(
                                                            json
                                                                .toString()
                                                                .toByteArray()
                                                        )
                                                        fOut.close()
                                                        emitter.onNext("1")
                                                    }
                                                } catch (Ex: Exception) {
                                                    emitter.onError(Ex)
                                                }
                                            }
                                        }
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe({ t ->
                                            if (t.equals("1")) {
                                                setResult(1)
                                                finish()
                                            }
                                        }, {
                                            it.printStackTrace()
                                            Toast
                                                .makeText(
                                                    baseContext,
                                                    it.message,
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }, {

                                        })
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
                }
            }
        }

    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        Greeting()
    }
}