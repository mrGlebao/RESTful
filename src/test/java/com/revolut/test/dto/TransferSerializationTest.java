package com.revolut.test.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;

public class TransferSerializationTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private TransferDTO dtoEquivalentToJson = TransferDTO.builder()
            .withIdFrom(1)
            .withIdTo(2)
            .withAmount(100)
            .build();

    @Test
    public void testTransferDTOSerialization_roundtrip() throws Exception {
        String expected = MAPPER.writeValueAsString(dtoEquivalentToJson);
        String actual = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/transfer.json"), TransferDTO.class));
        assertEquals("unexpected serialization result", expected, actual);
    }


    @Test
    public void deserializesFromJSON() throws Exception {
        TransferDTO actual = MAPPER.readValue(fixture("fixtures/transfer.json"), TransferDTO.class);
        assertEquals("unexpected deserialization result", actual, dtoEquivalentToJson);
    }
}
