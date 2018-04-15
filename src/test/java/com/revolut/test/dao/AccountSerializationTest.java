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
    public void testAccountDTOSerialization_roundtrip() throws Exception {
        String expected = MAPPER.writeValueAsString(AccountDTO.of(3, 12));
        String actual = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/account.json"), AccountDTO.class));
        assertEquals("unexpected serialization result", expected, actual);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        AccountDTO expected = AccountDTO.of(3, 12);
        AccountDTO actual = MAPPER.readValue(fixture("fixtures/account.json"), AccountDTO.class);
        assertEquals("unexpected deserialization result", expected, actual);
    }
}
