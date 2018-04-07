package com.revolut.test;

import com.revolut.test.health.SimpleHealthCheck;
import com.revolut.test.resources.PingPongResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class GlebWebApplication extends Application<GlebWebConfiguration> {

    public static void main(final String[] args) throws Exception {
        new GlebWebApplication().run(args);
    }

    @Override
    public String getName() {
        return "GlebWeb";
    }

    @Override
    public void initialize(final Bootstrap<GlebWebConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final GlebWebConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new PingPongResource());
        environment.healthChecks().register("health check", new SimpleHealthCheck());
    }

}
