package com.example.androidportfolio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private Spinner searchtype;
    private EditText value;
    private Button btnsearch;
    private ListView listview;

    private List<Medicine> list;
    private ArrayAdapter<Medicine> medicineAdapter;

    //페이지 번호와 페이지 당 데이터 개수를 저장할 변수
    int pageNo = 1;
    int size = 3;

    //조건에 맞는 데이터 개수를 저장할 변수
    int cnt;

    //출력할 내용
    String result = "";

    //스레드가 다운로드 받아서 파싱한 결과를 출력할 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message message){

            //list.setText(result);

            //adapter를 이용해서 ListView 에 데이터가
            //수정되었으니 다시 출력하고 신호를 보냄

            //신호를 보내는 것을 프로그래밍에서는
            //Notification이라고 합니다.
            medicineAdapter.notifyDataSetChanged();
        }
    };
    //데이터를 다운로드 받아서 파싱하는 스레드
    class ThreadEx extends Thread{
        //다운로드 받은 문자열을 저장할 객체
        StringBuilder sb = new StringBuilder();

        public void run(){
            try{
                URL url = null;
                //콤보 박스 선택한 항목 번호를 idx에 저장
                int idx = searchtype.getSelectedItemPosition();
                if(idx == 0){
                    url = new URL(
                            "http://192.168.0.87:8080/portfolio1/list?" +
                                    "pageno=" + pageNo);
                }else if(idx == 1){
                    url = new URL(
                            "http://192.168.0.87:8080/portfolio1/list?"
                                    + "searchtype=item_seq_num&" + "value=" +
                                    value.getText().toString() + "&pageno="
                                    +pageNo
                    );
                }else if(idx == 2){
                    url = new URL(
                            "http://192.168.0.87:8080/portfolio1/list?"
                                    + "searchtype=item_name&" + "value=" +
                                    value.getText().toString() + "&pageno="
                                    +pageNo
                    );
                }else if(idx == 3) {
                    url = new URL(
                            "http://192.168.0.87:8080/portfolio1/list?"
                                    + "searchtype=entp_seq&" + "value=" +
                                    value.getText().toString() + "&pageno="
                                    + pageNo
                    );
                }else if(idx == 4) {
                    url = new URL(
                            "http://192.168.0.87:8080/portfolio1/list?"
                                    + "searchtype=entp_name&" + "value=" +
                                    value.getText().toString() + "&pageno="
                                    + pageNo
                    );
                }else{
                    url = new URL(
                            "http://192.168.0.87:8080/portfolio1/list?"
                                    + "searchtype=edi_code&" + "value=" +
                                    value.getText().toString() + "&pageno="
                                    +pageNo
                    );
                }

                HttpURLConnection con = (
                        HttpURLConnection)url.openConnection();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                while(true){
                    String line = br.readLine();
                    if(line == null){
                        break;
                    }
                    sb.append(line + "\n");
                }
                br.close();
                con.disconnect();

            }catch(Exception e){
                Log.e("다운로드 예외", e.getMessage());
            }
            try{
                //객체로 변환
                JSONObject object = new JSONObject(sb.toString());
                //데이터 개수는 count에 숫자로 저장
                cnt = object.getInt("count");
                //list 키의 데이터를 배열로 가져오기
                JSONArray ar = object.getJSONArray("list");
                for(int i=0; i<ar.length(); i=i+1){
                    JSONArray temp = ar.getJSONArray(i);
                    //result = result + temp.getString(1) + "\n";
                    Medicine medicine = new Medicine();
                    medicine.item_seq_num = temp.getString(0);
                    medicine.item_name = temp.getString(1);
                    medicine.entp_seq = temp.getString(2);
                    medicine.entp_name = temp.getString(3);
                    medicine.chart = temp.getString(4);
                    medicine.item_image = temp.getString(5);
                    medicine.print_front = temp.getString(6);
                    medicine.print_back = temp.getString(7);
                    medicine.drug_shape = temp.getString(8);
                    medicine.color_class1 = temp.getString(9);
                    medicine.color_class2 = temp.getString(10);
                    medicine.line_front = temp.getString(11);
                    medicine.line_back = temp.getString(12);
                    medicine.leng_long = temp.getString(13);
                    medicine.leng_short = temp.getString(14);
                    medicine.thick = temp.getString(15);
                    medicine.img_regist_ts = temp.getString(16);
                    medicine.class_no = temp.getString(17);
                    medicine.class_name = temp.getString(18);
                    medicine.etc_otc_name = temp.getString(19);
                    medicine.item_permit_date = temp.getString(20);
                    medicine.form_code_name = temp.getString(21);
                    medicine.mark_code_front_anal = temp.getString(22);
                    medicine.mark_code_back_anal = temp.getString(23);
                    medicine.mark_code_front_img = temp.getString(24);
                    medicine.mark_code_back_img = temp.getString(25);
                    medicine.change_date = temp.getString(26);
                    medicine.mark_code_front = temp.getString(27);
                    medicine.mark_code_back = temp.getString(28);
                    medicine.item_eng_name = temp.getString(29);
                    medicine.edi_code = temp.getString(30);
                    //list.add(item);
                    list.add(medicine);
                }
                //핸들러에게 출력을 요청
                handler.sendEmptyMessage(0);

            }catch(Exception e){
                Log.e("파싱에러", e.getMessage());
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        searchtype = (Spinner)findViewById(R.id.searchtype);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.searchtype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchtype.setAdapter(adapter);



        value = (EditText)findViewById(R.id.value);
        btnsearch = (Button)findViewById(R.id.btnsearch);
        listview = (ListView)findViewById(R.id.listview);



    }
}