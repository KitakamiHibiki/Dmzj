package app.android.dmzj.Tools;

import androidx.annotation.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

public class Tools {
    public static String getContent(String url, HashMap<String, String> HM) throws Exception {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            StringBuilder content = new StringBuilder();
            if (HM != null && HM.size() != 0) {
                Object[] key = HM.keySet().toArray();
                for (Object a : key) {
                    content.append("&");
                    content.append(URLEncoder.encode((String) a));
                    content.append("=");
                    content.append(URLEncoder.encode(HM.get(a)));
                }
                content = new StringBuilder(content.substring(1));
            }
            OutputStream out = conn.getOutputStream();
            out.write(content.toString().getBytes(StandardCharsets.UTF_8));
            InputStream in = conn.getInputStream();
            String Data = "";
            byte[] bytes = new byte[1024];
            int len = in.read(bytes);
            while (len != -1) {
                Data += new String(bytes, 0, len);
                len = in.read(bytes);
            }
            return Data;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.toString());
        }
    }

    public static String UpLoadFile(String url, HashMap<String, String> HM, File file) {
        String boundary = UUID.randomUUID().toString().replace("-", "");
        String start = "--" + boundary + "\r\nContent-Disposition: form-data; name=";
        String end = "--" + boundary + "--";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream out = conn.getOutputStream();
            if (HM != null && HM.size() != 0) {
                Object[] key = HM.keySet().toArray();
                for (Object a : key) {
                    out.write((start + "\"" + a.toString() + "\"\r\n\r\n" + HM.get(a) + "\r\n").getBytes(StandardCharsets.UTF_8));
                }
            }
            out.write((start + "\"file\"; filename=\"" + file.getPath() + "\"" + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
            FileInputStream fin = new FileInputStream(file);
            byte[] b = new byte[1024];
            int len = fin.read(b);
            while (len != -1) {
                byte[] bb = new byte[len];
                System.arraycopy(b, 0, bb, 0, len);
                out.write(bb);
                len = fin.read(b);
            }
            out.write(("\r\n" + end).getBytes(StandardCharsets.UTF_8));
            InputStream in = conn.getInputStream();
            String Data = "";
            byte[] bytes = new byte[1024];
            len = in.read(bytes);
            while (len != -1) {
                Data += new String(bytes, 0, len);
                len = in.read(bytes);
            }
            fin.close();
            return Data;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Nullable
    public static File getFile(String url, String savePath) {
        try {
            File file = new File(savePath);
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            InputStream in = conn.getInputStream();
            FileOutputStream FOut = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = in.read(bytes);
            while (len != -1) {
                FOut.write(bytes, 0, len);
                len = in.read(bytes);
            }
            FOut.close();
            in.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
