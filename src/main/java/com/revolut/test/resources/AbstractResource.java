package com.revolut.test.resources;

import com.revolut.test.dto.Result;

import javax.ws.rs.core.Response;

public class AbstractResource {
    public <T> Response wrapIntoResponse(Result<T> result) {
        if (result.isSuccess()) {
            return Response.ok().entity(result.getData()).build();
        }
        return Response.status(500).entity(result.getEvent()).build();
    }
}
