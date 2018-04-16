package com.revolut.test.resources.simple;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ping")
@Produces(MediaType.TEXT_PLAIN)
public class PingPongResource {

    @GET
    @Timed
    public String pong() {
        return "pong";
    }

}
