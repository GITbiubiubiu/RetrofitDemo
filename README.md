# RetrofitDemo
Retrofit+RxJava+OkHttp链式封装
对status和判断和message的处理进行封装，在调用时，我们只关心请求成功后返回的数据，只需要在请求成功后对返回的对象进行处理，而请求失败或者其他情况全部进行封装，不在调用时处理。

使用举例：
```
HttpManager.getInstance()
            .with(this)
            .setObservable(RetrofitManager.getService().getExpress("yuantong", "200382770316"))
            .setDataListener(new HttpDataListener<List<Express>>() {
                @Override
                public void onNext(List<Express> list) {
                    //这里对返回数据进行处理
                    String result = "";
                    for (int i = 0; i < list.size(); i++){
                        result = result + list.get(i).toString();
                    }
                    data.setText(result);
                }
            });
 ```
