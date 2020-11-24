package com.example.BookingApp.currency.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rate {
    public String no;
    public String effectiveDate;
    public double mid;
}
