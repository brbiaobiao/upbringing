package com.teaching.upbringing.mvpBase;


import com.outsourcing.library.mvp.rxbase.IContextView;

import java.util.List;

/**
 * Created by SpannerBear on 2018/9/13.
 * use to:
 */
public interface IListContract {
    
    interface IView<M> extends IContextView {
        void refreshFinish();
        
        void addData(List<M> list);
    
        void refresh();
        
        void cleanData();
    }
    
    interface IPresenter {
        void requestData(String key, int pageSize, int index);
    }
    
}
