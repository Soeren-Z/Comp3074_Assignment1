package com.example.a1_harrison_pape;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.NumberFormat;

@Entity(tableName = "payments")
public class Payment {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "gross_pay")
    public double grossPay;
    @ColumnInfo(name = "overtime_pay")
    public double overtimePay;
    @ColumnInfo(name = "tax")
    public double tax;
    @ColumnInfo(name = "net_pay")
    public double netPay;

    public Payment(double grossPay, double overtimePay, double tax, double netPay) {
        this.grossPay = grossPay;
        this.overtimePay = overtimePay;
        this.tax = tax;
        this.netPay = netPay;
    }
    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        return "Payment Number: " + id + "\n" +
                "Gross Pay: " + currencyFormat.format(grossPay) + "\n" +
                "Overtime Pay: " + currencyFormat.format(overtimePay) + "\n" +
                "Tax: " + currencyFormat.format(tax) + "\n" +
                "Net Pay: " + currencyFormat.format(netPay);
    }
}
