package com.example.BookingApp.currency.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rate {
    private String no;
    private String effectiveDate;
    private double mid;

    public Rate() {
    }

    public Rate(String no, String effectiveDate, double mid) {
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.mid = mid;
    }
}
