package app.android.dmzj.service;

import static app.android.dmzj.service.Service.sendMessage;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;

import app.android.dmzj.activity.Main;
import app.android.dmzj.request.comic.Request_Comic;

public class ComicService {
    public static class getComicRecommend extends Thread {
        private final Handler handler;

        public getComicRecommend(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                sendMessage(handler,102, new JSONArray(Request_Comic.requestComicRecommend()).toString());
            } catch (Exception e) {
                e.printStackTrace();
                if(Main.Companion.getNowFragment()==0)
                    sendMessage(handler, 101, "[" + e.getClass().getName() + "]" + e.getMessage());
            }
        }
    }
}
