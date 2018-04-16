package com.revolut.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.NoSuchElementException;

enum Status {
    SUCCESS, ERROR,
}

public class Result<T> {

    private Status status;

    private T data;

    private Exception event;

    public Result() {
        // Jackson deserialization
    }

    private Result(T data, Status status, Exception event) {
        this.status = status;
        this.data = data;
        this.event = event;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, Status.SUCCESS, null);
    }

    public static <T> Result<T> error(Exception error) {
        return new Result<>(null, Status.ERROR, error);
    }

    @JsonProperty
    public Status getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    @JsonProperty
    public T getData() {
        if (isSuccess()) {
            return data;
        }
        throw new NoSuchElementException("No data present");
    }

    @JsonProperty
    public Exception getEvent() {
        if (!isSuccess()) {
            return event;
        }
        throw new NoSuchElementException("No events occured");
    }
}
