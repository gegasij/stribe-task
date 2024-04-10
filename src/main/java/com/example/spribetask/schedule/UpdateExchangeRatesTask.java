package com.example.spribetask.schedule;

import com.example.spribetask.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateExchangeRatesTask {
    private final CurrencyService currencyService;

    @Scheduled(cron = "${scheduling.cronExpression}")
    public void updateRates() {
        currencyService.getNewCurrencyStateAndUpdateCache();
    }
}
