package org.techtown.poodles;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

    public class MyIdeasActivity extends AppCompatActivity {
        private MyIdeasManager ideasManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_ideas);

            // 액션바 제목 설정
            getSupportActionBar().setTitle("내 아이디어 보관함");
            // 뒤로가기 버튼을 상단바에 추가
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ideasManager = new MyIdeasManager(this);

            // SharedPreferences에서 모든 아이디어를 불러옴
            List<String> allIdeas = ideasManager.loadAllIdeas(this);

            // 화면에 표시할 텍스트뷰에 모든 아이디어를 설정
            StringBuilder displayText = new StringBuilder();
            for (String idea : allIdeas) {
                displayText.append(idea).append("\n");
            }
            /*
            TextView displayTextView = findViewById(R.id.displayTextView);
            displayTextView.setText(displayText.toString());
             */
        }






        // 뒤로가기 버튼 클릭 시 동작 정의
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    // 뒤로 가기 버튼 클릭 시 홈 화면으로 이동
                    navigateUpToMain();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        // 홈 화면으로 이동하는 메서드
        private void navigateUpToMain() {
            // 홈 화면으로 이동하는 코드 추가
            // 예를 들어 MainActivity.class 대신에 실제 홈 화면의 Activity 클래스를 사용
            Intent upIntent = new Intent(this, MainActivity.class);
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(upIntent);
            finish();
        }


    }

