package xyy.retrofitdemo.observer;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import xyy.retrofitdemo.listener.HttpDataListener;

public class HttpObserver<T> implements Observer<T> {

    private HttpDataListener mSubscriberOnNextListener;
    private WeakReference<Context> context;
    private ProgressDialog dialog;

    public HttpObserver(HttpDataListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = new WeakReference<>(context);
        initProgressDialog();
    }

    //自定义ProgressDialog提示文字
    public HttpObserver(HttpDataListener mSubscriberOnNextListener, Context context, String message) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = new WeakReference<>(context);
        initProgressDialog(message);
    }

    //自定义ProgressDialog
    public HttpObserver(HttpDataListener mSubscriberOnNextListener, Context context, ProgressDialog dialog) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = new WeakReference<>(context);
        this.dialog = dialog;
    }

    private void initProgressDialog() {
        Context context = this.context.get();
        if (dialog == null && context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("加载中……");
            dialog.setCancelable(false);
        }
    }

    private void initProgressDialog(String message) {
        Context context = this.context.get();
        if (dialog == null && context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.setCancelable(false);
        }
    }

    private void showProgressDialog() {
        Context context = this.context.get();
        if (dialog == null || context == null) return;
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        showProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        Context context = this.context.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("http", "error----------->" + e.toString());
        }
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

}