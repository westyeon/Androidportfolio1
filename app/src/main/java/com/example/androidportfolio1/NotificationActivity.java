package com.example.androidportfolio1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NotificationActivity extends AppCompatActivity {
    private List<Notification> list;
    private ListView noti_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        noti_listview = (ListView)findViewById(R.id.noti_listview);

    }

    @Override
    public void onResume(){
        super.onResume();
        new updateThread().start();
    }

    class updateThread extends Thread{
        String xml = null;

        public void run(){
            //다운로드 받는 코드

            String str = ""; //다운로드 받은 문자열을 저장
            try {
                String addr = "http://www.pharm.or.kr/API/recentChange.utf8.xml";
                URL url = new URL(addr);
                //다운로드 받는 코드 - HttpURLConnection
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setConnectTimeout(30000);
                StringBuilder sb = new StringBuilder();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while(true){
                    String line = br.readLine();
                    if(line == null)
                        break;
                    sb.append(line);
                }
                br.close();
                con.disconnect();
                str = sb.toString();
                Log.e("다운로드 받은 문자열", str);
            }catch(Exception e){
                Log.e("다운로드 에러", e.getMessage());


            }

            //xml 파싱 하는 코드

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
                Document doc = builder.parse(istream);
                Element druginfo = doc.getDocumentElement();
                NodeList items = druginfo.getElementsByTagName("drug");
                String Result = "";
                for (int i = 0; i < items.getLength();i++) {
                    Node item = items.item(i);
                    Node text = item.getFirstChild();
                    String directorName = text.getNodeValue();
                    Result += directorName + " : ";
                    NamedNodeMap Attrs = item.getAttributes();
                    for (int j = 0;j < Attrs.getLength(); j++) {
                        Node attr = Attrs.item(j);
                        Result += (attr.getNodeName() + " = " +
                                attr.getNodeValue() + " ");
                    }
                    Result += "\n";
                }

            }
            catch (Exception e) {

            }
        }

            //핸들러 호출하는 코드


        }


    Handler displayHandler = new Handler(Looper.getMainLooper()){
      public void handleMessage(Message message){

      }
    };

}