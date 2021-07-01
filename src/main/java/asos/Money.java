package asos;

import java.util.Objects;

public class Money implements Comparable<Money> {
    static final String NON_NUMERIC_PRICE_PART = "[^\\d^.]";
    static final String NUMERIC_PRICE_PART = "[\\d.]";
    public final static double MONEY_DELTA = 0.001;

    double value;
    String currency;

    public Money(String moneyString) {
        value = Double.parseDouble(moneyString.replaceAll(NON_NUMERIC_PRICE_PART, ""));

        currency = moneyString.replaceAll(NUMERIC_PRICE_PART, "")
                .trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.value, value) == 0 && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public int compareTo(Money money) {
        // if same currency compare by price
        // else compare by currency

        if (currency.equals(money.currency)) {
            return Double.compare(value, money.value);
        } else {
            return currency.compareTo(money.currency);
        }
    }

    @Override
    public String toString() {
        return currency + value;
    }

    public double getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }
}
