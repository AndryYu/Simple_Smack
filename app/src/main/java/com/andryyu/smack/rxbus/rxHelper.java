package com.andryyu.smack.rxbus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yufei on 2017/10/14.
 */

public class rxHelper {
    /**
     * 发布消息
     *
     * @param o
     */
    public static void post(Object o) {
        RxBus.getDefault().post(o);
    }

    /**
     * 接收消息,并在主线程处理
     *
     * @param aClass
     * @param disposables 用于存放消息
     * @param listener
     * @param <T>
     */
    public static <T> void doOnMainThread(Class<T> aClass, CompositeDisposable disposables, OnEventListener<T> listener) {
        disposables.add(RxBus.getDefault().toFlowable(aClass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onEvent, throwable -> listener.onError()));
    }

    public static <T> void doOnMainThread(Class<T> aClass, OnEventListener<T> listener) {
        RxBus.getDefault().toFlowable(aClass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onEvent, throwable -> listener.onError());
    }

    /**
     * 接收消息,并在子线程处理
     *
     * @param aClass
     * @param disposables
     * @param listener
     * @param <T>
     */
    public static <T> void doOnChildThread(Class<T> aClass, CompositeDisposable disposables, OnEventListener<T> listener) {
        disposables.add(RxBus.getDefault().toFlowable(aClass)
                .subscribeOn(Schedulers.newThread())
                .subscribe(listener::onEvent, throwable -> listener.onError()));
    }

    public static <T> void doOnChildThread(Class<T> aClass, OnEventListener<T> listener) {
        RxBus.getDefault().toFlowable(aClass)
                .subscribeOn(Schedulers.newThread())
                .subscribe(listener::onEvent, throwable -> listener.onError());
    }

    public interface OnEventListener<T> {
        void onEvent(T t);

        void onError();
    }
}
