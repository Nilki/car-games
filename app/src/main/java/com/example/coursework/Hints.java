package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hints extends AppCompatActivity {
    private final int[] carImagesList = new int[]{
            R.drawable.lamborghini_1,R.drawable.lamborghini_2,R.drawable.lamborghini_3,R.drawable.lamborghini_4,R.drawable.lamborghini_5,R.drawable.lamborghini_6,
            R.drawable.jaguar_1,R.drawable.jaguar_2,R.drawable.jaguar_3,R.drawable.jaguar_4,R.drawable.jaguar_5,R.drawable.jaguar_6,
            R.drawable.benz_1,R.drawable.benz_2,R.drawable.benz_3,R.drawable.benz_4,R.drawable.benz_5,R.drawable.benz_6,
            R.drawable.bmw_1,R.drawable.bmw_2,R.drawable.bmw_3,R.drawable.bmw_4,R.drawable.bmw_5,R.drawable.bmw_6,
            R.drawable.audi_1,R.drawable.audi_2,R.drawable.audi_3,R.drawable.audi_4,R.drawable.audi_5,R.drawable.audi_6,
    };

    int attempt = 3;
    String randomCarName , tempName;
    StringBuilder stringBuilder;
    ArrayList<String> carName = new ArrayList<>();

    View view;
    ImageView imageViewHint;
    TextView resultText, correctAnswerText, nameView, letterBox, CountDown;
    Button submitBtn;

    private long timeMillis;
    private CountDownTimer mCountDownTimer;
    private boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);
        submitBtn = findViewById(R.id.submit_btn_hint);
        CountDown = findViewById(R.id.timer_hint);
        nameView = findViewById(R.id.name_view);

        timerCountdown();

        imageViewHint = findViewById(R.id.hint_image);                          //find imageView
        int randomCarNumber = (int) (Math.random() * carImagesList.length);     //select random number to choose car image
        imageViewHint.setImageResource(carImagesList[randomCarNumber]);         //set new car image using random number

        randomCarName = selectCar(randomCarNumber).toLowerCase();               //display "_" according to car name length
        for (int i=0; i<=randomCarName.length()-1; i++) {
            carName.add("-");
        }
        String displayName = " ";                                               //use space between dashes
        for(String str : carName ){
            displayName = displayName + str + " ";
        }
        nameView.setText(displayName);
    }

    private String selectCar(int carNumber) {                               //search car names using index number

        if (carNumber >= 0 && carNumber <= 5){
            return "Lamborghini";
        }
        else if(carNumber >= 6 && carNumber <= 11){
            return "jaguar";
        }
        else if(carNumber >= 12 && carNumber <= 17){
            return "benz";
        }
        else if(carNumber >= 18 && carNumber <= 23){
            return "bmw";
        }
        else{
            return "audi";
        }
    }

    public void submitAnswer(View view) {
        letterBox = findViewById(R.id.letter_box);
        resultText = findViewById(R.id.result_hint);
        correctAnswerText = findViewById(R.id.correct_answer);

        String displayName = "";
        String newLetter = letterBox.getText().toString().toLowerCase();

        if (attempt != 1) {
            letterBox.setText("");
            if ((newLetter.equals("")) || (!randomCarName.contains(newLetter))) {
                attempt--;
                if (switchState) {
                    mCountDownTimer.cancel();
                    timerCountdown();
                }
            }
            if (randomCarName.contains(newLetter)) {
                correctAnswer(newLetter, displayName);

                if (tempName.equals(randomCarName)) {
                    resultText.setText(R.string.correct_msg);
                    resultText.setTextColor(Color.parseColor("#66b05f"));
                    submitBtn.setText(R.string.next);
                    submitBtn.setOnClickListener(v -> reloadNewPage());
                }
            }
        } else {
            if (randomCarName.contains(newLetter)) {
                correctAnswer(newLetter, displayName);
                if (tempName.equals(randomCarName)) {
                    resultText.setText(R.string.correct_msg);
                    resultText.setTextColor(Color.parseColor("#66b05f"));
                    submitBtn.setText(R.string.next);
                    submitBtn.setOnClickListener(v -> reloadNewPage());
                }
            } else {
                correctAnswerText.setText(randomCarName);
                resultText.setText(R.string.wrong_msg);
                resultText.setTextColor(Color.parseColor("#ab5c5c"));
                submitBtn.setText(R.string.next);
                submitBtn.setOnClickListener(v -> reloadNewPage());
            }
        }
    }
    private void correctAnswer(String newLetter, String displayName){
        Pattern guessText = Pattern.compile(newLetter);                     //Regular expressions can be used to perform all types
        Matcher matcher = guessText.matcher(randomCarName);                 //of text search and text replace operations

        ArrayList<Integer> tempList = new ArrayList<>();

        while (matcher.find()) {
            tempList.add(matcher.start());
        }
        for (Integer integer : tempList) {
            carName.set(integer, newLetter);
        }
        stringBuilder = new StringBuilder();
        for (String str : carName) {
            displayName = displayName + str + " ";
            stringBuilder.append(str);
        }
        nameView.setText(displayName);
        tempName = stringBuilder.toString();
    }

    private void timerCountdown() {                                     // https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
        Bundle extras = getIntent().getExtras();                        // use "coding in flow" site for countdown
        if (extras != null) {
            switchState = extras.getBoolean("SWITCH_MESSAGE");
        }
        if (switchState) {
            mCountDownTimer = new CountDownTimer(21000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeMillis = millisUntilFinished;
                    updateCountDownText();
                    String submitText = submitBtn.getText().toString();

                    if (submitText.toUpperCase().equals("NEXT")) {
                        mCountDownTimer.cancel();
                    }
                }

                @Override
                public void onFinish() {
                    CountDown.setText("Finished");
                    if(attempt != 1){
                        submitAnswer(view);
                        mCountDownTimer.start();
                    }else{
                        submitAnswer(view);
                    }
                }
            }.start();
        }
    }
    private void updateCountDownText(){
        int minutes = (int) (timeMillis / 1000) / 60;               // https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
        int seconds = (int) (timeMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d",minutes, seconds);
        CountDown.setText(timeLeftFormatted);
    }

    private void reloadNewPage() {
        Intent i = new Intent(this, Hints.class);               //sending extra data to another activity (switch)
        i.putExtra("SWITCH_MESSAGE", switchState);
        startActivity(i);
    }
}

