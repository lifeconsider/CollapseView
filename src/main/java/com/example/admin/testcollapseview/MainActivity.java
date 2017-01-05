package com.example.admin.testcollapseview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CollapseView mCollapseVIew = (CollapseView) findViewById(R.id.collapseView);



        mCollapseVIew.setTitle("小宝贝");
        mCollapseVIew.setContent(R.layout.content);
    }
}
