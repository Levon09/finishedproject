package com.example.carsquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class oldActivity extends AppCompatActivity implements View.OnClickListener {

    public static String question[] = {
            "Which car is older? "
    };
    public static String choices[][] = {
            {"Mercedes GLE400", "Mercedes G63", "BMW x6", "Lamborghini Urus"},
            {"Bugatti Divo", "Koenigsegg Jesko", "Koenigsegg One", "Bugatti Chiron Sport"},
            {"Koenigsegg One", "Lamborghini Huracan", "Pagani Zonda R", "Koenigsegg Jesko"},
            {"BMW m5 competition", "Mercedes GT63", "Porsche 911 GT3Rs", "Ferrari 458 Italia"},
            {"Ferrari Laferrari", "Lamborghini Aventador", "Lotus Exige S", "Lamborghini Huracan"},
            {"Ford Mustang Shelby", "Porsche 918 Spyder", "Chevrolet Corvette C7", "Chevrolet Camaro"},
            {"Buggati Chiron Sport", "Lamborghini Veneno", "Ferrari F40", "McLaren P1"},
            {"Dodge Challanger SRT8", "BMW m3", "Porsche 911 GT2Rs", "Porsche Carerra Gt"},
            {"Mercedes GT", "Dodge Challanger SRT8", "Ford Mustang Shelby", "BMW m6"},
            {"Koenigsegg One", "Bugatti Veyron", "Ferrari Laferrari", "Pagani Zonda R"},
    };

    public static String correctAnswers[] = {
            "Mercedes GLE400",
            "Koenigsegg One",
            "Pagani Zonda R",
            "Ferrari 458 Italia",
            "Lotus Exige S",
            "Chevrolet Corvette C7",
            "Ferrari F40",
            "Porsche Carrera GT",
            "Dodge Challenger SRT8",
            "Pagani Zonda R",
    };
    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score=0;
    int totalQuestion = question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old);

        questionTextView = findViewById(R.id.questionTextView);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        loadNewQuestion();


    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit) {
            if (selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
                score++;
                if (selectedAnswer.equals(choices[currentQuestionIndex][0])){
                    ansA.setBackgroundColor(Color.GREEN);
                }
                if (selectedAnswer.equals(choices[currentQuestionIndex][1])){
                    ansB.setBackgroundColor(Color.GREEN);
                }
                if (selectedAnswer.equals(choices[currentQuestionIndex][2])){
                    ansC.setBackgroundColor(Color.GREEN);
                }
                if (selectedAnswer.equals(choices[currentQuestionIndex][3])){
                    ansD.setBackgroundColor(Color.GREEN);
                }

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentQuestionIndex++;
                        loadNewQuestion();
                    }
                }, 1000);
////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            else {
                if (selectedAnswer.equals(choices[currentQuestionIndex][0])){
                    ansA.setBackgroundColor(Color.RED);
                }
                if (selectedAnswer.equals(choices[currentQuestionIndex][1])){
                    ansB.setBackgroundColor(Color.RED);
                }
                if (selectedAnswer.equals(choices[currentQuestionIndex][2])){
                    ansC.setBackgroundColor(Color.RED);
                }
                if (selectedAnswer.equals(choices[currentQuestionIndex][3])){
                    ansD.setBackgroundColor(Color.RED);
                }
/////////////////////////////////////////////////////////////////////////////////////////////////////

                if (correctAnswers[currentQuestionIndex].equals(choices[currentQuestionIndex][0])){
                    ansA.setBackgroundColor(Color.GREEN);
                }
                if (correctAnswers[currentQuestionIndex].equals(choices[currentQuestionIndex][1])){
                    ansB.setBackgroundColor(Color.GREEN);
                }
                if (correctAnswers[currentQuestionIndex].equals(choices[currentQuestionIndex][2])){
                    ansC.setBackgroundColor(Color.GREEN);
                }
                if (correctAnswers[currentQuestionIndex].equals(choices[currentQuestionIndex][3])){
                    ansD.setBackgroundColor(Color.GREEN);
                }

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentQuestionIndex++;
                        loadNewQuestion();
                    }
                }, 1000);
            }

        }
        else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.BLUE);

        }

    }

    ///////////////////////////////////////////////////////////////
    void loadNewQuestion() {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        if (currentQuestionIndex == 10) {
            finishQuiz();
            return;
        }

        ansA.setText(choices[currentQuestionIndex][0]);
        ansB.setText(choices[currentQuestionIndex][1]);
        ansC.setText(choices[currentQuestionIndex][2]);
        ansD.setText(choices[currentQuestionIndex][3]);

    }

    //////////////////////////////////////////////////////////////////////////////////////
    void finishQuiz() {
        String passStatus = "";
        if (score > 6) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + 10)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();


    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

}