package com.example.a1_harrison_pape;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
    public static final String TAG = "Activity_Lifecycle";
    private EditText hoursWorked;
    private EditText payRate;
    private TextView displayText;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hoursWorked = findViewById(R.id.hoursWorked);
        payRate = findViewById(R.id.payRate);
        displayText = findViewById(R.id.displayText);

        db = AppDatabase.getInstance(this);
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
        double tax = grossPay * 0.18;
        double netPay = grossPay - tax;
        db.paymentDao().insert(new Payment(grossPay, overtimePay, tax, netPay));
        displayText(grossPay, overtimePay, tax, netPay);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
    private double TryParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch(NumberFormatException e) {
            return -1;
        }
    }
    private void displayText(double grossPay, double overtimePay, double tax, double netPay) {
        displayText.setText(
                "Gross Pay: " + grossPay + "\n" +
                "Overtime Pay :" + overtimePay + "\n" +
                "Tax: " + tax + "\n" +
                "Net Pay " + netPay);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.details_activity) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Main");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Main");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Main");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Main");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Main");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Main");
    }
}