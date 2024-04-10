package com.example.spribetask.service;

import com.example.spribetask.model.Currency;
import com.example.spribetask.model.CurrencyState;
import com.example.spribetask.repository.CurrencyRepository;
import com.example.spribetask.repository.CurrencyStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CurrencyCacheService {
    private final ConcurrentHashMap<String, CurrencyState> customCache =
            new ConcurrentHashMap<>();
    private final List<String> currencyList = new ArrayList<>();

    @Autowired
    public CurrencyCacheService(final CurrencyStateRepository currencyStateRepository,
                                final CurrencyRepository currencyRepository) {
        List<Currency> allCurrenciesUsed = currencyRepository.findAll();
        List<String> list = allCurrenciesUsed.stream().map(Currency::getCode).toList();

        currencyList.addAll(list);
        List<CurrencyState> latestByCurrencyCodes = currencyStateRepository.findLatestByCurrencyCodes(list);
        latestByCurrencyCodes.forEach(currencyState -> customCache.put(currencyState.getCurrency().getCode(), currencyState));
    }

    public void put(CurrencyState currencyState) {
        customCache.put(currencyState.getCurrency().getCode(), currencyState);
    }

    public CurrencyState getByCurrencyCode(String currencyCode) {
        return customCache.get(currencyCode);
    }

    public List<String> getCurrencyList() {
        return new ArrayList<>(currencyList);
    }

    public void addNewTrackingCurrency(CurrencyState currencyState) {
        currencyList.add(currencyState.getCurrency().getCode());
        customCache.put(currencyState.getCurrency().getCode(), currencyState);
    }

    public void updateCache(List<CurrencyState> updateList) {
        updateList.forEach(it -> customCache.put(it.getCurrency().getCode(), it));
    }
}
