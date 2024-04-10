package com.example.spribetask.openexchangerates.model;

import lombok.Value;

import java.util.Map;

@Value
public class OERCurrency {
    long timestamp;
    String base;
    Map<String, Double> rates;
}
