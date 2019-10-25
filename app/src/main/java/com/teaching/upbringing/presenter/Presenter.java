package com.teaching.upbringing.presenter;


import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.RxLife;

import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public abstract class Presenter<V> implements IBasePresenter<V>, RxLife.IRxLift {

    private BehaviorSubject<RxLife.Event> mRxLifeSubject = RxLife.createSubject();

    private V view;
    boolean isInited = false;//是否已经初始化
    private InitListener mListener;

    public interface InitListener{
        void onInited();
    }

    /**
     * 监听presenter初始化(其实没什么好监听的)
     * @param listener
     */
    public void setInitListener(InitListener listener) {
        mListener = listener;
    }

    public Presenter(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

    public void setView(V baseView) {
        this.view = baseView;
    }

    public void onStart() {
        RxLife.onEvent(mRxLifeSubject, RxLife.Event.START);
    }

    public void onRestart() {
    }

    @Override
    public void initPresenter() {
        if (!isInited) {
            init();
            isInited = true;
            if (mListener != null) {
                mListener.onInited();
            }
        }
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (ModuleActivity or Fragment) onResume() method.
     */
    public void onResume() {
        RxLife.onEvent(mRxLifeSubject, RxLife.Event.RESUME);
    }

    /**
     * 手动调用初始化
     */
    public void executeInit() {
        init();
    }

    /**
     * 初始化presenter(如用于初始化数据,使用{@link #onViewInited()}更优)
     * 如果要初始化内部变量,应该在构造方法中初始化
     */
    protected abstract void init();

    @Override
    public void onViewInited() {

    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (ModuleActivity or Fragment) onPause() method.
     */
    public void onPause() {
        RxLife.onEvent(mRxLifeSubject, RxLife.Event.PAUSE);
    }

    public void onStop() {
        RxLife.onEvent(mRxLifeSubject, RxLife.Event.START);
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * ModuleActivity onDestroy() method. Fragment onDestroyView() method.
     */
    public void onDestroy() {
        RxLife.onEvent(mRxLifeSubject, RxLife.Event.DESTROY);
    }

    @Override
    public void attach(V view) {
        this.view = view;
    }

    @Override
    public void detach() {
        RxLife.onEvent(mRxLifeSubject, RxLife.Event.DETACH);
        view = null;
    }

    @Override
    public void onDestroyView() {
        RxLife.onEvent(mRxLifeSubject, RxLife.Event.DESTROY_VIEW);
    }

    public boolean isAttach() {
        return view != null;
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLife() {
        return RxLife.bindLife(this);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLife(RxLife.Event event) {
        return RxLife.bindLife(this,event);
    }


    @Override
    public BehaviorSubject<RxLife.Event> getRxLiftSubject() {
        return mRxLifeSubject;
    }
}
