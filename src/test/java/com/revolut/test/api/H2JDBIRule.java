package com.revolut.test.api;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.rules.ExternalResource;

public class H2JDBIRule extends ExternalResource {

    private Jdbi dbi;
    private Handle handle;

    public Jdbi getJdbi() {
        return dbi;
    }

    public Handle getHandle() {
        return handle;
    }

    @Override
    protected void before() throws Throwable {
        Environment environment = new Environment("test-env",
                Jackson.newObjectMapper(), null, new MetricRegistry(), null);
        dbi = new JdbiFactory().build(environment, getDataSourceFactory(),
                "test");
        handle = dbi.open();
        createDatabase();
    }

    /**
     * This is where you create your databases using handle.createScript() or handle.createStatement() and so on....Remember to
     * wrap them with handle.begin() and handle.commit() so that the change is visible for test code
     */
    public void createDatabase() {
        handle.begin();
        handle.createScript("create table account (id int primary key, amount int)").execute();
        handle.commit();
    }

    @Override
    protected void after() {
        handle.execute("drop table account if exists");
        handle.close();
    }

    private DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.h2.Driver");
        dataSourceFactory.setUrl(String.format(
                "jdbc:h2:mem:gleb",   // in-memory db (*)
                System.currentTimeMillis()));
        dataSourceFactory.setUser("sa");
        return dataSourceFactory;
    }

}