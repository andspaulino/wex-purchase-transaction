CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(17, 2) NOT NULL,
    description VARCHAR(50),
    transaction_date DATE NOT NULL
);