package com.revolut.test.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.test.dto.TransferDTO;
import com.revolut.test.service.TransferService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource extends AbstractResource {

    private final TransferService service;

    public TransferResource(TransferService service) {
        this.service = service;
    }

    @POST
    @Consumes({"application/json"})
    @Timed
    public Response update(final TransferDTO dto) {
        return wrapIntoResponse(service.transfer(dto));

    }

}
