package org.techtown.poodles;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InputIdeaActivity extends AppCompatActivity {
    private MyIdeasManager ideasManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_idea);

        // 액션바 제목 설정
        getSupportActionBar().setTitle("아이디어 추천받기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ideasManager = new MyIdeasManager(this);

        Button recommendButton = findViewById(R.id.buttonGetRecommendation);
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 아이디어 내용
                EditText ideaEditText = findViewById(R.id.editTextIdeaInput);
                String userIdea = ideaEditText.getText().toString();

                // 새로운 페이지로 이동하면서 사용자가 입력한 아이디어 전달
                Intent intent = new Intent(InputIdeaActivity.this, RecommendationResultActivity.class);
                intent.putExtra("user_idea", userIdea);
                startActivity(intent);
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
}




