package com.example.spribetask.service;

import com.example.spribetask.model.Currency;
import com.example.spribetask.model.CurrencyState;
import com.example.spribetask.openexchangerates.service.OERCurrencyService;
import com.example.spribetask.repository.CurrencyRepository;
import com.example.spribetask.repository.CurrencyStateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyStateRepository currencyStateRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyCacheService currencyCacheService;
    private final OERCurrencyService oerCurrencyService;

    public CurrencyState getByCurrencyCode(String code) {
        Optional<CurrencyState> cachedCurrency = Optional.ofNullable(currencyCacheService.getByCurrencyCode(code));

        if (cachedCurrency.isPresent()) {
            return cachedCurrency.get();
        }
        Optional<CurrencyState> dbCurrency = currencyStateRepository.findByCode(code);

        if (dbCurrency.isPresent()) {
            currencyCacheService.put(dbCurrency.get());
            return dbCurrency.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found");
        }
    }

    public void getNewCurrencyStateAndUpdateCache() {
        List<String> usedCurrencies = getUsedCurrencies();
        List<CurrencyState> currenciesByCodes = oerCurrencyService.getCurrenciesByCodes(usedCurrencies);
        currencyCacheService.updateCache(currenciesByCodes);
    }

    public List<String> getUsedCurrencies() {
        return currencyCacheService.getCurrencyList();
    }

    public void saveNewCurrency(String code) {
        if (Optional.ofNullable(currencyCacheService.getByCurrencyCode(code))
                .map(it -> currencyStateRepository.findByCode(code))
                .isEmpty()) {
            trackNewCurrency(code);
        }
    }

    @Transactional
    private void trackNewCurrency(String code) {
        CurrencyState currencyByCode = oerCurrencyService.getCurrencyByCode(code);
        CurrencyState savedCurrency = currencyStateRepository.save(currencyByCode);
        currencyRepository.save(Currency.builder().code(savedCurrency.getCurrency().getCode()).build());
        currencyCacheService.addNewTrackingCurrency(savedCurrency);
    }

}
