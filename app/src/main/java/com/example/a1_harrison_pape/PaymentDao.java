package com.example.a1_harrison_pape;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PaymentDao {
    @Insert
    void insert(Payment payment);

    @Query("SELECT * FROM payments ORDER BY id DESC")
    List<Payment> getAllPayments();
}
