package xyy.retrofitdemo.listener;

public interface HttpDataListener<T> {
    void onNext(T t);
}
