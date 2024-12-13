package com.example.api.currencyConverter.Currency.Converter.clients.impl;

import com.example.api.currencyConverter.Currency.Converter.clients.CurrencyConverter;
import com.example.api.currencyConverter.Currency.Converter.dto.CurrencyConverterDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger log= LoggerFactory.getLogger(CurrencyConverterImpl.class);
    @Override
    public Double getCurrencyConverter(String fromCurrency, String toCurrency, Long units) {
        log.trace("Trying to Call Free Currency API in getCurrencyConverter");
        CurrencyConverterDTO currencyConverterDTO=restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/latest")
                        .queryParam("currencies",toCurrency)
                        .queryParam("base_currency",fromCurrency)
                        .build())
                .header("apiKey",apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                    log.debug("in4xxclient error due to:");
                    log.error(new String(res.getBody().readAllBytes()));
                    throw new HttpClientErrorException(res.getStatusCode(),res.getBody().toString());
                }).body(new ParameterizedTypeReference<>() {
                });
        log.debug("Successfully triggered the API");
        log.trace("API Returned Rates: {}",currencyConverterDTO.getData());
        return currencyConverterDTO.getData().get(toCurrency)*units;
    }
}

