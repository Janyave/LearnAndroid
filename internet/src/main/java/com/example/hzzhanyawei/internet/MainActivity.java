package com.example.hzzhanyawei.internet;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hzzhanyawei.internet.Util.HTTPUtil;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String DEBUG = "MainActivity";

    public final static int MSG_HTTPURL = 1;
    public final static int MSG_HTTPCLIENT = 2;

    private Button bt_web;
    private Button bt_httpurl;
    private Button bt_prase;
    private TextView tv_content;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_web = (Button) findViewById(R.id.bt_web);
        bt_httpurl = (Button) findViewById(R.id.bt_httpurl);
        bt_prase = (Button) findViewById(R.id.bt_prase);
        tv_content = (TextView) findViewById(R.id.tv_content);

        bt_web.setOnClickListener(this);
        bt_httpurl.setOnClickListener(this);
        bt_prase.setOnClickListener(this);

        handler = new MyHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_web:
                Intent intent = new Intent(this, Web.class);
                startActivity(intent);
                break;
            case R.id.bt_httpurl:
                //sendRequestWithHttpUrlConnection();
               HTTPUtil.sendRequestWithHttpUrlConnection("http://10.240.155.13/test.json", new HTTPUtil.HttpCallbackListener() {
                   @Override
                   public void onFinish(String response) {
                       praseJsonWithGson(response);
                   }

                   @Override
                   public void onError(Exception e) {

                   }
               });
                break;
            case R.id.bt_prase:
                break;
            default:
                break;
        }

    }

    private void sendRequestWithHttpUrlConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://www.baidu.com");
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
                    //Log.d(DEBUG, "send " + response.toString());
                    Message msg = new Message();
                    msg.what = MSG_HTTPURL;
                    msg.obj = response.toString();
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void sendRequestWithHttpClientConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://10.240.155.13/test.json");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity);

                        //praseXMLWithSAX(response);
                        //praseJsonWithJsonObject(response);
                        praseJsonWithGson(response);

                        Message msg = new Message();
                        msg.what = MSG_HTTPCLIENT;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void praseJsonWithJsonObject(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray("first");
            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                String id = object.getString("id");
                String name = object.getString("name");
                String version = object.getString("version");
                Log.d(DEBUG, "id is " + id);
                Log.d(DEBUG, "name is " + name);
                Log.d(DEBUG, "version is " + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void praseJsonWithGson(String jsonData){
        Gson gson = new Gson();
        JsonContainer container = gson.fromJson(jsonData, JsonContainer.class);

        Log.d(DEBUG, "结果是： " + container.toString());

    }



    private void praseXMLWithPull(String xmlData){
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));
            int eventType = parser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("id".equals(nodeName)){
                            id = parser.nextText();
                        } else if("name".equals(nodeName)) {
                            name = parser.nextText();
                        } else if("version".equals(nodeName)){
                            version = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Log.d(DEBUG, "id is " + id);
                            Log.d(DEBUG, "name is " + name);
                            Log.d(DEBUG, "version is " + version);
                        }
                        break;
                }
                eventType =  parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void praseXMLWithSAX(String xmlData){

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();
            XMLHandler handler = new XMLHandler();

            reader.setContentHandler(handler);
            reader.parse(new InputSource(new StringReader(xmlData)));

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_HTTPURL:
                    //Log.d(DEBUG, msg.obj + "");
                    String response = (String) msg.obj;
                    tv_content.setText(response);
                    break;
                case MSG_HTTPCLIENT:
                    String clinetRes = (String) msg.obj;
                    tv_content.setText(clinetRes);
                    break;
                default:
                    break;
            }
        }
    }

    class XMLHandler extends DefaultHandler{

        private String nodeName;
        private StringBuilder id;
        private StringBuilder name;
        private StringBuilder version;

        @Override
        public void startDocument() throws SAXException {
           //Log.d(DEBUG, "start document");
            id = new StringBuilder();
            name = new StringBuilder();
            version = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            //Log.d(DEBUG, "start element and now node name is "+ localName);
            nodeName = localName;//记录当前节点名
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if ("id".equals(nodeName)){
                id.append(ch, start, length);
            } else if ("name".equals(name)){
                name.append(ch, start, length);
            } else if ("version".equals(nodeName)){
                version.append(ch, start, length);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            //Log.d(DEBUG, "end element and the element is "+ localName);
            if ("app".equals(localName)) {
                Log.d(DEBUG, "id is " + id);
                Log.d(DEBUG, "name is " + name);
                Log.d(DEBUG, "version is " + version);
                id.setLength(0);
                name.setLength(0);
                version.setLength(0);
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }
}

class JsonContainer{

    private List<Frist> first;

    public List<Frist> getFrist() {
        return first;
    }

    public void setFrist(List<Frist> frist) {
        this.first = first;
    }

    @Override
    public String toString() {
        return "{first:" + first + "}";
    }

    class Frist{
        private String id;
        private String name;
        private String version;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "{id:" + id + ",name:" + name + ",version:" + version +"}";
        }
    }
}
