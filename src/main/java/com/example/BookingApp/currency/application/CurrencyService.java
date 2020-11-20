package com.example.BookingApp.currency.application;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    private RestTemplate restTemplate = new RestTemplate();
}
