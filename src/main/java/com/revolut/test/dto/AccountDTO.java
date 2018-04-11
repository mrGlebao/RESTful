package com.revolut.test.dto;

public class AccountDTO {

    private int id;

    private int amount;

    public AccountDTO(){}

    public AccountDTO(int id, int amount) {
        this.id = id;
        this.amount = amount;
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
        if(o instanceof AccountDTO) {
            AccountDTO otherDTO = (AccountDTO)o;
            return this.id == otherDTO.id && this.amount == otherDTO.amount;
        }
        return false;
    }

    @Override
    public int hashCode(){
        int result = 0;
        result = 31 * amount + result;
        result = 31 * id + result;
        return result;
    }

}
