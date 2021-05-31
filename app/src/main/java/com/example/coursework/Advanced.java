package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class Advanced extends AppCompatActivity {
    private final int[] carImagesList = new int[]{
            R.drawable.lamborghini_1,R.drawable.lamborghini_2,R.drawable.lamborghini_3,R.drawable.lamborghini_4,R.drawable.lamborghini_5,R.drawable.lamborghini_6,
            R.drawable.jaguar_1,R.drawable.jaguar_2,R.drawable.jaguar_3,R.drawable.jaguar_4,R.drawable.jaguar_5,R.drawable.jaguar_6,
            R.drawable.benz_1,R.drawable.benz_2,R.drawable.benz_3,R.drawable.benz_4,R.drawable.benz_5,R.drawable.benz_6,
            R.drawable.bmw_1,R.drawable.bmw_2,R.drawable.bmw_3,R.drawable.bmw_4,R.drawable.bmw_5,R.drawable.bmw_6,
            R.drawable.audi_1,R.drawable.audi_2,R.drawable.audi_3,R.drawable.audi_4,R.drawable.audi_5,R.drawable.audi_6,
    };

    ImageView imageViewOne, imageViewTwo, imageViewThree;
    EditText carOneName, carTwoName, carThreeName;
    TextView score, result, answer, CountDown;
    Button submit;

    int attempt = 3;
    int randomCarNumber1, randomCarNumber2, randomCarNumber3 ;
    String carNamesRandom1, carNamesRandom2, carNamesRandom3;
    String firstCarName, secondCarName, thirdCarName;

    private long timeMillis;
    private CountDownTimer mCountDownTimer;
    private boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        CountDown = findViewById(R.id.timer_advanced);
        submit = findViewById(R.id.submit_btn);

        timerCountdown();

        //car_one
        //find imageView
        do {
            imageViewOne = findViewById(R.id.carOne);
            randomCarNumber1 = (int) (Math.random() * carImagesList.length);    //select random number to choose car image
            imageViewOne.setImageResource(carImagesList[randomCarNumber1]);        //set new car image using random number

            //car_two
            //find imageView
            imageViewTwo = findViewById(R.id.carTwo);
            randomCarNumber2 = (int) (Math.random() * carImagesList.length);    //select random number to choose car image
            imageViewTwo.setImageResource(carImagesList[randomCarNumber2]);        //set new car image using random number

            //car_three
            //find imageView
            imageViewThree = findViewById(R.id.carThree);
            randomCarNumber3 = (int) (Math.random() * carImagesList.length);    //select random number to choose car image
            imageViewThree.setImageResource(carImagesList[randomCarNumber3]);        //set new car image using random number
            // search car name using random number
            carNamesRandom1 = selectCar(randomCarNumber1);
            carNamesRandom2 = selectCar(randomCarNumber2);
            carNamesRandom3 = selectCar(randomCarNumber3);

        }while((carNamesRandom1.equals(carNamesRandom2)) || (carNamesRandom1.equals(carNamesRandom3)) || (carNamesRandom2.equals(carNamesRandom3)));
    }
    //search car name
    private String selectCar(int carNumber) {

        if (carNumber >= 0 && carNumber <= 5){
            return "Lamborghini";
        }
        else if(carNumber >= 6 && carNumber <= 11){
            return "Jaguar";
        }
        else if(carNumber >= 12 && carNumber <= 17){
            return "Benz";
        }
        else if(carNumber >= 18 && carNumber <= 23){
            return "BMW";
        }
        else{
            return "Audi";
        }
    }

    public void submitAnswer(View view) {
        carOneName = findViewById(R.id.carOneName);
        carTwoName = findViewById(R.id.carTwoName);
        carThreeName = findViewById(R.id.carThreeName);
        result = findViewById(R.id.result_advanced);
        score = findViewById(R.id.score_a);
        answer = findViewById(R.id.correct_answers);

        int currentScore = 0;

        firstCarName = carOneName.getText().toString();
        secondCarName = carTwoName.getText().toString();
        thirdCarName = carThreeName.getText().toString();

            if (attempt != 0){                                                                      //1,2,3 attempts
                if (firstCarName.toLowerCase().equals(carNamesRandom1.toLowerCase())) {             //checks input car names equals random car name
                    carOneName.setBackgroundColor(Color.parseColor("#66b05f"));           //check correct or wrong
                    carOneName.setEnabled(false);
                    currentScore++;                                                                 // get score
                } else {
                    carOneName.setBackgroundColor(Color.parseColor("#ab5c5c"));
                }
                if (secondCarName.toLowerCase().equals(carNamesRandom2.toLowerCase())) {
                    carTwoName.setBackgroundColor(Color.parseColor("#66b05f"));
                    carTwoName.setEnabled(false);
                    currentScore++;
                } else {
                    carTwoName.setBackgroundColor(Color.parseColor("#ab5c5c"));
                }
                if (thirdCarName.toLowerCase().equals(carNamesRandom3.toLowerCase())) {
                    carThreeName.setBackgroundColor(Color.parseColor("#66b05f"));
                    carThreeName.setEnabled(false);
                    currentScore++;
                } else {
                    carThreeName.setBackgroundColor(Color.parseColor("#ab5c5c"));
                }
                attempt--;                              // reduce attempt one by one
                if (switchState) {                       // start timer again
                    mCountDownTimer.cancel();
                    timerCountdown();
                }

                if (firstCarName.toLowerCase().equals(carNamesRandom1.toLowerCase()) &&         // all car names are correct display CORRECT message, final
                    secondCarName.toLowerCase().equals(carNamesRandom2.toLowerCase()) &&        // score and submit button changes next button
                    thirdCarName.toLowerCase().equals(carNamesRandom3.toLowerCase()) ){
                    result.setText(R.string.correct_msg);
                    result.setTextColor(Color.parseColor("#66b05f"));
                    score.setText("Score - " + currentScore);
                    submit.setText(R.string.next);
                    submit.setOnClickListener(v -> reloadNewPage());
                }else{
                    score.setText("Score - " + currentScore);                                   // attempt 0
                    if(attempt == 0){                                                           //stop timer
                        result.setText(R.string.wrong_msg);                                     //car names are incorrect then display wrong message and display next button
                        result.setTextColor(Color.parseColor("#ab5c5c"));
                        correctAnswers();
                        submit.setText(R.string.next);
                        submit.setOnClickListener(v -> reloadNewPage());
                        if (switchState) {                       // start timer again
                            mCountDownTimer.cancel();
                        }
                    }
                }
            }
        }

    private void timerCountdown() {                     //timer countdown method
        Bundle extras = getIntent().getExtras();        //to send data across intent
        if (extras != null) {
            switchState = extras.getBoolean("SWITCH_MESSAGE");
        }
        if (switchState) {
            mCountDownTimer = new CountDownTimer(21000, 1000) {     // https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
                @Override                                                                       // "coding in flow"
                public void onTick(long millisUntilFinished) {
                    timeMillis = millisUntilFinished;
                    updateCountDownText();
                    String submitText = submit.getText().toString();

                    if (submitText.toUpperCase().equals("NEXT")) {
                        mCountDownTimer.cancel();
                    }
                }
                @Override
                public void onFinish() {
                    CountDown.setText("Finished");
                }
            }.start();
        }
    }

    private void correctAnswers() {         // display correct answers
        if(!firstCarName.toLowerCase().equals(carNamesRandom1.toLowerCase()) &&
           !secondCarName.toLowerCase().equals(carNamesRandom2.toLowerCase()) &&
           !thirdCarName.toLowerCase().equals(carNamesRandom3.toLowerCase())){
            answer.setText(" Correct Answers are " + " 1. " + carNamesRandom1 + " 2. " + carNamesRandom2+ " 3. " +carNamesRandom3);
        }else if (!firstCarName.toLowerCase().equals(carNamesRandom1.toLowerCase()) &&
                !secondCarName.toLowerCase().equals(carNamesRandom2.toLowerCase())){
            answer.setText(" Correct Answers are " +" 1. "+ carNamesRandom1 + " 2. " + carNamesRandom2);
        }else if(!firstCarName.toLowerCase().equals(carNamesRandom1.toLowerCase()) && !thirdCarName.toLowerCase().equals(carNamesRandom3.toLowerCase())){
            answer.setText(" Correct Answers are " + " 1. " + carNamesRandom1 + " 3. " + carNamesRandom3);
        }else if (!secondCarName.toLowerCase().equals(carNamesRandom2.toLowerCase()) && !thirdCarName.toLowerCase().equals(carNamesRandom3.toLowerCase())){
            answer.setText("Correct Answers are " + " 2. "+ carNamesRandom2 + " 3. " + carNamesRandom3);
        }else if (!firstCarName.toLowerCase().equals(carNamesRandom1.toLowerCase())){
            answer.setText(" Correct Answer is " + carNamesRandom1 );
        }else if (!secondCarName.toLowerCase().equals(carNamesRandom2.toLowerCase())){
            answer.setText(" Correct Answer is " + carNamesRandom2);
        }else if (!thirdCarName.toLowerCase().equals(carNamesRandom3.toLowerCase())){
            answer.setText(" Correct Answer is " + carNamesRandom3);
        }
    }

    private void updateCountDownText(){
        int minutes = (int) (timeMillis / 1000) / 60;               // https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
        int seconds = (int) (timeMillis / 1000) % 60;               // use "coding in flow" site for countdown
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d",minutes, seconds);
        CountDown.setText(timeLeftFormatted);
    }

    private void reloadNewPage() {                                      //new screen
        Intent i = new Intent(this, Advanced.class);
        i.putExtra("SWITCH_MESSAGE", switchState);                //sending extra data to another activity (switch)
        startActivity(i);
        attempt = 3;
    }
}