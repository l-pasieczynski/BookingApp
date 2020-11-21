package com.example.BookingApp.currency.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {
    private String table;
    private String currency;
    private String code;
    private Rate[] rates;

    public Currency() {
    }

    public Currency(String table, String currency, String code, Rate[] rates) {
        this.table = table;
        this.currency = currency;
        this.code = code;
        this.rates = rates;
    }
}
