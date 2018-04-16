package com.revolut.test.resources;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class TestUtils {
//((InboundJaxrsResponse) response).context.getRequestContext().getEntity()
//
//    public static <T> Optional<T> parseEntityFromResponse(Response r, Class<T> resultClass) {
//        Optional<Field> optField = Arrays.stream(r.getClass().getDeclaredFields())
//                .filter(field -> "context".equals(field.getName()))
//                .findAny();
//        if (optField.isPresent()) {
//            try {
//                Field f = optField.get();
//                f.setAccessible(true);
//                ClientResponse resp = (ClientResponse) f.get(r);
//                return Optional.ofNullable(resp)
//                        .map(ClientResponse::getRequestContext)
//                        .map(OutboundMessageContext::getEntity)
//                        .map(resultClass::cast);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//                return Optional.empty();
//            }
//        }
//        return Optional.empty();
//    }

}
