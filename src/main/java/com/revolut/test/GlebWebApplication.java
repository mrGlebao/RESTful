package com.revolut.test;

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
        // TODO: implement application
    }

}
