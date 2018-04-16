package com.revolut.test.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.test.entities.Account;
import com.revolut.test.service.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource extends AbstractResource {

    private final AccountService service;

    public AccountResource(AccountService service) {
        this.service = service;
    }

    @GET
    @Path("/{user}/view")
    @Timed
    public Response getUser(@PathParam("user") int user) {
        return wrapIntoResponse(service.get(user));
    }

    @PUT
    @Path("/add")
    @Timed
    public Response add(final Account dto) {
        return wrapIntoResponse(service.add(dto));
    }

    @POST
    @Path("/update")
    @Timed
    public Response update(final Account dto) {
        return wrapIntoResponse(service.update(dto));
    }

}
