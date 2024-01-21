package com.example.chatgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class newsGenration extends AppCompatActivity {

    private TextView resultTextView;
    private Button getTopWordsButton;
    private static final String API_KEY = "your_api_key_here";
    private static final String ENDPOINT_URL = "https://newsapi.org/v2/top-headlines";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_genration);
        resultTextView = findViewById(R.id.top_words_textview);
//        getTopWordsButton = findViewById(R.id.top_words_listview);

        getTopWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getTopWords();
            }
        });
    }

//    private void getTopWords() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, getEndpointUrl(),
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            JSONArray articlesArray = jsonResponse.getJSONArray("articles");
//                            List<String> headlines = new ArrayList<>();
//                            for (int i = 0; i < articlesArray.length(); i++) {
//                                JSONObject article = articlesArray.getJSONObject(i);
//                                String title = article.getString("title");
//                                headlines.add(title.toLowerCase());
//                            }
//                            Map<String, Integer> wordCounts = new HashMap<>();
//                            for (String headline : headlines) {
//                                String[] words = headline.split("\\s+");
//                                for (String word : words) {
//                                    if (wordCounts.containsKey(word)) {
//                                        int count = wordCounts.get(word) + 1;
//                                        wordCounts.put(word, count);
//                                    } else {
//                                        wordCounts.put(word, 1);
//                                    }
//                                }
//                            }
//                            Map<String, Integer> sortedWordCounts = sortByValue(wordCounts);
//                            String resultText = "";
//                            for (int i = 0; i < 10; i++) {
//                                String word = (String) sortedWordCounts.keySet().toArray()[i];
//                                int count = sortedWordCounts.get(word);
//                                resultText += word + ": " + count + "\n";
//                            }
//                            resultTextView.setText(resultText);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//
//        new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        resultTextView.setText("Error: " + error.getMessage());
//                    }
//                });
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(stringRequest);
//    }
//
//    private String getEndpointUrl() {
//        String sources = "the-times-of-india";
//        String language = "en";
//        String country = "in";
//        String query = String.format("?apiKey=%s&sources=%s&language=%s&country=%s", API_KEY, sources, language, country);
//        return ENDPOINT_URL + query;
//    }
//
//    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortedMap) {
//        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());
//        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
//        Map<String, Integer> sortedMap = new LinkedHashMap<>();
//        for (Map.Entry<String, Integer> entry : list) {
//            sortedMap.put(entry.getKey(), entry.getValue());
//        }
//        return sortedMap;
//    }
}








