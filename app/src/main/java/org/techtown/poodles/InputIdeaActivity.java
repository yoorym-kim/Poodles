package org.techtown.poodles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.techtown.poodles.data.ChatGptApiService;
import org.techtown.poodles.data.request.ChatGptRequest;
import org.techtown.poodles.data.RetrofitCreator;
import org.techtown.poodles.data.response.ResponseChatGPT;
import org.techtown.poodles.data.response.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputIdeaActivity extends AppCompatActivity {
    public static final String KEY_ANSWERS = "KEY_ANSWERS";
    public String keywords;
    private PreferenceManager preferenceManager;

    ActivityResultLauncher<Intent> resultListener = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        getIdeas(keywords);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_idea);
        preferenceManager = new PreferenceManager(this);

        // 액션바 제목 설정
        getSupportActionBar().setTitle("아이디어 추천받기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button recommendButton = findViewById(R.id.buttonGetRecommendation);
        EditText ideaEditText = findViewById(R.id.editTextIdeaInput);

        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 아이디어 내용
                String userIdea = ideaEditText.getText().toString();
                getIdeas(userIdea);
            }
        });
        ideaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                keywords = s.toString();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 액션 바의 뒤로 가기 버튼 처리
            case android.R.id.home:
                finish(); // 현재 액티비티를 종료하고 뒤로 이동
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getIdeas(String keywords) {
        ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        ChatGptApiService apiService = new RetrofitCreator().chatGptApiService;
        Call<ResponseChatGPT> call = apiService.getIdeation(
                new ChatGptRequest(
                      keywords
                )
        );

        call.enqueue(new Callback<ResponseChatGPT>() {
            @Override
            public void onResponse(Call<ResponseChatGPT> call, Response<ResponseChatGPT> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    String jsonIdea = response.body().getChoices().get(0).getMessage().getContent();
                    ResponseMessage idea = new Gson().fromJson(jsonIdea, ResponseMessage.class);
                    if(jsonIdea != null && idea != null && idea.getIdeas() != null) {
                        if(idea.getIdeas().size() < 3) {
                            Toast.makeText(InputIdeaActivity.this, "다른 키워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            navToResult(response.body().getChoices().get(0).getMessage().getContent());
                        }
                    } else {
                        Toast.makeText(InputIdeaActivity.this, "다른 키워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(InputIdeaActivity.this, "결과를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseChatGPT> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InputIdeaActivity.this, "결과를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navToResult(String ideaJson) {
        Intent intent = new Intent(this, RecommendationResultActivity.class);
        intent.putExtra(KEY_ANSWERS, ideaJson);
        resultListener.launch(intent);
    }
}




