package com.revolut.test.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AccountSerializationTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void testAccount_serialization_roundtrip() throws Exception {
        String expected = MAPPER.writeValueAsString(Account.of(3, 12));
        String actual = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/account.json"), Account.class));
        assertEquals("unexpected serialization result", expected, actual);
    }

    @Test
    public void testAccount_deserializesFromJSON() throws Exception {
        Account expected = Account.of(3, 12);
        Account actual = MAPPER.readValue(fixture("fixtures/account.json"), Account.class);
        assertEquals("unexpected deserialization result", expected, actual);
    }
}
