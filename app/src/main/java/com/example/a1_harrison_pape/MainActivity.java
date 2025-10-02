package com.example.a1_harrison_pape;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText hoursWorked;
    private EditText payRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hoursWorked = findViewById(R.id.hoursWorked);
        payRate = findViewById(R.id.payRate);
    }
    public void confirmPay(View view) {
        double hoursWorkedF = TryParseDouble(hoursWorked.getText().toString().trim());
        double payRateF = TryParseDouble(payRate.getText().toString().trim());
        if(hoursWorkedF < 0 || payRateF < 0) {
            return;
        }
        String msg = String.format(Locale.CANADA,"Hours Worked: %.2f\nPay Rate %.2f", hoursWorkedF, payRateF);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Confirm info is correct.")
                .setMessage(msg)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, w) -> {
                    calculatePay(hoursWorkedF, payRateF);
                })
                .create()
                .show();
    }
    private void calculatePay(double hoursWorked, double payRate) {
        double grossPay;
        double overtimePay = 0;
        if(hoursWorked <= 40) {
            grossPay = hoursWorked * payRate;
        } else {
            overtimePay = (hoursWorked - 40) * payRate * 1.5;
            grossPay = (40 * payRate) + ((hoursWorked - 40) * payRate * 1.5);
        }
        double tax = grossPay * 0.8;
        double netPay = grossPay - tax;
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
    private double TryParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch(NumberFormatException e) {
            return -1;
        }
    }
}