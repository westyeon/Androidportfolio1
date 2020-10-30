package com.example.androidportfolio1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookMarkAdapter extends BaseAdapter {
    //뷰를 출력할 때 필요한 Context(문맥-어떤 작업을 하기 위해 필요한 정보를 저장한 객체) 변수
    Context context;
    //ListView에 출력할 데이터
    ArrayList<BookMark> data;
    //항목 뷰에 해당하는 레이아웃의 아이디를 저장할 변수
    int layout;
    //xml로 만들어진 레이아웃을 뷰로 변환하기 위한 클래스의 변수
    LayoutInflater inflater;


    public BookMarkAdapter(Context context, ArrayList<BookMark> data, int layout) {
        super();
        this.context = context;
        this.data = data;
        this.layout = layout;
        inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getItem();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        //convertView 생성
        if (convertView == null) {
            //layout에 정의된 뷰를 parent에 넣을 수 있도록 View로 생성
            convertView = inflater.inflate(layout, parent, false);
        }

        //텍스트 출력
        final TextView txt1 = (TextView) convertView.findViewById(R.id.bookmark_item);
        final TextView txt2 = (TextView) convertView.findViewById(R.id.bookmark_search_word);
        TextView txt3 = (TextView) convertView.findViewById(R.id.bookmark_date);

        txt1.setText(data.get(pos).getItemname());
        txt2.setText(data.get(pos).getSearch_word());
        txt3.setText(data.get(pos).getDate());

        //버튼의 이벤트 처리
        Button btn = (Button)convertView.findViewById(R.id.bookmark_delete_btn);
        final int x = position;
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BookMark bookMark = new BookMark();
                bookMark.setItem(data.get(pos).getItem());
                bookMark.setSearch_word(txt2.getText().toString());

                BookMarkDB db = new BookMarkDB(context, null, null, 1);
                db.deleteBookMark(bookMark);

                data.remove(pos);

                BookMarkAdapter.this.notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
