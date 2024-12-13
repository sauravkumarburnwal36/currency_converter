package com.example.api.currencyConverter.Currency.Converter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConverterDTO {
    Map<String,Double> data;
}
