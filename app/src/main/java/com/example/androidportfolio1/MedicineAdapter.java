package com.example.androidportfolio1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicineAdapter extends BaseAdapter {
    //뷰를 출력할 때 필요한 Context(문맥-어떤 작업을 하기 위해 필요한 정보를 저장한 객체) 변수
    Context context;
    //ListView에 출력할 데이터
    List<Medicine> data;
    //항목 뷰에 해당하는 레이아웃의 아이디를 저장할 변수
    int layout;
    //xml로 만들어진 레이아웃을 뷰로 변환하기 위한 클래스의 변수
    LayoutInflater inflater;

    int x;

    MedicineAdapter self;

    public MedicineAdapter(Context context, List<Medicine> data, int layout, int x) {
        super();
        this.context = context;
        this.data = data;
        this.layout = layout;
        this.x = x;
        self = this;
        inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    //출력할 데이터의 개수를 설정하는 메소드
    public int getCount() {
        return data.size();
    }
    @Override
    //항목 뷰에 보여질 문자열을 설정하는 메소드
    //position은 반복문이 수행될 때의 인덱스
    public Object getItem(int position) {
        return data.get(position).item_seq_num;
    }
    @Override
    //각 항목뷰의 아이디를 설정하는 메소드
    public long getItemId(int position) {
        return position;
    }
    @Override
    //ListView에 출력될 실제 뷰의 모양을 설정하는 메소드
    //convertView는 화면에 보여질 뷰인데 처음에는 null이 넘어오고 두번째 부터는
    //이전에 출력된 뷰가 넘어옵니다.
    //인덱스마다 다른 뷰를 출력하고자 하면 convertView를 새로 만들지만
    //모든 항목뷰의 모양이 같다면 처음 한번만 만들면 됩니다.
    public View getView(int position, View convertView, ViewGroup parent) {
        final Medi_BookMarkDB db = new Medi_BookMarkDB(context, null, null, 1);

        final int pos = position;
        //convertView 생성
        if(convertView == null){
        //layout에 정의된 뷰를 parent에 넣을 수 있도록 View로 생성
            convertView = inflater.inflate(layout, parent, false);
        }

        //이미지 출력
        ImageView imgView = (ImageView)convertView.findViewById(R.id.search_item_img);
        Log.e("이미지 URL", data.get(pos).toString());
        new ImageThread(imgView, data.get(pos).item_image).start();

        //텍스트 출력
        TextView txt1 = (TextView)convertView.findViewById(R.id.item_seq_num);
        final TextView txt2 = (TextView)convertView.findViewById(R.id.item_name);
        final TextView txt3 = (TextView)convertView.findViewById(R.id.entp_seq);
        final TextView txt4 = (TextView)convertView.findViewById(R.id.entp_name);
        final TextView txt5 = (TextView)convertView.findViewById(R.id.edi_code);
        txt1.setText(data.get(pos).item_seq_num);
        txt2.setText(data.get(pos).item_name);
        txt3.setText(data.get(pos).entp_seq);
        txt4.setText(data.get(pos).entp_name);
        txt5.setText(data.get(pos).edi_code);
        //버튼의 이벤트 처리
        final CheckBox btn = (CheckBox)convertView.findViewById(R.id.bookmark_btn);
        if(db.checkMedicine(data.get(pos).item_seq_num, data.get(pos).item_image)){
            btn.setChecked(true);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("클릭", btn.isChecked()+"");

                if(btn.isChecked()){
                    Medicine medicine = new Medicine();
                    medicine.setItem_seq_num(data.get(pos).getItem_seq_num());
                    medicine.setItem_name(txt2.getText().toString());
                    medicine.setEntp_seq(txt3.getText().toString());
                    medicine.setEntp_name(txt4.getText().toString());
                    medicine.setEdi_code(txt5.getText().toString());
                    medicine.setItem_image(data.get(pos).item_image);

                    db.addMedicine(medicine);

                }else{
                    Medicine medicine = new Medicine();
                    medicine.setItem_seq_num(data.get(pos).getItem_seq_num());
                    medicine.setItem_name(txt2.getText().toString());
                    medicine.setEntp_seq(txt3.getText().toString());
                    medicine.setEntp_name(txt4.getText().toString());
                    medicine.setEdi_code(txt5.getText().toString());
                    medicine.setItem_image(data.get(pos).item_image);

                    Medi_BookMarkDB db = new Medi_BookMarkDB(context, null, null, 1);
                    db.deleteMedicine(medicine);

                    if(x == 1){
                        data.remove(pos);
                        self.notifyDataSetChanged();
                    }
                }
            }
        });

        return convertView;
    }

    class ImageThread extends Thread{

        ImageView imageView;
        String url;

        public ImageThread(ImageView imageView, String url){
            this.imageView = imageView;
            this.url = url;
        }
        public void run(){
            try {
                InputStream is = new URL(url).openStream();
                Bitmap bit = BitmapFactory.decodeStream(is);
                is.close();

                Message msg = new Message();
                Map<String, Object> map = new HashMap<>();
                map.put("imageView", imageView);
                map.put("bitmap", bit);
                msg.obj = map;

                imageHandler.sendMessage(msg);

            }catch(Exception e){

            }

        }
    }

    Handler imageHandler =  new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            Map<String, Object>map = (Map<String, Object>)msg.obj;
            ImageView imageView = (ImageView)map.get("imageView");
            Bitmap bit = (Bitmap)map.get("bitmap");
            imageView.setImageBitmap(bit);

        }
    };
}