package com.example.api.currencyConverter.Currency.Converter.clients;

public interface CurrencyConverter {
    Double getCurrencyConverter(String fromCurrency, String toCurrency, Long units);
}
