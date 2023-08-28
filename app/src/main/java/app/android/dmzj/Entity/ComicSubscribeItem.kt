package app.android.dmzj.Entity

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import app.android.dmzj.Request.Tools.Tools
import org.json.JSONObject
import java.io.File

class ComicSubscribeItem(jo:JSONObject, context:Context) {
    val Jo = jo
    val sub_img:String
    val sub_first_letter:String
    val name:String
    val sub_uptime:Int
    val sub_readed:Int
    val id:Int
    val sub_update:String
    val status:String
    lateinit var imageBitmap:ImageBitmap

    init {
        sub_img = jo.getString("sub_img")
        sub_first_letter = jo.getString("sub_first_letter")
        name = jo.getString("name")
        sub_uptime = jo.getInt("sub_uptime")
        sub_readed = jo.getInt("sub_readed")
        id = jo.getInt("id")
        sub_update = jo.getString("sub_update")
        status = jo.getString("status")
        val split = sub_img.split(".")
        if(!File(context.cacheDir.path+"/Images").exists()){
            File(context.cacheDir.path+"/Images").mkdir()
        }
        val imageFilePath=context.cacheDir.path+"/Images/"+"Comic_${id}.${split[split.size-1]}"
        val file = File(imageFilePath)
        if(file.exists()){
            imageBitmap=BitmapFactory.decodeFile(imageFilePath).asImageBitmap()
        }else{
            val t1=Thread {
                Tools.getFile(sub_img, imageFilePath)
                imageBitmap = BitmapFactory.decodeFile(imageFilePath).asImageBitmap()
            }
            t1.start()
            t1.join()
        }
    }

    override fun toString(): String {
        return Jo.toString()
    }
}