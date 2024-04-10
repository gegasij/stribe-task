package com.example.spribetask.openexchangerates.client;

import com.example.spribetask.openexchangerates.configuration.FeignClientConfiguration;
import com.example.spribetask.openexchangerates.model.OERCurrency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "openexchangerates-latest-api",
        url = "${openexchangerates.endpoint}",
        configuration = FeignClientConfiguration.class
)
public interface OpenExchangeRatesClient {
    @GetMapping("/latest.json")
    OERCurrency getLatestCurrencies();
}
