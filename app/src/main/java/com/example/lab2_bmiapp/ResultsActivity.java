package com.example.lab2_bmiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Activity elements
        Button returnButton = findViewById(R.id.returnButton);
        ProgressBar bmiIndicatorProgressBar = findViewById(R.id.bmiIndicatorProgressBar);

        // Retrieve passed values
        Intent intent = getIntent();
        double height = intent.getIntExtra("height", -1);
        int weight = intent.getIntExtra("weight", -1);

        // Calculate bmi
        double bmi = CalculateBmi(weight, height);

        // Present bmi
        TextView bmiResultTextView = findViewById(R.id.bmiResultTextView);
        bmiResultTextView.setText(String.valueOf(bmi));
        bmiIndicatorProgressBar.setProgress((int)bmi);

        // Listeners
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private double CalculateBmi(int weight, double height) {
        double heightInMeters = height / 100;

        double heightSquared = heightInMeters * heightInMeters;
        double bmi = weight / heightSquared;

        double bmiFormatted = Math.round(bmi);

        return bmiFormatted;
    }

    private void ShowToast(String toastMessage) {
        // Variables
        int toastDuration = Toast.LENGTH_SHORT;

        // Format toast
        Toast toast = Toast.makeText(this, toastMessage, toastDuration);

        // Display toast
        toast.show();
    }
}