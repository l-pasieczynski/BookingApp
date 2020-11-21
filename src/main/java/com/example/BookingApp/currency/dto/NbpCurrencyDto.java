package com.example.BookingApp.currency.dto;

import com.example.BookingApp.currency.model.Currency;
import com.example.BookingApp.currency.model.Rate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NbpCurrencyDto {
    private Currency currency;
    private Rate rate;

    public NbpCurrencyDto() {
    }

    public NbpCurrencyDto(Currency currency, Rate rate) {
        this.currency = currency;
        this.rate = rate;
    }
}
