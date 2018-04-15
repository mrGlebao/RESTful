package com.revolut.test.dto;

public class TransferDTO {

    private int idFrom;

    private int idTo;

    private int amount;

    public int getIdFrom() {
        return idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public int getAmount() {
        return amount;
    }

    private TransferDTO() {
    }

    private TransferDTO(int idFrom, int idTo, int amount) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.amount = amount;
    }

    public static class Builder {

        private int idFrom;

        private int idTo;

        private int amount;

        private Builder() {
        }

        public Builder withIdFrom(int idFrom) {
            this.idFrom = idFrom;
            return this;
        }

        public Builder withIdTo(int idTo) {
            this.idTo = idTo;
            return this;
        }

        public Builder withAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public TransferDTO build() {
            return new TransferDTO(this.idFrom, this.idTo, this.amount);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TransferDTO) {
            TransferDTO otherDTO = (TransferDTO) o;
            return this.idFrom == otherDTO.idFrom
                    && this.idTo == otherDTO.idTo
                    && this.amount == otherDTO.amount;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * amount + result;
        result = 31 * idFrom + result;
        result = 31 * idTo + result;
        return result;
    }

}
