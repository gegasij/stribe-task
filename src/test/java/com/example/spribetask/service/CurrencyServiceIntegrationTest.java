package com.example.spribetask.service;

import com.example.spribetask.model.Currency;
import com.example.spribetask.model.CurrencyState;
import com.example.spribetask.openexchangerates.service.OERCurrencyService;
import com.example.spribetask.repository.CurrencyRepository;
import com.example.spribetask.repository.CurrencyStateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CurrencyServiceIntegrationTest {

    @Autowired
    private CurrencyService currencyService;

    @MockBean
    private CurrencyStateRepository currencyStateRepository;

    @MockBean
    private CurrencyRepository currencyRepository;

    @MockBean
    private CurrencyCacheService currencyCacheService;

    @MockBean
    private OERCurrencyService oerCurrencyService;

    @Test
    public void testGetByCurrencyCode_CachedCurrency() {

        CurrencyState cachedCurrency = CurrencyState.builder().build();
        cachedCurrency.setCurrency(Currency.builder().code("USD").build());
        when(currencyCacheService.getByCurrencyCode("USD")).thenReturn(cachedCurrency);


        CurrencyState result = currencyService.getByCurrencyCode("USD");


        assertNotNull(result);
        assertEquals("USD", result.getCurrency().getCode());
    }

    @Test
    public void testGetByCurrencyCode_DBNotCachedCurrency() {
        CurrencyState dbCurrency = CurrencyState.builder().build();
        dbCurrency.setCurrency(Currency.builder().code("USD").build());
        when(currencyCacheService.getByCurrencyCode("USD")).thenReturn(null);
        when(currencyStateRepository.findByCode("USD")).thenReturn(Optional.of(dbCurrency));

        CurrencyState result = currencyService.getByCurrencyCode("USD");

        assertNotNull(result);
        assertEquals("USD", result.getCurrency().getCode());
        verify(currencyCacheService, times(1)).put(dbCurrency);
    }

}