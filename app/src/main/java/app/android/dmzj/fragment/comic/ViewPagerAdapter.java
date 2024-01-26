package app.android.dmzj.fragment.comic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.android.dmzj.R;
import app.android.dmzj.request.WriteFiles;

public class ViewPagerAdapter extends PagerAdapter {
    private final List<JSONObject> picUrl;
    private final LayoutInflater inflater;
    private final String basicPath;

    public static void setAdapter(ViewPager viewPager,PagerAdapter pagerAdapter){
        viewPager.setAdapter(pagerAdapter);
    }

    public ViewPagerAdapter(Context context, List<JSONObject> list) {
        this.picUrl = list;
        inflater = LayoutInflater.from(context);
        basicPath = context.getFilesDir().getPath();
    }

    @Override
    public int getCount() {
        return picUrl.size();
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
        String cover;
        String title;
        String sub_title;
        String type;
        String url;
        String obj_id;
        String status;
        String appWxId;
        try {
            cover = picUrl.get(position).getString("cover");
            title = picUrl.get(position).getString("title");
            sub_title = picUrl.get(position).getString("sub_title");
            type = picUrl.get(position).getString("type");
            url = picUrl.get(position).getString("url");
            obj_id = picUrl.get(position).getString("obj_id");
            status = picUrl.get(position).getString("status");
            appWxId = picUrl.get(position).getString("appWxId");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String[] split = cover.split("\\.");
        String path = basicPath+"/comic/recommend_46_"+position+"."+split[split.length-1];
        try {
            imageView.setImageBitmap(WriteFiles.getBitMap(cover,path));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
