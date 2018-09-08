package com.svichkarev.anatoly.ejb;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class EOrder implements Serializable {

    protected String number;
    protected String description;
    protected double amount;
    protected Currency currency;

    public EOrder() {
    }

    public EOrder(String number, String description, double amount, Currency currency) {
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

    @Override
    public String toString() {
        return "EOrder{" +
                "number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
