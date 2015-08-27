package com.example.hzzhanyawei.internet.Util;

import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hzzhanyawei on 2015/8/27.
 * Email hzzhanyawei@corp.netease.com
 */
public class HTTPUtil {
    public static void sendRequestWithHttpUrlConnection(final String inputUrl, final HttpCallbackListener listener){
        if(null == listener){
            Log.e("HTTPUtil Debug", "HttpcallbackListener is null!");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(inputUrl);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null){
                        response.append(line);
                    }
                    listener.onFinish(response.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    listener.onError(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError(e);
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendRequestWithHttpClientConnection(final String url, final HttpCallbackListener listener){
        if(null == listener){
            Log.e("HTTPUtil Debug", "HttpcallbackListener is null!");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity);
                        listener.onFinish(response);
                    }
                } catch (IOException e) {
                    listener.onError(e);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }
}
