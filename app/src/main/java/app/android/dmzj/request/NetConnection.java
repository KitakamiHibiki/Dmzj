package app.android.dmzj.request;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class NetConnection {

    public static String getJson(String url, HashMap<String, String> parameters, HashMap<String, String> requestProperty) throws Exception {
        return new String(get(url, parameters, requestProperty), StandardCharsets.UTF_8);
    }

    public static String postJson(String url, HashMap<String, String> parameters, HashMap<String, String> requestProperty) throws Exception {
        return new String(post(url, parameters, requestProperty), StandardCharsets.UTF_8);
    }

    public static byte[] getPictures(String url, HashMap<String, String> parameters, HashMap<String, String> requestProperty) throws Exception {
        return get(url,parameters,requestProperty);
    }

    public static byte[] postPictures(String url, HashMap<String, String> parameters, HashMap<String, String> requestProperty) throws Exception {
        return post(url,parameters,requestProperty);
    }

    public static byte[] get(String url, HashMap<String, String> parameters, HashMap<String, String> requestProperty) throws Exception {
        if (url==null||url.isEmpty())
            throw new Exception("地址不能为空");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        if (parameters!=null) {
            stringBuilder.append("?");
            for (String a : parameters.keySet()) {
                stringBuilder.append(a);
                stringBuilder.append("=");
                stringBuilder.append(parameters.get(a));
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(stringBuilder.toString()).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        if (requestProperty!=null) {
            for (String a : requestProperty.keySet())
                connection.setRequestProperty(a, requestProperty.get(a));
        }
        return readFromInputStream(connection.getInputStream());
    }

    public static byte[] post(String url, HashMap<String, String> parameters, HashMap<String, String> requestProperty) throws Exception {
        if (url==null||url.isEmpty())
            throw new Exception("url不能为空");
        StringBuilder stringBuilder = new StringBuilder();
        if (parameters!=null) {
            for (String a : parameters.keySet()) {
                stringBuilder.append(a);
                stringBuilder.append("=");
                stringBuilder.append(parameters.get(a));
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        if (requestProperty!=null)
            for (String a : requestProperty.keySet())
                connection.setRequestProperty(a, requestProperty.get(a));
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        return readFromInputStream(connection.getInputStream());
    }

    public static byte[] readFromInputStream(InputStream inputStream) throws Exception{
        int buffer_size = 1024;
        int len;
        byte[] bytes = new byte[buffer_size];
        byte[] result = new byte[0];
        do {
            len = inputStream.read(bytes);
            if (len == -1)
                break;
            result = byteCat(result, subBytes(bytes, 0, len));
        } while (true);
        return result;
    }

    public static byte[] byteCat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        int pos, resultPos = 0;
        for (pos = 0; pos < a.length; pos += 1, resultPos += 1) {
            result[resultPos] = a[pos];
        }
        for (pos = 0; pos < b.length; pos += 1, resultPos += 1) {
            result[resultPos] = b[pos];
        }
        return result;
    }

    public static byte[] subBytes(byte[] a, int start, int end) {
        byte[] bytes = new byte[end - start];
        for (int c = start, pos = 0; c < end; c += 1, pos += 1) {
            bytes[pos] = a[c];
        }
        return bytes;
    }
}
