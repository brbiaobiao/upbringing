package com.teaching.upbringing.widget.share;

import android.content.Context;

import com.outsourcing.library.utils.StringUtils;
import com.teaching.upbringing.application.AppFileManager;
import com.teaching.upbringing.utils.ToastUtil;


public class SaveImageSeedModule extends SeedModule {
    @Override
    public boolean work(Context context, SeedEntity seedEntity) {
        if (seedEntity.mBitmap != null) {
            String path = AppFileManager.saveImage(seedEntity.mBitmap);
            if (!StringUtils.isEmpty(path)) {
                ToastUtil.showShort("成功保存到:" + path);
                return true;
            }
        }
        return false;
    }
}
