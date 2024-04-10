CREATE TABLE currency_state (
    id SERIAL PRIMARY KEY,
    timestamp BIGINT,
    currency_id VARCHAR(5),
    exchange_rates JSONB,
           CONSTRAINT fk_currency_id
              FOREIGN KEY(currency_id)
              REFERENCES CURRENCY(id)
);

CREATE TABLE CURRENCY (
    id SERIAL PRIMARY KEY,
    code VARCHAR(5),
    )