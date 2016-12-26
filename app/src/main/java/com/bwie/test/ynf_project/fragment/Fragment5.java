package com.bwie.test.ynf_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bwie.test.ynf_project.activity.MainActivity;
import com.bwie.test.ynf_project.R;

/**
 * Created by Administrator on 2016/11/27.
 */
public class Fragment5 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page5, null);
       LinearLayout line = (LinearLayout) view.findViewById(R.id.line);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);

                startActivity(intent);
            }
        });
        return view;
    }
}
