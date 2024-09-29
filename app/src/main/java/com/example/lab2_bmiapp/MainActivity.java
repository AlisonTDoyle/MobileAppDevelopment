package com.example.lab2_bmiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Activity elements
        EditText userWeightEditText = findViewById(R.id.userWeightEditText);
        EditText userHeightEditText = findViewById(R.id.userHeightEditText);
        Button calculateBmiButton = findViewById(R.id.calculateBmiButton);
        Button resetFormButton = findViewById(R.id.resetFormButton);

        // Listeners
        calculateBmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Parse int from user input
                int enteredHeight = GetIntFromTextView(userHeightEditText);
                int enteredWeight = GetIntFromTextView(userWeightEditText);

                // Make sure values fall within accepted range
                boolean heightValid = ValidateHeight(enteredHeight);
                boolean weightValid = ValidateWeight(enteredWeight);

                if (heightValid && weightValid) {
                    // Pass value to results page and show new page
                    Intent results = new Intent(view.getContext(), ResultsActivity.class);
                    results.putExtra("weight", enteredWeight);
                    results.putExtra("height", enteredHeight);
                    startActivity(results);

                    ResetForm();
                }
            }
        });

        resetFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetForm();
            }
        });
    }
    private int GetIntFromTextView(TextView textView) {
        int value = 0;

        CharSequence input = textView.getText();

        try {
            value = Integer.parseInt(input.toString());
        } catch (Exception e) {
            ShowToast("Please make sure you have entered a valid number.");
        }

        return value;
    }

    private boolean ValidateHeight(int enteredHeight) {
        boolean isHeightValid = false;
        int minimumHeightInCm = 80;
        int maximumHeightInCm = 300;

        if ((enteredHeight >= minimumHeightInCm) && (enteredHeight <= maximumHeightInCm)) {
            isHeightValid = true;
        }
        else {
            ShowToast("Height must fall between " + minimumHeightInCm + "cm and " + maximumHeightInCm + "cm");
        }

        return isHeightValid;
    }

    private boolean ValidateWeight(int enteredWeight) {
        boolean isWeightValid = false;
        int minimumWeightInCm = 20;
        int maximumWeightInCm = 200;

        if ((enteredWeight >= minimumWeightInCm) && (enteredWeight <= maximumWeightInCm)) {
            isWeightValid = true;
        }
        else {
            ShowToast("Weight must fall between " + minimumWeightInCm + "kg and " + maximumWeightInCm + "kg");
        }

        return isWeightValid;
    }

    private void ShowToast(String toastMessage) {
        // Variables
        int toastDuration = Toast.LENGTH_SHORT;

        // Format toast
        Toast toast = Toast.makeText(this, toastMessage, toastDuration);

        // Display toast
        toast.show();
    }

    private void ResetForm() {
        EditText userWeightEditText = findViewById(R.id.userWeightEditText);
        EditText userHeightEditText = findViewById(R.id.userHeightEditText);

        userHeightEditText.setText("");
        userWeightEditText.setText("");
    }
}