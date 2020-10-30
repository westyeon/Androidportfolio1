package com.example.androidportfolio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private TextView itembookmark_title, medicinebookmark_title;
    private ListView bookmarklistview,medicine_bookmarklistview;
    private ArrayList<BookMark> list;
    private ArrayList<Medicine> list2;
    private BookMarkAdapter listAdapter;
    private MedicineAdapter medilistAdapter;
    BookMarkDB bookMarkDB;
    Medi_BookMarkDB medi_bookmarkDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        itembookmark_title = (TextView)findViewById(R.id.itembookmark_title);
        medicinebookmark_title = (TextView)findViewById(R.id.medicinebookmark_title);
        bookmarklistview = (ListView)findViewById(R.id.bookmarklistview);
        medicine_bookmarklistview = (ListView)findViewById(R.id.medicine_bookmarklistview);

        bookMarkDB = new BookMarkDB(BookmarkActivity.this, null, null, 1);
        medi_bookmarkDB = new Medi_BookMarkDB(BookmarkActivity.this, null, null, 1);

        list = bookMarkDB.listBookMark();
        listAdapter = new BookMarkAdapter(BookmarkActivity.this, list, R.layout.bookmark_list);
        bookmarklistview.setAdapter(listAdapter);

        list2 = medi_bookmarkDB.listMedicine();
        medilistAdapter = new MedicineAdapter(BookmarkActivity.this, list2, R.layout.search_item_list, 1);
        medicine_bookmarklistview.setAdapter(medilistAdapter);


    }

}

