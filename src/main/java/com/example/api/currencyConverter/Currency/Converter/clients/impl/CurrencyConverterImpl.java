package com.example.api.currencyConverter.Currency.Converter.clients.impl;

import com.example.api.currencyConverter.Currency.Converter.clients.CurrencyConverter;
import com.example.api.currencyConverter.Currency.Converter.dto.CurrencyConverterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CurrencyConverterImpl implements CurrencyConverter {
    private final RestClient restClient;

    @Value("${base.api.key}")
    private String apiKey;

    @Override
    public Double getCurrencyConverter(String fromCurrency, String toCurrency, Long units) {
        CurrencyConverterDTO currencyConverterDTO=restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/latest")
                        .queryParam("currencies",toCurrency)
                        .queryParam("base_currency",fromCurrency)
                        .build())
                .header("apiKey",apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                    throw new HttpClientErrorException(res.getStatusCode(),res.getBody().toString());
                }).body(new ParameterizedTypeReference<>() {
                });
        return currencyConverterDTO.getData().get(toCurrency)*units;
    }
}

