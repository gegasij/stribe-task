package com.example.spribetask.openexchangerates.service;

import com.example.spribetask.model.CurrencyState;
import com.example.spribetask.openexchangerates.client.OpenExchangeRatesClient;
import com.example.spribetask.openexchangerates.mapper.OERCurrencyToCurrencyMapper;
import com.example.spribetask.openexchangerates.model.OERCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OERCurrencyService {
    private final OpenExchangeRatesClient openExchangeRatesClient;

    public CurrencyState getCurrencyByCode(String code) {
        //i don't have access to api for getting different currencies, so will use only USD rates
        OERCurrency latestCurrencies = openExchangeRatesClient.getLatestCurrencies();

        return Optional.ofNullable(latestCurrencies)
                .map(OERCurrencyToCurrencyMapper::toCurrency)
                .orElseThrow(() -> new RuntimeException("Client returns null value"));
    }

    public List<CurrencyState> getCurrenciesByCodes(List<String> currencyCodes) {
        return currencyCodes.parallelStream()
                .map(this::getCurrencyByCode)
                .collect(Collectors.toList());
    }
}
