package app.android.dmzj.request;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ReadFiles {
    public static String readJson(File file) throws Exception{
        return new String(read(file), StandardCharsets.UTF_8);
    }

    public static byte[] read(File file) throws Exception{
        return NetConnection.readFromInputStream(Files.newInputStream(file.toPath()));
    }
}
