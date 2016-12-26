package com.bwie.test.ynf_project.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.utils.CommonUtils;

/**
 * Created by Administrator on 2016/11/28.
 */
public abstract class ShowingPage extends FrameLayout implements View.OnClickListener {
    public static final int STATE_UNLOAD = 1;
    public static final int STATE_LOAD = 2;
    public static final int STATE_LOAD_ERROR = 3;
    public static final int STATE_LOAD_EMPTY = 4;
    public static final int STATE_SUCCESS = 5;
    public int currentstate = STATE_UNLOAD;
    private View showingpage_load_empty;
    private View showingpage_load_error;
    private View showingpage_loading;
    private View showingpage_unload;
    private View showingpage_success;
    private final LayoutParams layoutParams;
    private Button reload;

    public ShowingPage(Context context) {
        super(context);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (showingpage_load_empty == null) {
            showingpage_load_empty = CommonUtils.inflate(R.layout.showingpage_load_empty);
            this.addView(showingpage_load_empty, layoutParams);
        }
        if (showingpage_load_error == null) {
            showingpage_load_error = CommonUtils.inflate(R.layout.showingpage_load_error);
            this.addView(showingpage_load_error, layoutParams);
        }
        if (showingpage_loading == null) {
            showingpage_loading = CommonUtils.inflate(R.layout.showingpage_loading);
            this.addView(showingpage_loading, layoutParams);
        }
        if (showingpage_unload == null) {
            showingpage_unload = CommonUtils.inflate(R.layout.showingpage_unload);
            this.addView(showingpage_unload, layoutParams);
        }
        showpage();
        onload();
    }
    public void showCurrentPage(StateType stateType){
        this.currentstate = stateType.getCurrentState();
       showpage();
    }
    protected abstract void onload();

    private void showpage() {
        CommonUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                showUIPage();
            }
        });

    }

    private void showUIPage() {
        if (showingpage_unload != null) {
            showingpage_unload.setVisibility(currentstate == STATE_UNLOAD ? View.VISIBLE : View.GONE);
        }
        if (showingpage_loading != null) {
            showingpage_loading.setVisibility(currentstate == STATE_LOAD ? View.VISIBLE : View.GONE);
        }
        if (showingpage_load_error != null) {
            reload = (Button) showingpage_load_error.findViewById(R.id.reload);
            reload.setOnClickListener(this);
            showingpage_load_error.setVisibility(currentstate == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        }
        if (showingpage_load_empty != null) {
            showingpage_load_empty.setVisibility(currentstate == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        }
        if (showingpage_success == null&&currentstate==STATE_SUCCESS) {
            showingpage_success=createSuccessView();
            this.addView(showingpage_success,layoutParams);
        }
        if (showingpage_success!=null){
            showingpage_success.setVisibility(currentstate==STATE_SUCCESS?View.VISIBLE:View.GONE);
        }
    }

    public abstract View createSuccessView();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reload:
                reSetView();
                break;
        }
    }

    private void reSetView() {
        if (currentstate!=STATE_UNLOAD){
            currentstate=STATE_UNLOAD;
        }
        showpage();
        onload();
    }
    public enum StateType{
        STATE_LOAD_ERROR(3),STATE_LOAD_EMPTY(4),STATE_LOAD_SUCCESS(5);

        private final int currentState;

        StateType(int currentpage){
            this.currentState = currentpage;
        }
        public int getCurrentState(){
            return currentState;
        }
    }
}
