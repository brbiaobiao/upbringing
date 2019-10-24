package com.outsourcing.library.crash.reporter;

import android.content.Context;

import java.io.File;

/**
 * Created by zfgx on 2016/5/22.
 */
public class SimpleCrashHandler extends AbstractCrashHandler{
    public SimpleCrashHandler(Context context) {
        super(context);
    }

    @Override
    protected void sendReport(String title, String body, File file) {

    }
}
