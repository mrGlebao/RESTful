package com.revolut.test.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.test.db.AccountDAO;
import com.revolut.test.db.TransferDAO;
import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.TransferDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {

    private final TransferDAO dao;

    public TransferResource(TransferDAO dao) {
        this.dao = dao;
    }

    @POST
    @Consumes({"application/json"})
    @Timed
    public Response update(final TransferDTO dto) {
        dao.transfer(dto);
        return Response.ok(dto).build();
    }

}
