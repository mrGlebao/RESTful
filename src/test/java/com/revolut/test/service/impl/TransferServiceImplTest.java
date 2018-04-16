package com.revolut.test.service.impl;

import com.revolut.test.dao.TransferDAO;
import com.revolut.test.entities.Result;
import com.revolut.test.entities.Transfer;
import com.revolut.test.service.TransferService;
import com.revolut.test.service.impl.TransferServiceImpl;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TransferServiceImplTest {

    private TransferDAO dao = mock(TransferDAO.class);

    private Transfer expected = Transfer.builder()
            .withIdFrom(new Random().nextInt())
            .withIdTo(new Random().nextInt())
            .withAmount(new Random().nextInt())
            .build();

    private TransferService service = new TransferServiceImpl(dao);

    @Test
    public void testTransferServiceImpl_transferSuccess() {
        Result<Transfer> actual = service.transfer(expected);
        verify(dao, times(1)).transfer(expected);
        assertTrue(actual.isSuccess());
        assertEquals("Unexpected successful transfer result", expected, actual.getData());
    }

    @Test
    public void testTransferServiceImpl_transferFails() {
        doThrow(new RuntimeException("abc")).when(dao).transfer(expected);
        Result<Transfer> actual = service.transfer(expected);
        verify(dao, times(1)).transfer(expected);
        assertFalse(actual.isSuccess());
        assertEquals("Unexpected error transfer result", "abc", actual.getEvent().getMessage());
    }


}
