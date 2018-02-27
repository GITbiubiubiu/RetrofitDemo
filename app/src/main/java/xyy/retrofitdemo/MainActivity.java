package xyy.retrofitdemo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import xyy.retrofitdemo.http.HttpManager;
import xyy.retrofitdemo.http.RetrofitManager;
import xyy.retrofitdemo.listener.HttpDataListener;
import xyy.retrofitdemo.model.Express;


public class MainActivity extends Activity {

    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = (TextView) findViewById(R.id.data);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doHttpRequest();
            }
        });
    }

    private void doHttpRequest() {
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
    }

}
