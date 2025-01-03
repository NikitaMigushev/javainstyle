package ru.javainstyle;

import org.junit.jupiter.api.Test;
import ru.javainstyle.speed1.Container;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

class ContainerTest {
    @Test
    void testInitialAmount() {
        Container container = new Container();
        assertThat(container.getAmount()).isEqualTo(0.0);
    }

    @Test
    void testAddWaterToSingleContainer() {
        Container container = new Container();
        container.addWater(10.0);
        assertThat(container.getAmount()).isEqualTo(10.0);
    }

    @Test
    void testConnectContainers() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.addWater(10.0);
        c1.connectTo(c2);

        assertThat(c1.getAmount()).isEqualTo(5.0);
        assertThat(c2.getAmount()).isEqualTo(5.0);
    }

    @Test
    void testAddWaterAfterConnectingContainers() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.addWater(10.0);
        c1.connectTo(c2);
        c1.addWater(10.0);

        assertThat(c1.getAmount()).isEqualTo(10.0);
        assertThat(c2.getAmount()).isEqualTo(10.0);
    }

    @Test
    void testMultipleConnections() {
        Container c1 = new Container();
        Container c2 = new Container();
        Container c3 = new Container();

        c1.addWater(10.0);
        c1.connectTo(c2);
        c2.connectTo(c3);

        double expectedAmount = 10.0 / 3;
        assertThat(c1.getAmount()).isCloseTo(expectedAmount, within(1e-6));
        assertThat(c2.getAmount()).isCloseTo(expectedAmount, within(1e-6));
        assertThat(c3.getAmount()).isCloseTo(expectedAmount, within(1e-6));
    }

    @Test
    void testConnectingAlreadyConnectedContainers() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.connectTo(c2); // Connect the two containers
        c1.connectTo(c2); // Try connecting again

        c1.addWater(10.0);

        assertThat(c1.getAmount()).isEqualTo(5.0);
        assertThat(c2.getAmount()).isEqualTo(5.0);
    }
}