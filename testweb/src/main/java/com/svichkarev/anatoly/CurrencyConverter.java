package com.svichkarev.anatoly;

import com.svichkarev.anatoly.ejb.Currency;

public interface CurrencyConverter {

    /**
     * Convert amount from source currency to target currency.
     *
     * @param amount Amount in from currency.
     * @param source Source currency
     * @param target Target currency.
     * @return Amount in target currency.
     */
    double convert(double amount, Currency source, Currency target);
}
