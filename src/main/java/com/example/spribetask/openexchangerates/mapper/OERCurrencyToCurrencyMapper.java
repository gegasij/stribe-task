package com.example.spribetask.openexchangerates.mapper;

import com.example.spribetask.model.Currency;
import com.example.spribetask.model.CurrencyState;
import com.example.spribetask.openexchangerates.model.OERCurrency;

public class OERCurrencyToCurrencyMapper {
    public static CurrencyState toCurrency(OERCurrency oerCurrency) {
        return CurrencyState.builder()
                .currency(Currency.builder().code(oerCurrency.getBase()).build())
                .timestamp(oerCurrency.getTimestamp())
                .exchangeRates(oerCurrency.getRates())
                .build();
    }

}
