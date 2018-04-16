package com.revolut.test;

import com.revolut.test.dao.AccountDAO;
import com.revolut.test.dao.TransferDAO;
import com.revolut.test.dao.impl.AccountDAOImpl;
import com.revolut.test.dao.impl.TransferDAOImpl;
import com.revolut.test.resources.AccountResource;
import com.revolut.test.resources.TransferResource;
import com.revolut.test.resources.simple.PingPongResource;
import com.revolut.test.service.AccountService;
import com.revolut.test.service.TransferService;
import com.revolut.test.service.impl.AccountServiceImpl;
import com.revolut.test.service.impl.TransferServiceImpl;
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

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        AccountDAO dao = new AccountDAOImpl(jdbi);
        dao.createTransferTable();
        TransferDAO transferDAO = new TransferDAOImpl(jdbi, dao);

        AccountService accountService = new AccountServiceImpl(dao);
        TransferService transferService = new TransferServiceImpl(transferDAO);

        jdbi.installPlugin(new H2DatabasePlugin());
        jdbi.installPlugin(new SqlObjectPlugin());

        environment.jersey().register(new AccountResource(accountService));
        environment.jersey().register(new TransferResource(transferService));

    }

}
