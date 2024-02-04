package app.android.dmzj.fragment.novel;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.compose.ui.platform.ComposeView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.android.dmzj.R;
import app.android.dmzj.activity.Main;
import app.android.dmzj.request.NetConnection;
import app.android.dmzj.request.WriteFiles;
import app.android.dmzj.service.Service;

public class NovelRecommendFragment extends Fragment {
    private final Main parentActivity;
    private ComposeView composeView;
    private ViewPager viewPager;
    public static final int SET_COMPOSE_VIEW = 101;
    public static final int SET_VIEW_PAGER_ADAPTER = 102;
    public static final int ERROR = 103;
    public static final int CHANGE_VIEW_PAGER = 104;
    private final Handler handler;
    private static boolean isAlive = true;
    public final NovelUI novelUI;

    /*
     * https://nnv3api.idmzj.com/novel/recommend.json
     * BASE_URL_V3+"/novel/recommend.json"
     * category_id:57    轮番图
     * category_id:58    最新更新
     * category_id:60    动画进行时
     * category_id:62    即将动画化
     * category_id:63    经典必看
     * */

    public NovelRecommendFragment(Main parentActivity) {
        this.parentActivity = parentActivity;
        this.novelUI = new NovelUI(this.parentActivity.getBaseContext());
        handler = new Handler(message -> {
            switch (message.what) {
                case SET_COMPOSE_VIEW:
                    novelUI.setCompose(composeView, NovelUI.RecommendCompose, message.obj.toString());
                    break;
                case SET_VIEW_PAGER_ADAPTER:
                    viewPager.setAdapter((ViewPagerAdapter) message.obj);
                    break;
                case ERROR:
                    Service.sendMessage(parentActivity.getHandler(), Main.CHANGE_FRAGMENT_TO_ERROR, message.obj);
                    break;
                case CHANGE_VIEW_PAGER:
                    viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%(int)message.obj);
                    break;
            }
            return true;
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic_recommend, container, false);
        composeView = view.findViewById(R.id.compose);
        viewPager = view.findViewById(R.id.viewPager);
        new Thread(new GetRecommend(this)).start();
        return view;
    }

    private static class GetRecommend implements Runnable {
        String api = app.android.dmzj.request.api.BASE_URL_V3 + "/novel/recommend.json";
        NovelRecommendFragment fragment;
        String content;

        public GetRecommend(NovelRecommendFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void run() {
            try {
                //获取
                content = NetConnection.getJson(api, null, null);
                JSONArray jsonArray = new JSONArray(content);
                // 传入Compose
                Service.sendMessage(fragment.handler, SET_COMPOSE_VIEW, jsonArray);
                JSONObject jsonObject = null;
                for (int a = 0; a < jsonArray.length(); a += 1) {
                    if (jsonArray.getJSONObject(a).getString("category_id").equals("57")) {
                        jsonObject = jsonArray.getJSONObject(a);
                        break;
                    }
                }
                //解析出轮番图并写入url
                if (jsonObject == null)
                    throw new Exception("数据加载错误");
                JSONArray category57 = jsonObject.getJSONArray("data");
                //填充数据列表
                ArrayList<JSONObject> category57dataList = new ArrayList<>();
                for (int a = 0; a < category57.length(); a += 1) {
                    category57dataList.add(category57.getJSONObject(a));
                }
                //设置适配器
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragment, category57dataList);
                Service.sendMessage(fragment.handler, SET_VIEW_PAGER_ADAPTER, viewPagerAdapter);
                //定时轮播图片
                new Thread(()->{
                    try{
                        while (isAlive){
                            Thread.sleep(3000);
                            Service.sendMessage(fragment.handler,CHANGE_VIEW_PAGER,category57dataList.size());
                        }
                    }catch (Exception exception){
                        exception.printStackTrace();
                        Service.sendMessage(fragment.handler, ERROR, exception.getMessage());
                    }
                }).start();
            } catch (Exception exception) {
                exception.printStackTrace();
                if(Main.Companion.getNowFragment()==1)
                    Service.sendMessage(fragment.handler, ERROR, exception.getMessage());
            }
        }
    }

    private static class ViewPagerAdapter extends PagerAdapter {
        List<JSONObject> list;
        NovelRecommendFragment fragment;
        LayoutInflater inflater;

        public ViewPagerAdapter(NovelRecommendFragment fragment, List<JSONObject> list) {
            this.list = list;
            this.fragment = fragment;
            this.inflater = LayoutInflater.from(fragment.parentActivity);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.pager_view_layout, container, false);
            ImageView imageView = view.findViewById(R.id.pagerImage);

            try {
                JSONObject jsonObject = list.get(position);
                String id = jsonObject.getString("id");
                String obj_id = jsonObject.getString("obj_id");
                String title = jsonObject.getString("title");
                String cover = jsonObject.getString("cover");
                String url = jsonObject.getString("url");
                String type = jsonObject.getString("type");
                String sub_title = jsonObject.getString("sub_title");
                String status = jsonObject.getString("status");
                String[] split = cover.split("\\.");
                String path = fragment.parentActivity.getFilesDir().getPath() + "/novel/recommend_57_" + position + "." + split[split.length - 1];
                imageView.setImageBitmap(WriteFiles.getBitMap(cover, path));
                container.addView(view);
            } catch (Exception exception) {
                exception.printStackTrace();
                Service.sendMessage(fragment.handler, ERROR, exception);
            }
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onDestroy() {
        isAlive = false;
        super.onDestroy();
    }
}