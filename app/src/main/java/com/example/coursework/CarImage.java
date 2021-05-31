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

import java.util.Locale;

public class CarImage extends AppCompatActivity {
    private final int[] carImagesList = new int[]{          //image array list
            R.drawable.lamborghini_1,R.drawable.lamborghini_2,R.drawable.lamborghini_3,R.drawable.lamborghini_4,R.drawable.lamborghini_5,R.drawable.lamborghini_6,
            R.drawable.jaguar_1,R.drawable.jaguar_2,R.drawable.jaguar_3,R.drawable.jaguar_4,R.drawable.jaguar_5,R.drawable.jaguar_6,
            R.drawable.benz_1,R.drawable.benz_2,R.drawable.benz_3,R.drawable.benz_4,R.drawable.benz_5,R.drawable.benz_6,
            R.drawable.bmw_1,R.drawable.bmw_2,R.drawable.bmw_3,R.drawable.bmw_4,R.drawable.bmw_5,R.drawable.bmw_6,
            R.drawable.audi_1,R.drawable.audi_2,R.drawable.audi_3,R.drawable.audi_4,R.drawable.audi_5,R.drawable.audi_6,
    };

    ImageView imageViewOne, imageViewTwo, imageViewThree;
    TextView resultView, ranCarName, CountDown;
    Button nextBtn;

    int randomCarNumber1, randomCarNumber2, randomCarNumber3;
    String carNamesRandom1, carNamesRandom2, carNamesRandom3;


    private long timeMillis;
    private CountDownTimer mCountDownTimer;
    private boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        resultView = findViewById(R.id.result_view);
        CountDown = findViewById(R.id.timer_car_img);
        nextBtn = findViewById(R.id.btn_next);
        ranCarName = findViewById(R.id.carName);

        timerCountdown(); //timer
        //car_one
        //find imageView
        do {
            imageViewOne = findViewById(R.id.car_one);
            randomCarNumber1 = (int) (Math.random() * carImagesList.length);    //select random number to choose car image
            imageViewOne.setImageResource(carImagesList[randomCarNumber1]);        //set new car image using random number

            //car_two
            //find imageView
            imageViewTwo = findViewById(R.id.car_two);
            randomCarNumber2 = (int) (Math.random() * carImagesList.length);    //select random number to choose car image
            imageViewTwo.setImageResource(carImagesList[randomCarNumber2]);        //set new car image using random number

            //car_three
            //find imageView
            imageViewThree = findViewById(R.id.car_three);
            randomCarNumber3 = (int) (Math.random() * carImagesList.length);    //select random number to choose car image
            imageViewThree.setImageResource(carImagesList[randomCarNumber3]);        //set new car image using random number

            carNamesRandom1 = selectCar(randomCarNumber1);
            carNamesRandom2 = selectCar(randomCarNumber2);
            carNamesRandom3 = selectCar(randomCarNumber3);

        }while((carNamesRandom1.equals(carNamesRandom2)) || (carNamesRandom1.equals(carNamesRandom3)) || (carNamesRandom2.equals(carNamesRandom3)));


        int nameRandom = (int) (Math.random() * 3);             //display random  one car name randomly
        if(nameRandom == 0){
            String carName = selectCar(randomCarNumber1);
            ranCarName.setText(carName);
        }else if(nameRandom == 1){
            String carName = selectCar(randomCarNumber2);
            ranCarName.setText(carName);
        }else {
            String carName = selectCar(randomCarNumber3);
            ranCarName.setText(carName);
        }
    }

    private String selectCar(int carNumber) {           //search car name

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

    public void carOneClick(View view) {                        //if select car image equals display random name then enable next button
        String carName = selectCar(randomCarNumber1);           //close timer
        checkResult(carName);
        nextBtn.setEnabled(true);
        if (switchState) {
            mCountDownTimer.cancel();
        }
    }

    public void carTwoClick(View view) {
        String carName = selectCar(randomCarNumber2);
        checkResult(carName);
        nextBtn.setEnabled(true);
        if (switchState) {
            mCountDownTimer.cancel();
        }
    }

    public void carThreeClick(View view) {
        String carName = selectCar(randomCarNumber3);
        checkResult(carName);
        nextBtn.setEnabled(true);
        if (switchState) {
            mCountDownTimer.cancel();
        }
    }

    private void checkResult(String carName) {                      //check select image correct or wrong

        if (ranCarName.getText().toString().equals(carName)){
            resultView.setText(R.string.correct_msg);
            resultView.setTextColor(Color.parseColor("#66b05f"));
        }else{
            resultView.setText(R.string.wrong_msg);
            resultView.setTextColor(Color.parseColor("#ab5c5c"));
        }
        imageViewOne.setEnabled(false);                             //unable images
        imageViewTwo.setEnabled(false);
        imageViewThree.setEnabled(false);
    }

    private void timerCountdown() {
        Bundle extras = getIntent().getExtras();                                                // https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
        if (extras != null) {                                                                    // use "coding in flow" site for countdown
            switchState = extras.getBoolean("SWITCH_MESSAGE");
        }
        if (switchState) {
            mCountDownTimer = new CountDownTimer(21000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    CountDown.setText("Finished");
                    resultView.setText(R.string.wrong_msg);
                    resultView.setTextColor(Color.parseColor("#ab5c5c"));
                    nextBtn.setEnabled(true);

                }
            }.start();
        }
    }

    private void updateCountDownText(){
        int minutes = (int) (timeMillis / 1000) / 60;                         // https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
        int seconds = (int) (timeMillis / 1000) % 60;                          // use "coding in flow" site for countdown
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d",minutes, seconds);
        CountDown.setText(timeLeftFormatted);
    }

    public void nextButtonClick(View view) {
        Intent i = new Intent(this, CarImage.class);
        i.putExtra("SWITCH_MESSAGE", switchState);                      //sending extra data to another activity (switch)
        startActivity(i);
    }
}
