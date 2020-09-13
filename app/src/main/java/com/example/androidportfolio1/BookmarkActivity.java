package com.example.androidportfolio1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookmarkActivity extends AppCompatActivity {
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new BookmartActivity());
    }

}
    class BookmartActivity implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        // FAB Click 이벤트 처리 구간
    }
}