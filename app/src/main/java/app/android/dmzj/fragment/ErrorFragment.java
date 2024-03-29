package app.android.dmzj.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.android.dmzj.R;

public class ErrorFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle =  getArguments();
        View view = inflater.inflate(R.layout.fragment_error, container, false);
        TextView textView = view.findViewById(R.id.Test);

        if(bundle!=null) {
            textView.setTextSize(23);
            textView.setTextColor(Color.RED);
            textView.setText("发生错误" + bundle.getString("error"));
        }else {
            textView.setText("加载中");

        }
        return view;
    }
}
