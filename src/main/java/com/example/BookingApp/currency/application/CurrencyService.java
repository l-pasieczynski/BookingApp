package com.example.BookingApp.currency.application;

import com.example.BookingApp.currency.client.CurrencyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyClient currencyClient;

    public Double midDollarValue(){
        return currencyClient.getDollarValue();
    }

    public Double midEuroValue(){
        return currencyClient.getEuroValue();
    }
}
