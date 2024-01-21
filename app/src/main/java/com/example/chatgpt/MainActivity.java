package com.example.chatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

//    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

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    RecyclerView recyclerView;
    TextView textView;
    EditText editText;
    ImageButton imageButton;
    List<message> messageList;
    MessageAdapter messageAdapter;

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageView);
        textView = findViewById(R.id.welcome_text);

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editText.getText().toString().trim();
                if(!question.isEmpty()){
                    addtochat(question,message.SENT_BY_ME);
                    editText.setText("");
                    callApi(question);
                    textView.setVisibility(View.GONE);
                }
                else
                    Toast.makeText(MainActivity.this, "please Enter Something", Toast.LENGTH_SHORT).show();

//                Toast.makeText(MainActivity.this, ""+question, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void addtochat(String message,String sentBy){
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  messageList.add(new message(message,sentBy));
                  messageAdapter.notifyDataSetChanged();
                  recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
              }
          });
    }

    //to create image
//    void callApi() {
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put (  "prompt", "car in air");
//            jsonBody.put (  "n", 1);
//            jsonBody. put ( "size", "1024x1024") ;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
//        Request request = new Request.Builder()
//                .url("https://api.openai.com/v1/images/generations")
//                .header("Authorization","Bearer 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")
//                .post(body)
//                .build();
//
//
//                client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.d("output","fail"+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if(response.isSuccessful()){
//                    String r = response.body().string();
//                    Log.d("output","jj " +r);
//                }
//                else
//                    Log.d("output", "jkhg");
//            }
//        });
//
//    }

    void addresponse(String response){
        addtochat(response,message.SENT_BY_BOT);
    }

    void callApi(String question) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put (  "prompt", question);
            jsonBody.put (  "max_tokens", 4000);
            jsonBody.put ( "temperature", 0) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addresponse("Failed to load due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray =jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addresponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    addresponse("Failed to  response due to   "+response.body().toString());
                }
            }
        });

    }
}