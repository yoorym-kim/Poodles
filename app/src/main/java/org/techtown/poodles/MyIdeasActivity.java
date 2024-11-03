package org.techtown.poodles;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.techtown.poodles.data.response.ResponseMessage;
import org.w3c.dom.Text;

import java.util.List;

    public class MyIdeasActivity extends AppCompatActivity {
        private MyIdeasManager ideasManager;
        private PreferenceManager preferenceManager;

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_ideas);
            preferenceManager = new PreferenceManager(this);
            TextView idea2TitleTextView = findViewById(R.id.idea2TitleTextView);
            TextView idea2ContentTextView = findViewById(R.id.idea2ContentTextView);
            TextView idea3TitleTextView = findViewById(R.id.idea3TitleTextView);
            TextView idea3ContentTextView = findViewById(R.id.idea3ContentTextView);

            // 액션바 제목 설정
            getSupportActionBar().setTitle("내 아이디어 보관함");
            // 뒤로가기 버튼을 상단바에 추가
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            // SharedPreferences에서 모든 아이디어를 불러옴
            List<ResponseMessage.IdeaModel> allIdeas = null;

            allIdeas = preferenceManager.getIdeaList();

            // 화면에 표시할 텍스트뷰에 모든 아이디어를 설정
            for(int i = 0; i < allIdeas.size(); i++) {
                ResponseMessage.IdeaModel idea = allIdeas.get(i);
                if(i == 0) {
                    idea2TitleTextView.setText(idea.getTitle());
                    idea2ContentTextView.setText(idea.getDescription());
                } else if(i == 1) {
                    idea3TitleTextView.setText(idea.getTitle());
                    idea3ContentTextView.setText(idea.getDescription());
                }
            }

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

