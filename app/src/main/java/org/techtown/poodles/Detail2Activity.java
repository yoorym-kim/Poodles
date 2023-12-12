package org.techtown.poodles;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Detail2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_detail2);

        // 액션바 제목 설정
        getSupportActionBar().setTitle(" ");
        // 뒤로 가기 버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            final String userIdea = intent.getStringExtra("userIdea");



            // 아이디어 저장 버튼 추가
            Button saveButton = findViewById(R.id.buttonSaveToSharedPreferences);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveToSharedPreferences(userIdea);
                    Toast.makeText(Detail2Activity.this, "아이디어가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            /*
                // 아이디어 저장 후 필요한 작업 수행 !!!!!
                // 아이디어 저장 부분 수정 필요 !!!!!!!!!!!!!!!!!!!
                ideasManager.saveIdea(userIdea, RecommendIdeaActivity.this);
                Toast.makeText(RecommendIdeaActivity.this, "아이디어가 저장되었습니다.", Toast.LENGTH_SHORT).show();

                 */

        }
    }

    // SharedPreferences에 데이터 저장
    private void saveToSharedPreferences(String idea) {
        // MyIdeasManager를 사용하여 세 개의 아이디어 데이터를 함께 저장
        MyIdeasManager myIdeasManager = new MyIdeasManager(this);
        myIdeasManager.saveMultipleIdeas("AI 기반 의료영상 진단 솔루션", "아이디어: 의료 영상 데이터를 분석하고 진단을 도와주는 인공지능 기반 의료 영상 진단 솔루션", "내용: X-레이, MRI, CT 스캔 등의 의료 영상 데이터를 학습한 인공지능 모델을 사용하여 진단을 도와주는 소프트웨어를 개발합니다. 정확한 진단을 빠르게 내릴 수 있어 의료진의 업무 효율성을 향상시키고 환자의 치료를 개선할 수 있습니다.", this);
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

    private void saveIdeaToMyIdeas(String idea) {
        try {
            MyIdeasManager myIdeasManager = new MyIdeasManager(this); // Context를 MyIdeasManager에 전달
            myIdeasManager.saveIdea(idea, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

