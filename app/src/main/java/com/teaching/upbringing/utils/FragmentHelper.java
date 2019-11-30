package com.teaching.upbringing.utils;

import android.text.TextUtils;

import com.lefore.tutoring.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * Class Description
 * Created on 2018/7/12.
 *
 * @author ldzero
 */
public class FragmentHelper {

    public static final int NONE = 0;
    public static final int LEFT_IN = 1;
    public static final int LEFT_OUT = 2;
    public static final int RIGHT_IN = 3;
    public static final int RIGHT_OUT = 4;

    @IntDef({NONE, LEFT_IN, LEFT_OUT, RIGHT_IN, RIGHT_OUT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimOrientation {

    }

    public static void addFragment(AppCompatActivity activity, @IdRes int containerId, Fragment fragment) {
        addFragment(activity, containerId, fragment, null);
    }

    public static void addFragment(AppCompatActivity activity, @IdRes int containerId,
                                   Fragment fragment, String tag) {
        addFragment(activity, containerId, fragment, tag, RIGHT_IN, LEFT_OUT);
    }

    public static void addFragment(AppCompatActivity activity, @IdRes int containerId,
                                   Fragment fragment, String tag,
                                   @AnimOrientation int inAnimOrientation,
                                   @AnimOrientation int outAnimOrientation) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (inAnimOrientation != NONE && outAnimOrientation != NONE) {
            fragmentTransaction.setCustomAnimations(getAnimId(inAnimOrientation), getAnimId(outAnimOrientation),
                    getPopAnimId(inAnimOrientation), getPopAnimId(outAnimOrientation));
        }
        if (TextUtils.isEmpty(tag)) {
            fragmentTransaction.add(containerId, fragment);
        } else {
            fragmentTransaction.add(containerId, fragment, tag);
        }
        fragmentTransaction.commit();
    }

    public static void addFragment(FragmentActivity activity, @IdRes int containerId,
                                   Fragment fragment, String tag,
                                   @AnimOrientation int inAnimOrientation,
                                   @AnimOrientation int outAnimOrientation) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (inAnimOrientation != NONE && outAnimOrientation != NONE) {
            fragmentTransaction.setCustomAnimations(getAnimId(inAnimOrientation), getAnimId(outAnimOrientation),
                    getPopAnimId(inAnimOrientation), getPopAnimId(outAnimOrientation));
        }
        if (TextUtils.isEmpty(tag)) {
            fragmentTransaction.add(containerId, fragment);
        } else {
            fragmentTransaction.add(containerId, fragment, tag);
        }
        fragmentTransaction.commit();
    }

    public static void replaceFragment(AppCompatActivity activity, @IdRes int containerId,
                                       Fragment fragment) {
        replaceFragment(activity, containerId, fragment, null, true, null);
    }

    public static void replaceFragment(AppCompatActivity activity, @IdRes int containerId,
                                       Fragment fragment, String tag,
                                       boolean addToBackStack, String backFragmentName) {
        replaceFragment(activity, containerId, fragment, tag, addToBackStack, backFragmentName,
                RIGHT_IN, LEFT_OUT);
    }

    public static void replaceFragment(AppCompatActivity activity, @IdRes int containerId,
                                       Fragment fragment, String tag,
                                       boolean addToBackStack, String backFragmentName,
                                       @AnimOrientation int inAnimOrientation,
                                       @AnimOrientation int outAnimOrientation) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (inAnimOrientation != NONE && outAnimOrientation != NONE) {
            fragmentTransaction.setCustomAnimations(getAnimId(inAnimOrientation), getAnimId(outAnimOrientation),
                    getPopAnimId(inAnimOrientation), getPopAnimId(outAnimOrientation));
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backFragmentName);
        }
        if (TextUtils.isEmpty(tag)) {
            fragmentTransaction.replace(containerId, fragment);
        } else {
            fragmentTransaction.replace(containerId, fragment, tag);
        }
        fragmentTransaction.commit();
    }

    public static void popFragment(AppCompatActivity activity, String popFragmentName) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (TextUtils.isEmpty(popFragmentName)) {
            fragmentManager.popBackStack();
        } else {
            fragmentManager.popBackStack(popFragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public static Fragment getFragment(AppCompatActivity activity, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return fragmentManager.findFragmentByTag(tag);
    }

    public static Fragment getFragment(AppCompatActivity activity, @IdRes int containerId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return fragmentManager.findFragmentById(containerId);
    }

    @AnimRes
    @AnimatorRes
    private static int getAnimId(@AnimOrientation int animOrientation) {
        switch (animOrientation) {
            case LEFT_IN:
                return R.anim.slide_left_in;
            case LEFT_OUT:
                return R.anim.slide_left_out;
            case RIGHT_IN:
                return R.anim.slide_right_in;
            case RIGHT_OUT:
                return R.anim.slide_right_out;
            case NONE:
            default:
                return 0;
        }
    }

    @AnimRes
    @AnimatorRes
    private static int getPopAnimId(@AnimOrientation int animOrientation) {
        switch (animOrientation) {
            case LEFT_IN:
                return R.anim.slide_right_in;
            case LEFT_OUT:
                return R.anim.slide_right_out;
            case RIGHT_IN:
                return R.anim.slide_left_in;
            case RIGHT_OUT:
                return R.anim.slide_left_out;
            case NONE:
            default:
                return 0;
        }
    }
}
