package com.example.chatgpt;

import static com.example.chatgpt.MainActivity.JSON;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class createPost extends AppCompatActivity {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    ImageView imageView;
    Button button;
    EditText editText;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        imageView = findViewById(R.id.imageView2);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.post_idea);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rs = editText.getText().toString();
                if(!rs.isEmpty()){
                    editText.setText("");
                    callApi(rs);
                }
                else
                    Toast.makeText(createPost.this, "please enter your idea", Toast.LENGTH_SHORT).show();

            }
        });

    }

        void callApi(String question) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("prompt",question);
            jsonBody.put("n",1);
            jsonBody.put( "size","1024x1024") ;
        } catch (
    JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.body().string());
                                JSONArray jsonArray =jsonObject.getJSONArray("data");
                                result = jsonArray.getJSONObject(0).getString("url");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Perform UI operation here, such as updating a TextView
                                        Picasso.get().load(result).into(imageView);
                                    }
                                });
                                Log.d("output","jj " +result);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
//                            String r = response.body().string();
//                            Log.d("output","jj " +r);
                        }
                        else
                            Log.d("output", "jkhg");
                    }


                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("output","fail"+e.getMessage());
                    }
        });


    }
}