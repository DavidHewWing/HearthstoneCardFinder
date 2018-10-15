package com.example.dhew6.intersect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class GridActivity extends AppCompatActivity {

    private List<HearthstoneCard> dataList = DataHolder.getInstance().getDataList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        Log.d("data", dataList.toString());

    }
}
