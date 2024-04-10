package com.example.spribetask.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCurrencies() throws Exception {
        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetCurrencyExchangeRate() throws Exception {
        String currencyCode = "USD";

        mockMvc.perform(get("/currencies/exchange-rates/{currency_code}", currencyCode))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testAddNewCurrency() throws Exception {
        String currencyCode = "EUR";

        mockMvc.perform(post("/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(currencyCode))
                .andExpect(status().isOk());

    }
}