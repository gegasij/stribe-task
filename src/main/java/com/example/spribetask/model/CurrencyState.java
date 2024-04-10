package com.example.spribetask.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Map;


@Getter
@Setter
@Builder
@Entity
@Table(name = "Currency_state")
public class CurrencyState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    long timestamp;

    @Column(name = "exchange_rates", columnDefinition = "jsonb")
    Map<String, Double> exchangeRates;
}
