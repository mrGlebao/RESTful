package com.revolut.test;

import com.revolut.test.health.SimpleHealthCheck;
import com.revolut.test.resources.PingPongResource;
import com.revolut.test.db.AccountDAO;
import com.revolut.test.resources.AccountResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

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

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        AccountDAO dao = jdbi.onDemand(AccountDAO.class);
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        dao.createTransferTable();
        dao.insert(1, 2);
        System.out.println(dao.findAmountById(1));

        jdbi.installPlugin(new H2DatabasePlugin());
        jdbi.installPlugin(new SqlObjectPlugin());
        environment.jersey().register(new AccountResource(dao));

    }

}
