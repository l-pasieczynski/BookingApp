package com.example.BookingApp.currency.application;

import com.example.BookingApp.currency.model.Rate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NBPCurrencyData {
    public String table;
    public String currency;
    public String code;
    public Rate[] rates;
}

