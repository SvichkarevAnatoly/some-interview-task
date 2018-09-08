package com.svichkarev.anatoly;

public class Order {

    private String number;
    private String description;
    private double amount;
    private Currency currency;

    public enum Currency {
        EUR,
        USD
    }

    public Order() {
    }

    public Order(String number, String description, double amount, Currency currency) {
        this.number = number;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
