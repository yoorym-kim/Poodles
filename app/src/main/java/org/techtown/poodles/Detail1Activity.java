package org.techtown.poodles;

import static org.techtown.poodles.RecommendationResultActivity.IDEA_DETAIL;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.techtown.poodles.data.response.ResponseMessage;

public class Detail1Activity extends AppCompatActivity {
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_detail);

        preferenceManager = new PreferenceManager(this);

        // 액션바 제목 설정
        getSupportActionBar().setTitle(" ");
        // 뒤로 가기 버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIdea();
        Intent intent = getIntent();
        if (intent != null) {
            final String userIdea = intent.getStringExtra("userIdea");


        }
    }

    private void getIdea() {
        try {
            String ideaJson = getIntent().getStringExtra(IDEA_DETAIL);
            ResponseMessage.IdeaModel idea = new Gson().fromJson(ideaJson, ResponseMessage.IdeaModel.class);
            TextView textViewTitle = findViewById(R.id.idea1Title);
            TextView textViewDescription = findViewById(R.id.idea1Detail1);

            textViewTitle.setText(idea.getTitle());
            textViewDescription.setText(idea.getDescription());
            saveToSharedPreferences(idea);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    // SharedPreferences에 데이터 저장
    private void saveToSharedPreferences(ResponseMessage.IdeaModel idea) {
        // MyIdeasManager를 사용하여 세 개의 아이디어 데이터를 함께 저장
        Button saveButton = findViewById(R.id.buttonSaveToSharedPreferences);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.saveIdea(idea);


                Toast.makeText(Detail1Activity.this, "아이디어가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // 뒤로 가기 버튼 클릭 시 동작 정의
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed(); // 시스템에서 제공하는 뒤로 가기 동작 호출
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

