package org.techtown.poodles;

import android.util.Log;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ChatGPTApi {

    private static final String TAG = "ChatGPTApi";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";

    public void getIdeasFromGPTAsync(String userInput, ChatGPTCallback callback) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject jsonBody = new JSONObject();
        JSONArray messagesArray = new JSONArray();

        try {
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", userInput);

            messagesArray.put(userMessage);

            jsonBody.put("model", "gpt-3.5-turbo");
            jsonBody.put("messages", messagesArray);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure(new IOException("JSON creation error", e));
            return;
        }

        RequestBody requestBody = RequestBody.create(mediaType, jsonBody.toString());

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        Log.d(TAG, "Request: " + request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                callback.onResponse(responseBody);
                Log.d(TAG, "Response: " + responseBody);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
                Log.e(TAG, "Request failed", e);
            }
        });
    }

    public static String getIdeasFromGPT(String userInput) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject jsonBody = new JSONObject();
        JSONArray messagesArray = new JSONArray();

        try {
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", userInput);

            messagesArray.put(userMessage);

            jsonBody.put("model", "gpt-3.5-turbo");
            jsonBody.put("messages", messagesArray);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException("JSON creation error", e);
        }

        RequestBody requestBody = RequestBody.create(mediaType, jsonBody.toString());

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        Log.d(TAG, "Request: " + request.toString());

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            Log.d(TAG, "Response: " + responseBody);
            return responseBody;
        }
    }

    public interface ChatGPTCallback {
        void onResponse(String response);
        void onFailure(IOException e);
    }
}
