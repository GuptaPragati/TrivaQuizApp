package com.example.triviaquizapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.example.triviaquizapplication.data.HandleQuestionBankAsysnCall;
import com.example.triviaquizapplication.data.QuestionBank;
import com.example.triviaquizapplication.model.Question;
import com.example.triviaquizapplication.model.Score;
import util.Prefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView cardView;
    ImageButton prevButton;
    ImageButton nextButton;
    Button True;
    TextView quesCounter;
    TextView currentScore;
    TextView highScore;
    TextView ques;
    Button False;
    boolean flag=false;
    private int scoreCounter =0;
    private Prefs prefs;
    private Score score;
    List<Question> questionList;
    private int currentQuestion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView=findViewById(R.id.quesCard);
        prevButton=findViewById(R.id.prev);
        nextButton=findViewById(R.id.next);
        quesCounter=findViewById(R.id.quesCounter);
        currentScore =findViewById(R.id.currentScore);
        highScore =findViewById(R.id.highScore);
        ques=findViewById(R.id.quesns);
        True=findViewById(R.id.TRUE);
        False=findViewById(R.id.FALSE);
        score=new Score();
        prefs=new Prefs(this);
        currentQuestion=prefs.getQuesState();
        flag=false;
        questionList=new QuestionBank().getQuestions(new HandleQuestionBankAsysnCall() {
            @Override
            public void processfinished(ArrayList<Question> questionArrayList) {
              //  Log.d("Json response","Json response is:"+questionArrayList);
                quesCounter.setText(MessageFormat.format("{0} / {1}",currentQuestion,questionList.size()));
                ques.setText(questionArrayList.get(currentQuestion).getQuestion());
            }
        });
        currentScore.setText("Current Score: "+score.getScore());
        highScore.setText("Highest Score: "+prefs.getHighScore());
        cardView.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        True.setOnClickListener(this);
        False.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.TRUE:
                if (!flag) {
                    checkAnswer(true);
                    flag=true;
                    changeQuestion();
                    onClick(findViewById(R.id.next));
                }
                break;
            case R.id.FALSE:
                if (!flag) {
                    checkAnswer(false);
                    flag=true;
                    changeQuestion();
                    onClick(findViewById(R.id.next));
                }
                break;
            case R.id.next:
                currentQuestion= (currentQuestion+1)%questionList.size();
                flag=false;
                changeQuestion();
                break;
            case R.id.prev:
                if (currentQuestion>0)
                {currentQuestion= (currentQuestion-1)%questionList.size();
                    changeQuestion();}
                break;
        }
    }

    private void addPoints()
    {
        scoreCounter = scoreCounter +1;
        score.setScore(scoreCounter);
        currentScore.setText("Current Score: "+ score.getScore());
    }

    private void deductPoints()
    {
        if (scoreCounter>0) {
            scoreCounter = scoreCounter -1;
            score.setScore(scoreCounter);
            currentScore.setText(MessageFormat.format("Current Score: {0}", score.getScore()));
        }
     /*   else
        {
            scoreCounter=0;
            score.setScore(scoreCounter);
            currentScore.setText("Current Score:" + score.getScore());
        }*/
    }

    private void changeQuestion() {
        ques.setText(questionList.get(currentQuestion).getQuestion());
        quesCounter.setText(MessageFormat.format("{0} / {1}",currentQuestion,questionList.size()));
    }

    private void checkAnswer(Boolean userAnswer) {
        Boolean answer=questionList.get(currentQuestion).isAnswerTrue();
        if (userAnswer==answer)
        {
            Toast.makeText(this,"Correct answer",Toast.LENGTH_SHORT).show();
            fadeAnimation();
            addPoints();
        }
        else
        {Toast.makeText(this,"Wrong answer",Toast.LENGTH_SHORT).show();
            shakeAnimationCard();
            deductPoints();
        }
    }

    private void fadeAnimation()
    {
        AlphaAnimation fade=new AlphaAnimation(1.0f,0.0f);
        fade.setDuration(250);
        fade.setRepeatCount(1);
        fade.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(fade);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.parseColor("#FFC107"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimationCard()
    {
        Animation shake= AnimationUtils.loadAnimation(this,R.anim.animation);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.parseColor("#F44336"));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.parseColor("#FFC107"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        prefs.saveHighestScore(score.getScore());
        prefs.setCurrentQuesState(currentQuestion);
        super.onPause();
    }
}
