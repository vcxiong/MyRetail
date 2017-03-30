package com.myretail.external;

import org.junit.Assert;
import org.junit.Test;

/**
 * ExternalServiceClient unit tests
 */
public class ExterenalServiceClientTest {

    @Test
    public void testGetProductName_ValidID_13860428() {
        String name = new ExternalServiceClient().getProductName(13860428);
        Assert.assertEquals("The Big Lebowski (Blu-ray)", name);
    }

    @Test
    public void testGetProductName_ValidID_16696652() {
        String name = new ExternalServiceClient().getProductName(16696652);
        Assert.assertEquals("Beats Solo 2 Wireless - Black (MHNG2AM/A)", name);
    }

    @Test
    public void testGetProductName_InvalidID() {
        String name = new ExternalServiceClient().getProductName(0);
        Assert.assertNull(name);
    }
}
