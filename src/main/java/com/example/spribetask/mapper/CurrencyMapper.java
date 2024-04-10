package com.example.spribetask.mapper;

import com.example.spribetask.model.CurrencyState;
import com.example.spribetask.model.dto.CurrencyStateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyStateDTO CurrencyToCurrencyDTO(CurrencyState source);
}
