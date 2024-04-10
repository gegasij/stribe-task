package com.example.spribetask.controller;

import com.example.spribetask.mapper.CurrencyMapper;
import com.example.spribetask.model.CurrencyState;
import com.example.spribetask.model.dto.CurrencyStateDTO;
import com.example.spribetask.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    @GetMapping
    ResponseEntity<List<String>> getCurrencies() {
        return ResponseEntity.ok(currencyService.getUsedCurrencies());
    }

    @GetMapping("/exchange-rates/{currency_code}")
    ResponseEntity<CurrencyStateDTO> getCurrencyExchangeRate(@PathVariable(name = "currency_code") String currencyCode) {
        CurrencyState byCurrencyCodeStatus = currencyService.getByCurrencyCode(currencyCode);
        return ResponseEntity.ok(currencyMapper.CurrencyToCurrencyDTO(byCurrencyCodeStatus));
    }

    @PostMapping
    ResponseEntity<Void> addNewCurrency(@RequestBody String currencyCode) {
        currencyService.saveNewCurrency(currencyCode);
        return ResponseEntity.ok().build();
    }

}
