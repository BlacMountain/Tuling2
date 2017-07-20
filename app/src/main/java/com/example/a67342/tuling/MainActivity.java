package com.example.a67342.tuling;

import android.content.Context;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Handler;
import com.google.gson.Gson;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;


public class MainActivity extends AppCompatActivity {
    private List<ListData> lists;
    private RecyclerView listView;
    private EditText sendText;
    private Button sendButton;
    private String content_string;
    private RecyclerAdapter adapter;
    private HttpClient client;
    private Context context = this;
    private LinearLayoutManager linearLayoutManager;
    private static int YESorNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //控件连接
        linearLayoutManager = new LinearLayoutManager(context);//设置layoutmanager形式为Linear
        listView = (RecyclerView) findViewById(R.id.lv);
        listView.setHasFixedSize(true);//高度固定 设置提高性能
        listView.setLayoutManager(linearLayoutManager);
        sendText = (EditText) findViewById(R.id.sendText);
        lists = new ArrayList<ListData>();
        client = new DefaultHttpClient();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String content = (String) msg.obj;
                initList_Adapter(content,ListData.RECEIVER);//更新页面
                sendText.setHint("接收成功");//检测是否接收 可去
            }
        }
    };

    private void initList_Adapter(String content_string,int RECEIVERorSEND){
        int i = lists.size();
        String time = "";
        if (i % 5 == 0) {//每五个对话显示一次时间，其他直接设置为空，方便赋值
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            time = formatter.format(curDate);
        }
        //end
        //构造新的集合数据，赋值对话、布局、时间、集合位置
        ListData listData = new ListData(content_string, RECEIVERorSEND,time,i);
        lists.add(listData);//添加数据
        adapter = new RecyclerAdapter(lists,context);//刷新适配器列表
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();//刷新页面
    }


    public void Send_Button(View view) {
        //设置当对话开始出框自动来到最下端
        if (YESorNo == 0&&lists.size()>=10) {
            linearLayoutManager.setStackFromEnd(true);
            YESorNo = 1;
        }
        //end
        content_string = sendText.getText().toString();//得到该发送数据
        sendText.setText("");//清空对话框
        initList_Adapter(content_string,ListData.SEND);//更新页面

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //网络请求 start
                    HttpPost post = new HttpPost("http://www.tuling123.com/openapi/api");
                    List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
                    data.add(new BasicNameValuePair("key","4ab85e311eba48ed9dee0f3628457317"));
                    data.add(new BasicNameValuePair("info",URLEncoder.encode(content_string,"UTF-8")));
                    sendText.setHint(URLEncoder.encode(content_string,"UTF-8"));//观察信息是否发送
                    data.add(new BasicNameValuePair("userid","106020"));
                    post.setEntity(new UrlEncodedFormEntity(data));

                    HttpResponse response = client.execute(post);
                    String result = EntityUtils.toString(response.getEntity());
                            /*//Json解析
                            JSONObject object = new JSONObject(result);
                               // if (object.getInt("code") == 100000) {
                                    String text = object.getString("text");
                            //end*/
                    //Gson解析
                    Gson mGson = new Gson();
                    Bean bean = mGson.fromJson(result,Bean.class);
                    String text = bean.getText();
                    Log.i("MainActivity", "run: "+bean.getCode());
                    if (bean.getCode() == 100000) {
                        //end
                        Message message = new Message();
                        message.obj = text;
                        message.what = 0;
                        handler.sendMessage(message);
                    } else {
                        Message message2  = new Message();
                        message2.what = 0;
                        message2.obj = "访问失败";
                        handler.sendMessage(message2);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
