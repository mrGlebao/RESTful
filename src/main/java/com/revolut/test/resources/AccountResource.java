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
        return Response.ok(dao.getById(user)).build();
    }

    @PUT
    @Path("/add")
    @Timed
    public Response add(final AccountDTO dto) {
        dao.insert(dto);
        return Response.ok(dto).build();
    }

    @POST
    @Path("/{user}/update")
    @Timed
    public Response update(final AccountDTO dto) {
        dao.update(dto);
        return Response.ok(dto).build();
    }

}
