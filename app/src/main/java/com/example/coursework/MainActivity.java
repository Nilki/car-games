package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    boolean switchState;
    Switch switchControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchControl = findViewById(R.id.switch_timer);
        switchControl.setOnCheckedChangeListener((buttonView, isChecked) -> switchState = isChecked);   //checks if the switch is on or off
    }

    public void carMake(View view) {                                            //buttons
        Intent i = new Intent(this, CarMake.class);
        i.putExtra("SWITCH_MESSAGE", switchState);
        startActivity(i);
    }

    public void hints(View view) {
        Intent i = new Intent(this, Hints.class);
        i.putExtra("SWITCH_MESSAGE", switchState);
        startActivity(i);
    }

    public void carImage(View view) {
        Intent i = new Intent(this, CarImage.class);
        i.putExtra("SWITCH_MESSAGE", switchState);
        startActivity(i);
    }

    public void Advanced(View view) {
        Intent i = new Intent(this, Advanced.class);
        i.putExtra("SWITCH_MESSAGE", switchState);
        startActivity(i);
    }
}