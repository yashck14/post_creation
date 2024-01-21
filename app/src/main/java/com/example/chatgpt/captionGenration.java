package com.example.chatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class captionGenration extends AppCompatActivity {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final int REQUEST_IMAGE = 100;
    TextView captionTextView ;
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption_genration);
        Button selectImageButton = findViewById(R.id.selectImageButton);
        captionTextView = findViewById(R.id.captionTextView);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                generateCaption(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    private void generateCaption(Bitmap bitmap) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        String prompt = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            prompt = "Generate a caption for this image: " + Base64.getEncoder().encodeToString(imageBytes);
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put (  "prompt", prompt.trim());
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
                Log.d("output","kkkkkk"+e.getMessage());
//                captionTextView.setText(e.getMessage().toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray =jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        Log.d("output","bbbbbbb"+result);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                captionTextView.setText(result.trim());
//                            }
//                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Log.d("output","aaaaa"+response.body().string());
//                    captionTextView.setText(response.body().toString());
                }
            }
        });

//        captionTextView.setText(caption);
    }
}
