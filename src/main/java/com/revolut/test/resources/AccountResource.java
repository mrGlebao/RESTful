package com.revolut.test.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final AccountDAO dao;

    public AccountResource(AccountDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/{user}/view")
    @Timed
    public Response getUser(@PathParam("user") int user) {
        return Response.ok(new AccountDTO(user, dao.findAmountById(user))).build();
    }

    @PUT
    @Path("/{user}/add")
    @Timed
    public Response put(@PathParam("user") int user, @QueryParam("amount") int amount) {
        dao.insert(new AccountDTO(user, amount));
        return Response.ok(new AccountDTO(user, amount)).build();
    }

    @POST
    @Path("/{user}/update")
    @Timed
    public Response update(@PathParam("user") int user, @QueryParam("amount") int amount) {
        dao.update(user, amount);
        return Response.ok(new AccountDTO(user, amount)).build();
    }

}
