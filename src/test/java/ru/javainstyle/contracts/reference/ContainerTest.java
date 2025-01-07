package ru.javainstyle.contracts.reference;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @Test
    void testSingleContainerAddWater() {
        Container container = new Container();
        container.addWater(10);

        assertThat(container.getAmount()).isEqualTo(10.0);
    }

    @Test
    void testConnectTwoContainers() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.addWater(10); // Add 10 units of water to c1
        c1.connectTo(c2); // Connect c1 to c2

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
        c2.addWater(20);

        c1.connectTo(c2); // Connect c1 and c2
        c1.connectTo(c3); // Connect c3 to the group

        double expectedAmount = (10 + 20 + 0) / 3;

        assertThat(c1.getAmount()).isCloseTo(expectedAmount, within(0.01));
        assertThat(c2.getAmount()).isCloseTo(expectedAmount, within(0.01));
        assertThat(c3.getAmount()).isCloseTo(expectedAmount, within(0.01));
    }

    @Test
    void testReconnectContainers() {
        Container c1 = new Container();
        Container c2 = new Container();
        Container c3 = new Container();

        c1.addWater(10);
        c2.addWater(20);

        c1.connectTo(c2);
        c2.connectTo(c3); // Reconnect group to c3

        double expectedAmount = (10 + 20 + 0) / 3;

        assertThat(c1.getAmount()).isCloseTo(expectedAmount, within(0.01));
        assertThat(c2.getAmount()).isCloseTo(expectedAmount, within(0.01));
        assertThat(c3.getAmount()).isCloseTo(expectedAmount, within(0.01));
    }

    @Test
    void testAddNegativeWaterThrowsException() {
        Container c1 = new Container();
        c1.addWater(5);

        assertThatThrownBy(() -> c1.addWater(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough water");
    }

    @Test
    void testInvariantAfterAddWater() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.addWater(10);
        c1.connectTo(c2);
        c1.addWater(10);

        assertThat(c1.getAmount()).isEqualTo(10.0);
        assertThat(c2.getAmount()).isEqualTo(10.0);

        // Group invariant: all containers in the group have the same amount
        assertThat(c1.getAmount()).isEqualTo(c2.getAmount());
    }

    @Test
    void testGroupAmountCorrectness() {
        Container c1 = new Container();
        Container c2 = new Container();
        Container c3 = new Container();

        c1.addWater(15);
        c1.connectTo(c2);
        c2.connectTo(c3);


        c1.addWater(30); // Adding 30 to the group

        double expectedAmount = (15 + 30) / 3;

        assertThat(c1.getAmount()).isCloseTo(expectedAmount, within(0.01));
        assertThat(c2.getAmount()).isCloseTo(expectedAmount, within(0.01));
        assertThat(c3.getAmount()).isCloseTo(expectedAmount, within(0.01));
    }
}