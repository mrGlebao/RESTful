package com.revolut.test.resources;

import com.revolut.test.dto.Result;

import javax.ws.rs.core.Response;

class AbstractResource {
    <T> Response wrapIntoResponse(Result<T> result) {
        if (result.isSuccess()) {
            return Response.ok().entity(result.getData()).build();
        }
        return Response.serverError().entity(result.getEvent().getMessage()).build();
    }
}
