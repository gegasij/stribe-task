package com.example.spribetask.repository;

import com.example.spribetask.model.CurrencyState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface CurrencyStateRepository extends JpaRepository<CurrencyState, Integer> {
    Optional<CurrencyState> findByCode(String code);
    @Query("SELECT cs FROM CurrencyState cs " +
            "WHERE cs.currency.code IN :currencyCodes " +
            "AND cs.timestamp = (SELECT MAX(cs2.timestamp) FROM CurrencyState cs2 WHERE cs2.currency = cs.currency)")
    List<CurrencyState> findLatestByCurrencyCodes(List<String> currencyCodes);
}
