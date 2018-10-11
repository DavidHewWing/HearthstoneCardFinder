package com.example.dhew6.intersect;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextSwitcher;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    final private String CARD_ENDPOINT = "https://omgvamp-hearthstone-v1.p.mashape.com/cards";
    TextView dateTextView;
    TextSwitcher patientTextSwitcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dateTextView = findViewById(R.id.dateTextView);
        patientTextSwitcher = findViewById(R.id.patientTextSwitcher);

        final String[] patientTexts = {"Destroying Monsters...", "Feeding Murlocs...", "Drinking Potions...",
        "Top-decking Lethal...", "Forging Decks...", "Cleaning Playing Board...", "Trimming Innkeepers Beard...",
        "Polishing Shields...", "Sharpening Weapons...", "Sweeping Floors...", "Praying to RNGJesus..."};

        Timer timer = new Timer();
        TimerTask timerTask;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        Random random = new Random();
                        int x = random.nextInt(patientTexts.length);
                        String text = patientTextSwitcher.getText().toString();
                        String replacement = patientTexts[x];
                        if(text.equals(patientTexts[x])){
                            if(text.equals(patientTexts[0])){
                                int y = random.nextInt(patientTexts.length-1)+1;
                                replacement=patientTexts[y];
                            }
                            else if(text.equals(patientTexts[patientTexts.length-1])){
                                int z = random.nextInt(patientTexts.length-1);
                                replacement=patientTexts[z];
                            }
                        }
                        patientTextSwitcher.setText(replacement);
                    }
                });
            }
        };
        timer.schedule(timerTask, 3000, 3000);


        new LongProcess().execute();

        Date date = new Date();
        String format = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.ENGLISH).format(date);
        dateTextView.setText(format);

    }

    private class LongProcess extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .header("X-Mashape-key", "tetQQ4YOXxmshBTcLQISNpEkwypnp14m0E1jsnIFftM5Wzg7Hx")
                    .url(CARD_ENDPOINT)
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject root = new JSONObject(s); //root
                HashMap<String, HearthstoneCard> map = new HashMap<>(); //key is the name, value is the card (for searching)
                ArrayList<HearthstoneCard> list = new ArrayList<>(); //list of the cards
                Iterator<String> keys = root.keys(); //iterator to loop through keys

                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONArray arr = root.getJSONArray(key); //get array

                    //loop through all cards in array and store card in map and list
                    for (int i = 0; i < arr.length(); i++) {

                        HearthstoneCard card;
                        JSONObject JO = arr.getJSONObject(i);
                        String name = JO.getString("name");
                        String type = JO.getString("type");
                        String cardSet = JO.getString("cardSet");
                        String imgUrl = null;
                        String playerClass = "";

                        if(JO.has("playerClass")){
                            playerClass = JO.getString("playerClass");
                        }

                        if (JO.has("img")) {
                            imgUrl = JO.getString("img");
                        }

                        card = new HearthstoneCard(name, imgUrl, type, playerClass, cardSet);
                        list.add(card);
                        map.put(name, card);

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}



