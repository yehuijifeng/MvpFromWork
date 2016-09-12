package com.android.mvp.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtils {

    public static void finish(Activity activity) {
        activity.finish();
    }

    public static FragmentTransaction addFragment(FragmentActivity activity,
                                                  Fragment fragment, int resource) {
        FragmentTransaction mFragmentTransaction = activity
                .getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(resource, fragment);
        mFragmentTransaction.commit();
        return mFragmentTransaction;
    }

    public static FragmentTransaction replaceFragment(
            FragmentActivity activity, Fragment fragment, int resource) {
        FragmentTransaction mFragmentTransaction = activity
                .getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(resource, fragment);
        mFragmentTransaction.commit();
        return mFragmentTransaction;
    }

}