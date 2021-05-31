package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class CarMake extends AppCompatActivity {
    private final int[] carImagesList = new int[]{   //images array list
            R.drawable.lamborghini_1,R.drawable.lamborghini_2,R.drawable.lamborghini_3,R.drawable.lamborghini_4,R.drawable.lamborghini_5,R.drawable.lamborghini_6,
            R.drawable.jaguar_1,R.drawable.jaguar_2,R.drawable.jaguar_3,R.drawable.jaguar_4,R.drawable.jaguar_5,R.drawable.jaguar_6,
            R.drawable.benz_1,R.drawable.benz_2,R.drawable.benz_3,R.drawable.benz_4,R.drawable.benz_5,R.drawable.benz_6,
            R.drawable.bmw_1,R.drawable.bmw_2,R.drawable.bmw_3,R.drawable.bmw_4,R.drawable.bmw_5,R.drawable.bmw_6,
            R.drawable.audi_1,R.drawable.audi_2,R.drawable.audi_3,R.drawable.audi_4,R.drawable.audi_5,R.drawable.audi_6,
    };

    ImageView imageView;
    View viewCarMake;
    Spinner spinner;
    TextView resultView, CountDown, nameMessage;
    Button identity;

    private int randomCarNumber ;
    private static final long TIME_MILLIS = 21000;
    private long timeMillis = TIME_MILLIS;
    private CountDownTimer mCountDownTimer;
    private boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        spinner = findViewById(R.id.carSpinner);
        identity =  findViewById(R.id.btn_identify);
        CountDown = findViewById(R.id.timer_car_make);

        Bundle extras = getIntent().getExtras();                            // timer countdown
        if (extras != null) {                                              // https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
            switchState = extras.getBoolean("SWITCH_MESSAGE");
        }

        if(switchState) {
            mCountDownTimer = new CountDownTimer(timeMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeMillis = millisUntilFinished;
                    updateCountDownText();
                    if(identity.getText().toString().equals("Next")){
                        mCountDownTimer.cancel();
                    }
                }

                @Override
                public void onFinish() {
                    CountDown.setText("Finished");
                    if(spinner.getSelectedItemPosition() == 0){
                        resultView.setText(R.string.wrong_msg);
                        resultView.setTextColor(Color.parseColor("#ab5c5c"));
                        identity.setText(R.string.next);
                        identity.setOnClickListener(v -> reloadNewPage());
                    }else{
                        identifyCarImage(viewCarMake);
                    }
                }
            }.start();

        }

        imageView = findViewById(R.id.carMakeImg_1);                            //find imageView
        randomCarNumber = (int)(Math.random() * carImagesList.length );         //select random number to choose car image
        imageView.setImageResource(carImagesList[randomCarNumber]);             //set new car image using random number
    }

    public void identifyCarImage(View view) {
        resultView = findViewById(R.id.result);
        nameMessage = findViewById(R.id.nameMessage);

        String choice = spinner.getSelectedItem().toString();               //select spinner name equals choice

        if (randomCarNumber >= 0 && randomCarNumber <= 5){                  //using array list index and checks names are correct or wrong
            if(choice.equals("Lamborghini")){
                resultView.setText(R.string.correct_msg);
                resultView.setTextColor(Color.parseColor("#66b05f"));
            }else{
                resultView.setText(R.string.wrong_msg);
                resultView.setTextColor(Color.parseColor("#ab5c5c"));
                nameMessage.setText(R.string.answer_Lamborghini);
            }
            identity.setText(R.string.next);                                        // reload new screen
            identity.setOnClickListener(v -> reloadNewPage());
        }
        else if(randomCarNumber >= 6 && randomCarNumber <= 11){
            if(choice.equals("Jaguar")){
                resultView.setText(R.string.correct_msg);
                resultView.setTextColor(Color.parseColor("#66b05f"));
            }else{
                resultView.setText(R.string.wrong_msg);
                resultView.setTextColor(Color.parseColor("#ab5c5c"));nameMessage.setText(R.string.answer_jaguar);
            }
            identity.setText(R.string.next);
            identity.setOnClickListener(v -> reloadNewPage());
        }
        else if(randomCarNumber >= 12 && randomCarNumber <= 17){
            if(choice.equals("Mercedes Benz")){
                resultView.setText(R.string.correct_msg);
                resultView.setTextColor(Color.parseColor("#66b05f"));
            }else{
                resultView.setText(R.string.wrong_msg);
                resultView.setTextColor(Color.parseColor("#ab5c5c"));
                nameMessage.setText(R.string.answer_benz);
            }
            identity.setText(R.string.next);
            identity.setOnClickListener(v -> reloadNewPage());
        }
        else if(randomCarNumber >= 18 && randomCarNumber <= 23){
            if(choice.equals("BMW")){
                resultView.setText(R.string.correct_msg);
                resultView.setTextColor(Color.parseColor("#66b05f"));
            }else{
                resultView.setText(R.string.wrong_msg);
                resultView.setTextColor(Color.parseColor("#ab5c5c"));
                nameMessage.setText(R.string.answer_bmw);
            }
            identity.setText(R.string.next);
            identity.setOnClickListener(v -> reloadNewPage());
        }
        else if(randomCarNumber >= 24 && randomCarNumber <= 29){
            if(choice.equals("Audi")){
                resultView.setText(R.string.correct_msg);
                resultView.setTextColor(Color.parseColor("#66b05f"));
            }else{
                resultView.setText(R.string.wrong_msg);
                resultView.setTextColor(Color.parseColor("#ab5c5c"));
                nameMessage.setText(R.string.answer_audi);
            }
            identity.setText(R.string.next);
            identity.setOnClickListener(v -> reloadNewPage());
        }
        else{
            Toast toast = Toast.makeText(this, "Pick the Answer", Toast.LENGTH_SHORT); //display message pick the answer
            toast.show();
        }
    }

    private void updateCountDownText(){                         // https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
        int minutes = (int) (timeMillis / 1000) / 60;           // timer countdown
        int seconds = (int) (timeMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d",minutes, seconds);
        CountDown.setText(timeLeftFormatted);
    }

    private void reloadNewPage() {
        Intent i = new Intent(this, CarMake.class);
        i.putExtra("SWITCH_MESSAGE", switchState);
        startActivity(i);

    }
}