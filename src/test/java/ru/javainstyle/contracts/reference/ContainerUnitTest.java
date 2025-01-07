package ru.javainstyle.contracts.reference;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ContainerUnitTest {
    private Container a, b;

    @Before
    public void setUp() {
        a = new Container();
        b = new Container();
    }

    @Test
    public void testNewContainer() {
        assertTrue("new container is not empty", a.getAmount() == 0);
    }

    @Test
    public void testAddPositiveToIsolated() {
        a.addWater(1);
        assertTrue("should be 1.0", a.getAmount() == 1);
    }

    @Test
    public void testAddZeroToIsolated() {
        a.addWater(0);
        assertTrue("should be 1.0", a.getAmount() == 0);
    }
}