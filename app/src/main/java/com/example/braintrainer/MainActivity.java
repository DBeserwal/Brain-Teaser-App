package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    TextView problemTextView;
    TextView scoreTextView;
    TextView resultTextView;
    TextView finalScoreTextView;
    TextView highScoreTextView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playButton;

    CountDownTimer countDownTimer;


    ArrayList<Integer> answers;

    int correctAnswer;
    int correctPosition;
    float score = 0f;
    float totalQuestions = 0f;
    Integer optionTapped;

    SharedPreferences sharedPreferences;

    Float previousAccuracy = 0f;
    Float previousMaxQuest = 0f;
    Float previousScore = 0f;



    public void funcOptionTapped(View view){

        totalQuestions++;
        optionTapped = Integer.parseInt((String) view.getTag());

        if(optionTapped == correctPosition)
        {
           score++;
           resultTextView.setText("Correct !");
           resultTextView.setVisibility(View.VISIBLE);


        }

        else{
            resultTextView.setText("Incorrect !");
            resultTextView.setVisibility(View.VISIBLE);
        }

        scoreTextView.setText((int)score + "/" + (int)totalQuestions);

        generateNewProblem();

    }

    public void funcPlay(View view){

        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void generateNewProblem (){

        answers = new ArrayList<Integer>();

        Random rand = new Random();

        int problem1 = rand.nextInt(26);
        int problem2 = rand.nextInt(26);

        problemTextView.setText(Integer.toString(problem1) + " + " + Integer.toString(problem2));

        correctAnswer = problem1 + problem2;



        correctPosition = rand.nextInt(4);

        for(int i = 0; i < 4; i++){

            if(i == correctPosition)
                answers.add(correctAnswer);

            else
                answers.add(rand.nextInt(51));

        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerTextView = (TextView) findViewById(R.id.timerTextView);
        problemTextView = (TextView) findViewById(R.id.problemTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        finalScoreTextView = (TextView) findViewById(R.id.finalScoreTextView);
        highScoreTextView = (TextView) findViewById(R.id.highScoreTextView);


        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playButton = (Button) findViewById(R.id.playButton);

        playButton.setEnabled(false);

        sharedPreferences = this.getSharedPreferences("com.example.braintrainer", MODE_PRIVATE);

        previousAccuracy = sharedPreferences.getFloat("accuracy", 0f);

        previousMaxQuest = sharedPreferences.getFloat("maxQuest", 0f);

        previousScore = sharedPreferences.getFloat("maxScore", 0f);

        answers = new ArrayList<Integer>();



        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(Integer.toString((int)millisUntilFinished/1000) + "s");

            }

            @Override
            public void onFinish() {

                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);

                playButton.setEnabled(true);

                resultTextView.setVisibility(View.INVISIBLE);

                finalScoreTextView.setText("Your Score : " + (int) score + "/" + (int)totalQuestions);
                finalScoreTextView.setVisibility(View.VISIBLE);

                Float currentAccuracy = (score/totalQuestions)*100;

                Float currentTotalQuest = totalQuestions;

                Float currentScore = score;

                if(currentAccuracy >= previousAccuracy && currentTotalQuest > previousMaxQuest){

                    sharedPreferences.edit().putFloat("accuracy", currentAccuracy).apply();

                    sharedPreferences.edit().putFloat("maxQuest", currentTotalQuest).apply();

                    sharedPreferences.edit().putFloat("maxScore", currentScore).apply();


                    Toast.makeText(MainActivity.this, "New High Score !!", Toast.LENGTH_SHORT).show();

                    Log.i("New", "HighScore");
                }

                Log.i("Current Total Question:", Float.toString(totalQuestions) + " Previous Max Questions:" + Float.toString(previousMaxQuest));
                Log.i("Curerent Accuracy:", Float.toString(currentAccuracy) + " Previous Accuracy:" + Float.toString(previousAccuracy));

                float a = sharedPreferences.getFloat("maxScore", 0f);
                float b = sharedPreferences.getFloat("maxQuest", 0f);


                highScoreTextView.setText("High Score : " + (int)a + "/" + (int)b);
                highScoreTextView.setVisibility(View.VISIBLE);

            }
        }.start();

        Random rand = new Random();

        int problem1 = rand.nextInt(26);
        int problem2 = rand.nextInt(26);

        problemTextView.setText(Integer.toString(problem1) + " + " + Integer.toString(problem2));

        correctAnswer = problem1 + problem2;



        correctPosition = rand.nextInt(4);

        for(int i = 0; i < 4; i++){

            if(i == correctPosition)
                answers.add(correctAnswer);

            else
                answers.add(rand.nextInt(51));

        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }
}