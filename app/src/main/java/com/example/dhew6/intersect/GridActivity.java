package com.example.dhew6.intersect;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridLayout;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GridActivity extends AppCompatActivity {

    private List<HearthstoneCard> dataList = DataHolder.getInstance().getDataSet();
    private List<HearthstoneCard> queryList = new ArrayList<>();
    private Menu menu;
    RecyclerViewAdapter recyclerViewAdapter, recyclerViewAdapterQueried;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(dataList, GridActivity.this);
        recyclerView.setAdapter(recyclerViewAdapter);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            runQuery(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setMaxWidth(Integer.MAX_VALUE);

        return true;
    }

    public void runQuery(String query) {

        final String endPoint = "https://omgvamp-hearthstone-v1.p.mashape.com/cards/search/" + query;
        final List<HearthstoneCard> cardsList = new ArrayList<>();
        queryList.clear();

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .header("X-Mashape-key", "tetQQ4YOXxmshBTcLQISNpEkwypnp14m0E1jsnIFftM5Wzg7Hx")
                        .url(endPoint)
                        .build();
                Response response;

                try {
                    response = client.newCall(request).execute();
                    return response.body().string();
                } catch (IOException e) {
                    Log.d("Help", "Not working");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                try {
                    JSONArray jArr = new JSONArray((String) o);
                    for (int i = 0; i < jArr.length(); i++) {

                        HearthstoneCard card;
                        JSONObject JO = jArr.getJSONObject(i);
                        String name = JO.getString("name");
                        String type = JO.getString("type");
                        String cardSet = JO.getString("cardSet");
                        String imgUrl = null;
                        String playerClass = "";

                        if (JO.has("playerClass")) {
                            playerClass = JO.getString("playerClass");
                        }

                        if (JO.has("img")) {
                            imgUrl = JO.getString("img");
                        }
                        card = new HearthstoneCard(name, imgUrl, type, playerClass, cardSet);
                        queryList.add(card);
                    }

                    recyclerViewAdapterQueried = new RecyclerViewAdapter(queryList, GridActivity.this);
                    recyclerView.setAdapter(recyclerViewAdapterQueried);
                    recyclerViewAdapterQueried.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.d("catch", "help");
                    e.printStackTrace();
                }
            }
        }.execute();

    }

}
