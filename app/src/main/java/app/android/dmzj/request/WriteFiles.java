package app.android.dmzj.request;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class WriteFiles {
    public static File writeJson(String content,String path) throws Exception{
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        return file;
    }

    public static File writePictures(byte[] picBytes,String path)throws Exception{
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(picBytes);
        return file;
    }
}
