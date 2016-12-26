package com.bwie.test.ynf_project.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/28.
 */
public class FragmentFactory {
    public  static HashMap<Integer,Fragment> fragmentMap = new HashMap<>();
    public static Fragment getFragent(int position){
        Fragment fragment = fragmentMap.get(position);
        if (fragment!=null){
            return fragment;
        }
        switch (position){
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new CategoryFragment();
                break;
            case 2:
                fragment = new CartFragment();
                break;
            case 3:
                fragment = new MineFragment();
                break;

        }
        fragmentMap.put(position,fragment);
        return fragment;
    }
}
