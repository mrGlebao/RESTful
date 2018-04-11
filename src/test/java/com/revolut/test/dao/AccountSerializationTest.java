package com.revolut.test.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.test.dto.AccountDTO;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AccountSerializationTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final AccountDTO account = new AccountDTO(3, 12);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/account.json"), AccountDTO.class));

        assertEquals("unexpected serialization result", MAPPER.writeValueAsString(account), expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final AccountDTO account = new AccountDTO(3, 12);
        assertEquals("unexpected deserialization result", MAPPER.readValue(fixture("fixtures/account.json"), AccountDTO.class), account);
    }
}
