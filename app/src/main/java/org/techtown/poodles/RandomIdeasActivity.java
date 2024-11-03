package org.techtown.poodles;

//import org.techtown.poodles.ChatGPTApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.techtown.poodles.data.ChatGptApiService;
import org.techtown.poodles.data.RetrofitCreator;
import org.techtown.poodles.data.request.ChatGptRequest;
import org.techtown.poodles.data.response.ResponseChatGPT;
import org.techtown.poodles.data.response.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RandomIdeasActivity extends AppCompatActivity {

    private TextView idea1TextView;
    private TextView idea2TextView;
    private TextView idea3TextView;

    private String[] newIdeas = {" "};

    private int currentIdeaIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_ideas);

        // 액션바 제목 설정
        getSupportActionBar().setTitle("랜덤 아이디어");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idea1TextView = findViewById(R.id.Random1TextView);
        idea2TextView = findViewById(R.id.Random2TextView);
        idea3TextView = findViewById(R.id.Random3TextView);


        getIdeas();
        retry();
    }

    private void updateIdeasText() {
        // 인덱스를 증가시키고, 배열의 범위를 초과하지 않도록 조절
        currentIdeaIndex = (currentIdeaIndex + 3) % newIdeas.length;

        // 각 텍스트뷰에 새로운 아이디어 설정
        idea1TextView.setText(newIdeas[currentIdeaIndex]);
        idea2TextView.setText(newIdeas[currentIdeaIndex + 1]);
        idea3TextView.setText(newIdeas[currentIdeaIndex + 2]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateUpToMain();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getIdeas() {
        ProgressBar progressBar = findViewById(R.id.progressbar_random);

        progressBar.setVisibility(View.VISIBLE);
        ChatGptApiService apiService = new RetrofitCreator().chatGptApiService;
        Call<ResponseChatGPT> call = apiService.getIdeation(
                new ChatGptRequest("")
        );

        call.enqueue(new Callback<ResponseChatGPT>() {
            @Override
            public void onResponse(Call<ResponseChatGPT> call, Response<ResponseChatGPT> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    String jsonIdea = response.body().getChoices().get(0).getMessage().getContent();
                    ResponseMessage idea = new Gson().fromJson(jsonIdea, ResponseMessage.class);
                    if(jsonIdea != null && idea != null) {
                        if(idea.getIdeas().isEmpty()) {
                            Toast.makeText(RandomIdeasActivity.this, "다른 키워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            TextView randomTitle = findViewById(R.id.Random1TextView);
                            TextView randomDescription = findViewById(R.id.Random2TextView);

                            randomTitle.setText(idea.getIdeas().get(0).getTitle());
                            randomDescription.setText(idea.getIdeas().get(0).getDescription());

                        }
                    }

                } else {
                    Toast.makeText(RandomIdeasActivity.this, "결과를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseChatGPT> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RandomIdeasActivity.this, "결과를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retry() {
        Button buttonGetAnotherIdea = findViewById(R.id.buttonGetAnotherIdea);
        buttonGetAnotherIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getIdeas();}
        });
    }

    private void navigateUpToMain() {
        Intent upIntent = new Intent(this, MainActivity.class);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(upIntent);
        finish();
    }
}