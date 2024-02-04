package app.android.dmzj.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteFiles {
    @NonNull
    public static File writeJson(String content, String path) throws Exception{
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        return file;
    }

    @NonNull
    public static File writePictures(byte[] picBytes, String path)throws Exception{
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(picBytes);
        return file;
    }

    public static Bitmap getBitMap(String url,String path) throws Exception{
        File file = new File(path);
        if(file.exists()){
            BasicFileAttributes attrs = Files.readAttributes(Paths.get(file.getAbsolutePath()), BasicFileAttributes.class);
            FileTime fileTime = attrs.creationTime();
            Date date = new Date(fileTime.toMillis());
            Date today = new Date();
            if(date.getYear()==today.getYear()&&date.getMonth()==today.getMonth()&&date.getDay()==today.getDay()){
                return BitmapFactory.decodeFile(path);
            }
        }
        //网络获取
        Thread thread = new Thread(() -> {
            try {
                writePictures(NetConnection.getPictures(url,null,null),path);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        thread.join();
        return BitmapFactory.decodeFile(path);
    }

    public static Bitmap getBitMapOverWriteFile(String url,String path) throws Exception{
        Thread thread = new Thread(() -> {
            try {
                writePictures(NetConnection.getPictures(url,null,null),path);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        thread.join();
        return BitmapFactory.decodeFile(path);
    }

}
