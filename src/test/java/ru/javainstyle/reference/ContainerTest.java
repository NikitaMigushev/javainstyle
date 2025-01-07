package ru.javainstyle.reference;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ContainerTest {
    @Test
    void testAddWaterToSingleContainer() {
        Container container = new Container();
        container.addWater(10);

        assertThat(container.getAmount()).isEqualTo(10.0);
    }

    @Test
    void testConnectTwoContainers() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.addWater(10);
        c1.connectTo(c2);

        assertThat(c1.getAmount()).isEqualTo(5.0);
        assertThat(c2.getAmount()).isEqualTo(5.0);
    }

    @Test
    void testAddWaterToConnectedContainers() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.connectTo(c2);
        c1.addWater(10);

        assertThat(c1.getAmount()).isEqualTo(5.0);
        assertThat(c2.getAmount()).isEqualTo(5.0);
    }

    @Test
    void testConnectMultipleContainers() {
        Container c1 = new Container();
        Container c2 = new Container();
        Container c3 = new Container();

        c1.addWater(10);
        c1.connectTo(c2);
        c3.addWater(20);
        c1.connectTo(c3); // Connect all three containers

        assertThat(c1.getAmount()).isCloseTo(10.0, within(0.01));
        assertThat(c2.getAmount()).isCloseTo(10.0, within(0.01));
        assertThat(c3.getAmount()).isCloseTo(10.0, within(0.01));
    }

    @Test
    void testReconnectContainers() {
        Container c1 = new Container();
        Container c2 = new Container();
        Container c3 = new Container();

        c1.addWater(10);
        c1.connectTo(c2);
        c2.connectTo(c3); // Reconnect c2 with c3

        assertThat(c1.getAmount()).isEqualTo(c2.getAmount());
        assertThat(c2.getAmount()).isEqualTo(c3.getAmount());
        assertThat(c1.getAmount()).isCloseTo(10.0 / 3, within(0.01));
    }

    @Test
    void testNoDuplicateGroupWaterAddition() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.connectTo(c2);
        c1.addWater(10);

        assertThat(c1.getAmount()).isEqualTo(5.0);
        assertThat(c2.getAmount()).isEqualTo(5.0);

        c2.addWater(10);

        assertThat(c1.getAmount()).isEqualTo(10.0);
        assertThat(c2.getAmount()).isEqualTo(10.0);
    }
}