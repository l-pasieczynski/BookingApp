package com.example.BookingApp.currency.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrencyDto {

    private String currency;
    private String code;
    private Double rate;

}
