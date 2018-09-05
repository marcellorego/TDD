package br.com.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.javamoney.moneta.Money;

public class Account {

    public static final int ACCOUNT_ID_LENGTH = 6;

    private String id = RandomStringUtils.randomAlphanumeric(ACCOUNT_ID_LENGTH);
    private boolean active = true;
    private Zone zone = Zone.ZONE_1;
    private Money balance = Money.of(0.00, "USD");

    public Account() {
    }

    public Account(boolean active, Zone zone, double balance) {
        this.active = active;
        this.zone = zone;
        if (balance < 0)
            throw new IllegalArgumentException("The balance can not be negative");
        this.balance = Money.of(balance, "USD");
    }

    public enum Zone {
        ZONE_1, ZONE_2, ZONE_3
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public Zone getZone() {
        return zone;
    }

    public Money getBalance() {
        return balance;
    }
}