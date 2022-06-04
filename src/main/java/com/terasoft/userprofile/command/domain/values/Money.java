package com.terasoft.userprofile.command.domain.values;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;
import java.math.RoundingMode;


@Embeddable
public class Money {
    private long amount;
    private Currency currency;
    private static final int[] cents = new int[] { 1, 10, 100, 1000 };

    private int roundingMode = RoundingMode.HALF_EVEN.ordinal();

    public Money() {
    }

    public Money(double amount, Currency currency) {
        this.currency = currency;
        this.amount = Math.round(amount * centFactor());
    }

    public Money(long amount, Currency currency) {
        this.currency = currency;
        this.amount = Math.round(amount * centFactor());
    }

    public Money(BigDecimal amount, Currency currency, int roundingMode) {
        this.currency = currency;
        long myAmount = amount.longValue();
        this.roundingMode = roundingMode;
        this.amount = Math.round(myAmount * centFactor());
    }

    public static Money dollars(double amount) {
        return new Money(amount, Currency.getInstance("USD"));
    }

    public static Money soles(double amount) {
        return new Money(amount, Currency.getInstance("PEN"));
    }

    public static Money euros(double amount) {
        return new Money(amount, Currency.getInstance("EUR"));
    }

    private int centFactor() {
        return cents[currency.getDefaultFractionDigits()];
    }

    public BigDecimal amount() {
        return BigDecimal.valueOf(amount, currency.getDefaultFractionDigits());
    }

    public Currency currency() {
        return currency;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Money) && equals((Money) other);
    }

    public boolean equals(Money other) {
        return currency.equals(other.currency) && (amount == other.amount);
    }

    @Override
    public int hashCode() {
        return (int) (amount ^ (amount >>> 32));
    }

    public Money add(Money other) {
        validateSameCurrency(other);
        return newMoney(amount + other.amount);
    }

    public Notification validateSameCurrency(Money arg) {
        Notification notification = new Notification();
        if (currency != arg.currency) {
            notification.addError("Money cannot have different currencies");
        }
        return notification;
    }

    private Money newMoney(long amount) {
        Money money = new Money();
        money.currency = this.currency;
        money.amount = amount;
        return money;
    }

    public Money subtract(Money other) {
        Notification notification = validateSameCurrency(other);
        if (!notification.hasErrors()) {
            return newMoney(amount - other.amount);
        }
        return newMoney(amount);
    }

    public int compareTo(Object other) {
        return compareTo((Money) other);
    }

    public int compareTo(Money other) {
        validateSameCurrency(other);
        if (amount < other.amount) {
            return -1;
        } else if (amount == other.amount) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean greaterThan(Money other) {
        return (compareTo(other) > 0);
    }

    public Money multiply(double amount) {
        return multiply(new BigDecimal(amount));
    }

    public Money multiply(BigDecimal amount) {
        return multiply(amount, roundingMode);
    }

    public Money multiply(BigDecimal amount, int roundingMode) {
        return new Money(amount().multiply(amount), currency, roundingMode);
    }

    // these two methods allocate money among multiple targets without losing cents
    public Money[] allocate(int targets) {
        Money lowResult = newMoney(amount / targets);
        Money highResult = newMoney(lowResult.amount + 1);
        Money[] results = new Money[targets];
        int remainder = (int) amount % targets;
        for (int i = 0; i < remainder; i++) {
            results[i] = highResult;
        }
        for (int i = remainder; i < targets; i++) {
            results[i] = lowResult;
        }
        return results;
    }

    // this allocation algorithm can handle any ratio
    public Money[] allocate(long[] ratios) {
        long total = 0;
        for (int i = 0; i < ratios.length; i++) {
            total += ratios[i];
        }
        long remainder = amount;
        Money[] results = new Money[ratios.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = newMoney(amount * ratios[i] / total);
            remainder -= results[i].amount;
        }
        for (int i = 0; i < remainder; i++) {
            results[i].amount++;
        }
        return results;
    }
}