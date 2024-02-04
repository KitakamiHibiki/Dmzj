package app.android.dmzj.fragment.userInfo;

import android.app.Activity;
import android.os.Bundle;

import androidx.compose.ui.platform.ComposeView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.android.dmzj.R;
import app.android.dmzj.activity.Main;

public class UserInfoFragment extends Fragment {
    private Main parentActivity;
    private UserUI userUI;

    public UserInfoFragment(Main parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ComposeView composeView = view.findViewById(R.id.compose);
        userUI = new UserUI(parentActivity);
        userUI.setCompose(composeView);
        return view;
    }
}