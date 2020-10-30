package com.example.androidportfolio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private Spinner searchtype;
    private EditText value;
    private Button btnsearch;

    private ListView listview;
    private List<Medicine> list;
    private MedicineAdapter listAdapter;

    BookMarkDB bookMarkDB;
    Medi_BookMarkDB medi_bookmarkDB;

    //데이터를 가져오는 스레드
    class DownloadThread extends Thread{
        public void run(){
            String searchType = "";
            //searchType에서 선택한 번호를 가지고 검색종류를 생성
            switch (searchtype.getSelectedItemPosition()){
                case 0:
                    errorHandler.sendEmptyMessage(0);
                    return;
                case 1:
                    searchType = "item_seq_num";
                    break;
                case 2:
                    searchType = "item_name";
                    break;
                case 3 :
                    searchType = "entp_seq";
                    break;
                case 4:
                    searchType = "entp_name";
                    break;
                case 5:
                    searchType = "edi_code";
                    break;

            }
            String keyword = value.getText().toString().trim();
            String str = ""; //다운로드 받은 문자열을 저장
            try {
                String addr = "http://172.30.1.48:8080/portfolio1/medicine/list?searchtype=" + searchType + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
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

            //json 파싱
            try{
                if(str != null){
                    JSONObject result = new JSONObject(str);
                    JSONArray jsonlist = result.getJSONArray("list");
                    list.clear();
                    for(int i=0; i<jsonlist.length(); i=i+1){
                        JSONObject item = jsonlist.getJSONObject(i);
                        Medicine medicine = new Medicine();
                        medicine.item_seq_num = item.getString("item_seq_num");
                        medicine.item_name = item.getString("item_name");
                        medicine.entp_seq = item.getString("entp_seq");
                        medicine.entp_name = item.getString("entp_name");
                        medicine.item_image = item.getString("item_image");
                        medicine.edi_code = item.getString("edi_code");

                        list.add(medicine);
                    }
                    Log.e("medicine", list.toString());

                    BookMark bookMark = bookMarkDB.findBookMark(searchType, keyword);

                    if(bookMark == null){
                        BookMark bm = new BookMark();
                        bm.setItem(searchType);
                        bm.setSearch_word(keyword);
                        bookMarkDB.addBookMark(bm);
                        //Select * FROM bookmark WHERE item = 'entp_name' and SEARCH_WORD = '동아제약'
                        Log.e("삽입", bm.toString());
                    }else{
                        bookMarkDB.updateBookMark(bookMark);
                        Log.e("수정", bookMark.toString());
                    }

                }
            }catch(Exception e){
                Log.e("에러", e.getMessage());
            }
            displayHandler.sendEmptyMessage(0);
        }
    }

    //데이터를 출력하기 위한 핸들러
    Handler displayHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){

            listAdapter.notifyDataSetChanged();

        }
    };

    //데이터를 출력하기 위한 핸들러
    Handler errorHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){

            Toast.makeText(SearchActivity.this,"다른 검색 항목을 선택하세요", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchtype = (Spinner)findViewById(R.id.searchtype);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.searchtype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchtype.setPrompt("항목을 선택하세요");
        searchtype.setAdapter(adapter);

        value = (EditText)findViewById(R.id.value);
        btnsearch = (Button)findViewById(R.id.btnsearch);

        listview = (ListView)findViewById(R.id.listview);
        list = new ArrayList<>();
        listAdapter = new MedicineAdapter(SearchActivity.this, list, R.layout.search_item_list, 0);
        listview.setAdapter(listAdapter);

        bookMarkDB = new BookMarkDB(SearchActivity.this, null, null, 1);
        medi_bookmarkDB = new Medi_BookMarkDB(SearchActivity.this,null,null,1);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadThread().start();

                InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(btnsearch.getWindowToken(), 0);
            }
        });
    }


}