package com.revolut.test.entities;

public class Account {

    private int id;

    private int amount;

    public Account() {
    }

    private Account(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public static Account of(int id, int amount) {
        return new Account(id, amount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Account) {
            Account otherDTO = (Account) o;
            return this.id == otherDTO.id && this.amount == otherDTO.amount;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * amount + result;
        result = 31 * id + result;
        return result;
    }

}
