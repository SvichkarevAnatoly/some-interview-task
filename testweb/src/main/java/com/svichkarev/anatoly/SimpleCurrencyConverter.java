package com.svichkarev.anatoly;

import com.svichkarev.anatoly.ejb.Currency;

public class SimpleCurrencyConverter implements CurrencyConverter {

    @Override
    public double convert(double amount, Currency source, Currency target) {
        if (source == target) {
            return amount;
        }

        if (source == Currency.EUR && target == Currency.USD) {
            // very simple converter
            return 1.16 * amount;
        }
        throw new IllegalArgumentException("Converter is very simple");
    }
}
