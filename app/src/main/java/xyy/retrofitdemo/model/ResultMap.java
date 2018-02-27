package xyy.retrofitdemo.model;

import io.reactivex.functions.Function;

public class ResultMap<T> implements Function<ResultModel<T>, T> {

  @Override
  public T apply(ResultModel<T> httpResult) {
    if ("200".equals(httpResult.getStatus())) {
      return httpResult.getData();
    } else {
      throw new RuntimeException("请求失败(code=" + httpResult.getStatus() + ",message=" + httpResult.getMessage() + ")");
    }
  }
}
