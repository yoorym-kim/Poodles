package org.techtown.poodles;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Detail1Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_detail);

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
                    Toast.makeText(Detail1Activity.this, "아이디어가 저장되었습니다.", Toast.LENGTH_SHORT).show();
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
        myIdeasManager.saveMultipleIdeas("임상시험 혁신을 위한 블록체인 플랫폼", "아이디어: 블록체인 기술을 활용하여 임상시험 프로세스를 투명하게 관리하고 혁신적인 방법으로 참여자 모집 및 데이터 수집을 개선하는 플랫폼", "내용: 블록체인은 데이터 무결성과 보안을 강화할 수 있는 기술로, 임상시험에서의 데이터 관리를 혁신적으로 개선할 수 있습니다. 스마트 계약을 활용하여 참여자 간 계약과 보상을 효율적으로 처리하고, 거래 내역을 블록체인에 기록하여 투명성을 확보합니다.", this);
        myIdeasManager.saveIdea("영화처럼 지나가버렸어요 그렇게 순식간에 한 해가 끝나가요 1 작은 그 숫자 안에 온갖 이야기들이 끝과 시작을 맞아요 모두 다 함께 말이에요 내가 바랬던 것 하지만 안 이루어진 것 차가운 공기 속에 후후 불어 날려 보내야죠 뭐 얼어붙은 손에 바램을 담아서 자그만 온기라도 호오 예쁘게 녹여야죠 뭐 어쩌겠어요 이상해 이렇게 추운데 다 괜찮을 것 같은 기분 그림을 그릴래 하얗게 덮인 추억 위에 또다른 소원을 마음을 사랑을",this);
        myIdeasManager.loadIdea(this);
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

