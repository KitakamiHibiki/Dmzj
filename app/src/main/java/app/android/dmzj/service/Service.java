package app.android.dmzj.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Service {
    public static void sendMessage(Handler handler, int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    public static void startActivity(Context start, Class<?> to, String whichCompose){
        Intent intent = new Intent(start,to);
        Bundle bundle = new Bundle();
        bundle.putString("whichCompose",whichCompose);
        intent.putExtra("data",bundle);
        start.startActivity(intent);
    }

    public static void startActivity(Context start, Class<?> to){
        Intent intent = new Intent(start,to);
        start.startActivity(intent);
    }
}
