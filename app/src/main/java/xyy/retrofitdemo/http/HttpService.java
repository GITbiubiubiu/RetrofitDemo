package xyy.retrofitdemo.http;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyy.retrofitdemo.model.Express;
import xyy.retrofitdemo.model.ResultModel;

public interface HttpService {

    @GET("query")
    Observable<ResultModel<List<Express>>> getExpress(@Query("type") String type,
                                                      @Query("postid") String postid);
}
