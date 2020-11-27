package com.example.BookingApp.currency.client;

import com.example.BookingApp.currency.application.CurrencyData;
import com.example.BookingApp.currency.application.NBPCurrencyData;
import com.example.BookingApp.currency.model.Rate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Component
public class CurrencyClient {

    private static final String NBP_URL = "http://api.nbp.pl/api/exchangerates/rates/";
    private LocalDate yesterday = LocalDate.now().minusDays(1);
    private RestTemplate restTemplate = new RestTemplate();


    public Double getDollarValue() {
        return getCurrency(yesterday, "usd").getRate();
    }

    public Double getEuroValue() {
        return getCurrency(yesterday, "eur").getRate();
    }

    private CurrencyData getCurrency(LocalDate date, String currencyCode) {
        NBPCurrencyData NBPCurrencyData = restTemplate.getForObject(NBP_URL + "a/" + currencyCode + "/" + date + "/?format=json", NBPCurrencyData.class);
//        NbpCurrencyDto nbpCurrencyDto = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/rates/a/{currencyCode}/{date}/?format=json", NbpCurrencyDto.class);
        if (NBPCurrencyData != null) {
            Double value = 0.00;
            for (Rate rate : NBPCurrencyData.getRates()) {
                value = rate.getMid();
            }
            return CurrencyData.builder()
                    .currency(NBPCurrencyData.getCurrency())
                    .code(NBPCurrencyData.getCode())
                    .rate(value)
                    .build();
        }
        throw new NoSuchElementException();
    }
}
