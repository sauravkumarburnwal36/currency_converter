package com.example.api.currencyConverter.Currency.Converter.controllers;

import com.example.api.currencyConverter.Currency.Converter.clients.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverter currencyConverter;

    @GetMapping(path ="/convertCurrency")
    public Double getCurrencyConverter(@RequestParam String fromCurrency, @RequestParam String toCurrency,
                                                      @RequestParam Long units){
        return currencyConverter.getCurrencyConverter(fromCurrency,toCurrency,units);
    }
}
