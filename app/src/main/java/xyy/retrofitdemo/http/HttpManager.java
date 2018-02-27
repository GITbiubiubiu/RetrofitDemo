package xyy.retrofitdemo.http;

import java.lang.ref.WeakReference;

import android.app.ProgressDialog;
import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import xyy.retrofitdemo.listener.HttpDataListener;
import xyy.retrofitdemo.model.ResultMap;
import xyy.retrofitdemo.observer.HttpObserver;

public class HttpManager {
  private volatile static HttpManager singleton;
  private WeakReference<Context> context;
  private Observable observable;
  private HttpObserver observer;

  private HttpManager() {

  }

  public static HttpManager getInstance() {
    if (singleton == null) {
      synchronized (HttpManager.class) {
        if (singleton == null) {
          singleton = new HttpManager();
        }
      }
    }
    return singleton;
  }

  public HttpManager with(Context context) {
    this.context = new WeakReference<>(context);
    return singleton;
  }

  public HttpManager setObservable(Observable observable) {
    this.observable = observable.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(new ResultMap());
    return singleton;
  }

  //创建subscriber
  public void setDataListener(HttpDataListener listener) {
    observer = new HttpObserver(listener, context.get());
    observable.subscribe(observer);
  }

  //创建subscriber 自定义ProgressDialog的文字
  public void setDataListener(HttpDataListener listener, String message) {
    observable.subscribe(new HttpObserver(listener, context.get(), message));
  }

  //创建subscriber 自定义ProgressDialog
  public void setDataListener(HttpDataListener listener, ProgressDialog dialog) {
    observable.subscribe(new HttpObserver(listener, context.get(), dialog));
  }

}
