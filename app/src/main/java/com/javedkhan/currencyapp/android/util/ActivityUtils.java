package com.javedkhan.currencyapp.android.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentToActivitywithbackstack(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId, String backstacklabel) {
        Log.e("backstack count", "@@@@" + fragmentManager.getBackStackEntryCount());

        FragmentTransaction transaction = fragmentManager.beginTransaction();

//        String abvc=fragment.getTag();
//        Fragment frag = fragmentManager.findFragmentById(frameId);
//        if(frag!=null){
//            fragmentManager.beginTransaction().remove(frag);
//        }
        transaction.replace(frameId, fragment, backstacklabel);
        transaction.addToBackStack(backstacklabel);
        transaction.commit();
    }

    public static void clearallTop(FragmentManager fm) {
        int stackCount = fm.getBackStackEntryCount();
        FragmentTransaction ft = fm.beginTransaction();

        for (int j = stackCount - 2; j > 1; j--) {
//            FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(j);
            fm.popBackStack();
//            Fragment fragment=  fm.findFragmentByTag(backEntry.getName());
//            ft.remove(fragment);
        }
    }

    public static void removeFromBackstack(@NonNull FragmentManager fm, String tag) {
        int stackCount = fm.getBackStackEntryCount();
//        FragmentTransaction ft =fm.beginTransaction();
        if (stackCount > 0) {
            for (int j = stackCount - 1; j > 0; j--) {
                FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(j);
                if (!backEntry.getName().equalsIgnoreCase(tag)) {
//                    Fragment fragment=  fm.findFragmentByTag(backEntry.getName());
//                    ft.remove(fragment);
                    fm.popBackStackImmediate();
                } else {
//                    Fragment fragment=  fm.findFragmentByTag(backEntry.getName());
//                    ft.remove(fragment);
//                    fm.popBackStackImmediate();
                    break;
                }
//                Fragment fragment=  fm.findFragmentByTag(backEntry.getName());
            }
        }

    }
}
