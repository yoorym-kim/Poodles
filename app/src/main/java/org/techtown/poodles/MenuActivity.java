package org.techtown.poodles;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 액션바 제목 설정
        getSupportActionBar().setTitle("MENU");
        // ActionBar에 뒤로가기 버튼 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button recommendIdeaButton = findViewById(R.id.buttonRecommendIdea);
        recommendIdeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, InputIdeaActivity.class));
            }
        });

        Button randomIdeaButton = findViewById(R.id.buttonRandomIdea);
        randomIdeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, RandomIdeasActivity.class));
            }
        });

        Button myIdeasButton = findViewById(R.id.buttonMyIdeas);
        myIdeasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MyIdeasActivity.class));
            }
        });

        Button settingButton = findViewById(R.id.buttonSetting);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SettingsActivity.class));
            }
        });
    }

    // ActionBar의 뒤로가기 버튼 이벤트 처리
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
