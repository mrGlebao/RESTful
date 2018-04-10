package com.revolut.test.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.test.db.AccountDAO;
import com.revolut.test.db.TransferDAO;
import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.TransferDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {

    private final TransferDAO dao;

    public TransferResource(TransferDAO dao) {
        this.dao = dao;
    }

    @POST
    @Path("/transfer")
    @Timed
    public Response update(@QueryParam("idFrom") int idFrom, @QueryParam("idTo") int idTo, @QueryParam("amount") int amount) {
        dao.transfer(idFrom, idTo, amount);
        return Response.ok(new TransferDTO(idFrom, idTo, amount)).build();
    }

}
