package com.example.lab2_bmiapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
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

        // Retrieve passed values
        Intent intent = getIntent();
        double height = intent.getIntExtra("height", -1);
        int weight = intent.getIntExtra("weight", -1);

        // Calculate bmi
        double bmi = CalculateBmi(weight, height);

        PresentBmi(bmi);

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

    private void PresentBmi(double bmi) {
        // Activity elements
        ProgressBar bmiIndicatorProgressBar = findViewById((R.id.bmiIndicatorProgressBar));
        TextView bmiResultTextView = findViewById(R.id.bmiResultTextView);
        TextView summaryTextView = findViewById(R.id.summaryTextView);

        // String create summary
        String weightClass = "";
        if (bmi < 18.5) {
            weightClass = "underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            weightClass = "normal";
        } else if (bmi >= 25 && bmi < 29.9) {
            weightClass = "overweight";
        } else if (bmi >= 30 && bmi < 39.9) {
            weightClass = "obese";
        } else {
            weightClass = "severely obese";
        }

        GradientDrawable gradient = createGradientWithStops();

        Drawable[] layers = new Drawable[2];
        layers[0] = new ColorDrawable(Color.parseColor("#848484")); // Background
        layers[1] = gradient; // Gradient progress

        // Step 4: Apply the gradient as the ProgressBar's progress drawable
         bmiIndicatorProgressBar.setBackgroundColor(Color.parseColor("#848484"));
         bmiIndicatorProgressBar.setProgressDrawable(gradient);

        // Present bmi details
        bmiResultTextView.setText(String.valueOf(bmi));
        bmiIndicatorProgressBar.setProgress((int)bmi);
        summaryTextView.setText("With your BMI, you are classed as " + weightClass);
    }

    private GradientDrawable createGradientWithStops() {
        int[] colors = {
                Color.parseColor("#E6C929"), // Light Yellow
                Color.parseColor("#57B658"), // Green
                Color.parseColor("#E6C929"), // Yellow
                Color.parseColor("#DA843F"), // Orange
                Color.parseColor("#d3333b")  // Red
        };
        float[] stops = {
                0.0f, 0.25f, 0.5f, 0.75f, 1.0f
        };

        GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        return gradient;
    }
}