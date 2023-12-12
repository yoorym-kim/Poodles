package org.techtown.poodles;

//import org.techtown.poodles.ChatGPTApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RandomIdeasActivity extends AppCompatActivity {

    private TextView idea1TextView;
    private TextView idea2TextView;
    private TextView idea3TextView;

    private String[] newIdeas = {
            "자동 운전 보조 시스템을 활용한 편의주택 도어 관리 시스템",
            "아이디어: 자동 운전 보조 시스템과 IoT 기술을 활용하여 편의주택의 출입문을 스마트하게 관리하는 시스템",
            "내용: 차량이 편의주택에 접근할 때, 자동 운전 보조 시스템을 통해 차량이 자동으로 출입문에 접근하고, 주택 주인의 허가 여부를 음성 및 비주얼 인터페이스를 통해 확인합니다. 이를 통해 주택 주인은 원격으로 출입문을 개방하거나 거부할 수 있습니다.",

            "환자 맞춤형 AI 의료 정보 제공 플랫폼",
            "아이디어: 개별 환자의 건강 상태와 선호도를 고려하여 맞춤형 의료 정보를 제공하는 플랫폼",
            "내용: 환자는 의료 기록, 건강 상태, 선호하는 언어 및 커뮤니케이션 방식 등을 플랫폼에 입력합니다. 인공지능은 이 정보를 분석하여 환자에게 맞춤형 건강 정보, 치료 계획, 예방 조언 등을 제공합니다. 이를 통해 환자들은 더 나은 의료 서비스를 받을 수 있습니다.\n"
    };

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

        Button buttonGetAnotherIdea = findViewById(R.id.buttonGetAnotherIdea);
        buttonGetAnotherIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 여기에서 새로운 아이디어를 추천받는 로직을 추가할 수 있습니다.
                        // 텍스트 업데이트 메서드 호출
                        updateIdeasText();
                    }
                }, 2500);
                updateIdeasText();
            }
        });




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

    private void navigateUpToMain() {
        Intent upIntent = new Intent(this, MainActivity.class);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(upIntent);
        finish();
    }
}