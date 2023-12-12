package org.techtown.poodles;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.techtown.poodles.ChatGPTApi;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class RecommendationResultActivity extends AppCompatActivity {

    private TextView idea1TextView;
    private TextView idea2TextView;
    private TextView idea3TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_result);

        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idea1TextView = findViewById(R.id.idea1TextView);
        idea2TextView = findViewById(R.id.idea2TextView);
        idea3TextView = findViewById(R.id.idea3TextView);

        String userIdea = getIntent().getStringExtra("user_idea");
        new GetGPTIdeasTask(this).execute(userIdea);

        Button buttonIdea1 = findViewById(R.id.buttonIdea1);
        buttonIdea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecommendationResultActivity.this, Detail1Activity.class));
            }
        });

        Button buttonIdea2 = findViewById(R.id.buttonIdea2);
        buttonIdea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecommendationResultActivity.this, Detail2Activity.class));
            }
        });

        Button buttonIdea3 = findViewById(R.id.buttonIdea3);
        buttonIdea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecommendationResultActivity.this, DetailsActivity.class));
            }
        });
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

    private static class GetGPTIdeasTask extends AsyncTask<String, Void, String> {
        private WeakReference<RecommendationResultActivity> activityReference;

        public GetGPTIdeasTask(RecommendationResultActivity activity) {
            this.activityReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            String userInput = params[0];
            try {
                return ChatGPTApi.getIdeasFromGPT(userInput);
            } catch (IOException e) {
                e.printStackTrace();
                return "ChatGPT에서 아이디어를 가져오는 중 오류가 발생했습니다.";
            }
        }

        @Override
        protected void onPostExecute(String GPTIdeas) {
            RecommendationResultActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            String[] ideasArray = GPTIdeas.split("\n");

            TextView idea1TextView = activity.findViewById(R.id.idea1TextView);
            TextView idea2TextView = activity.findViewById(R.id.idea2TextView);
            TextView idea3TextView = activity.findViewById(R.id.idea3TextView);

            idea1TextView.setText(ideasArray.length >= 1 ? ideasArray[0] : "");
            idea2TextView.setText(ideasArray.length >= 2 ? ideasArray[1] : "");
            idea3TextView.setText(ideasArray.length >= 3 ? ideasArray[2] : "");
        }
    }
}
