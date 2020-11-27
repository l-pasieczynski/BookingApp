package com.example.BookingApp.currency.application;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrencyData {

    private String currency;
    private String code;
    private Double rate;

}
