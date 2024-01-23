package app.android.dmzj.service;

import android.os.Handler;
import android.os.Message;

public class Service {
    public static void sendMessage(Handler handler, int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }
}
