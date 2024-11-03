package org.techtown.poodles;

import static org.techtown.poodles.InputIdeaActivity.KEY_ANSWERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.techtown.poodles.data.response.ResponseMessage;

public class RecommendationResultActivity extends AppCompatActivity {
    public static final String IDEA_DETAIL = "IDEA_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_result);
        getAnswers();
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retry();
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

    private void getAnswers() {
        String jsonIdeas = getIntent().getStringExtra(KEY_ANSWERS);
        ResponseMessage idea = new Gson().fromJson(jsonIdeas, ResponseMessage.class);

        TextView idea1TextView = findViewById(R.id.idea1TextView);
        TextView idea1ContentTextView = findViewById(R.id.idea1ContentTextView);

        TextView idea2TextView = findViewById(R.id.idea2TextView);
        TextView idea2ContentTextView = findViewById(R.id.idea1ContentTextView);

        TextView idea3TextView = findViewById(R.id.idea3TextView);
        TextView idea3ContentTextView = findViewById(R.id.idea1ContentTextView);

        try {
            if(idea.getIdeas() != null && idea.getIdeas().size() >= 3) {
                ResponseMessage.IdeaModel firstIdea = idea.getIdeas().get(0);
                ResponseMessage.IdeaModel secondIdea = idea.getIdeas().get(1);
                ResponseMessage.IdeaModel thirdIdea = idea.getIdeas().get(2);

                idea1TextView.setText(firstIdea.getTitle());
                idea1ContentTextView.setText(firstIdea.getDescription());
                navToDetail1(firstIdea);

                idea2TextView.setText(secondIdea.getTitle());
                idea2ContentTextView.setText(secondIdea.getDescription());
                navToSecondIdea(secondIdea);

                idea3TextView.setText(thirdIdea.getTitle());
                idea3ContentTextView.setText(thirdIdea.getDescription());
                navToThirdIdea(thirdIdea);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void navToDetail1(ResponseMessage.IdeaModel ideaModel) {
        Button buttonIdea1 = findViewById(R.id.buttonIdea1);
        buttonIdea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {navToDetailActivity(ideaModel);}
        });
    }

    private void navToSecondIdea(ResponseMessage.IdeaModel ideaModel) {
        Button buttonIdea2 = findViewById(R.id.buttonIdea2);
        buttonIdea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {navToDetailActivity(ideaModel);}
        });
    }

    private void navToThirdIdea(ResponseMessage.IdeaModel ideaModel) {
        Button buttonIdea3 = findViewById(R.id.buttonIdea3);
        buttonIdea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {navToDetailActivity(ideaModel);}
        });
    }

    private void retry() {
        Button buttonGetAnotherIdea = findViewById(R.id.buttonGetAnotherIdea);
        buttonGetAnotherIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void navToDetailActivity(ResponseMessage.IdeaModel ideaModel) {
        Intent intent = new Intent(RecommendationResultActivity.this, Detail1Activity.class);
        intent.putExtra(IDEA_DETAIL, new Gson().toJson(ideaModel));
        startActivity(intent);
    }
}
