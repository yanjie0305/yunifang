package com.bwie.test.ynf_project.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.test.ynf_project.view.ShowingPage;

/**
 * Created by Administrator on 2016/11/28.
 */
public abstract class BaseFragment extends Fragment {

    private ShowingPage showingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showingPage = new ShowingPage(getContext()) {
            @Override
            protected void onload() {
            }

            @Override
            public View createSuccessView() {
                return BaseFragment.this.createSuccessView();
            }
        };
        BaseFragment.this.onload();
        return showingPage;
    }

    public abstract void onload();

    public abstract View createSuccessView();

    public void showCurrentPage(ShowingPage.StateType stateType) {
        if (showingPage != null) {
            showingPage.showCurrentPage(stateType);
        }
    }
}
