package ru.javainstyle.speed3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @Test
    void testGetAmountWithoutConnections() {
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
    void testConnectTwoContainers() {
        Container container1 = new Container();
        Container container2 = new Container();

        container1.addWater(10.0);
        container1.connectTo(container2);

        assertThat(container1.getAmount()).isEqualTo(5.0);
        assertThat(container2.getAmount()).isEqualTo(5.0);
    }

    @Test
    void testConnectMultipleContainers() {
        Container container1 = new Container();
        Container container2 = new Container();
        Container container3 = new Container();

        container1.addWater(15.0);
        container2.addWater(5.0);
        container1.connectTo(container2);
        container1.connectTo(container3);

        assertThat(container1.getAmount()).isEqualTo(6.666666666666667);
        assertThat(container2.getAmount()).isEqualTo(6.666666666666667);
        assertThat(container3.getAmount()).isEqualTo(6.666666666666667);
    }

    @Test
    void testAddWaterToConnectedContainers() {
        Container container1 = new Container();
        Container container2 = new Container();

        container1.connectTo(container2);
        container1.addWater(10.0);

        assertThat(container1.getAmount()).isEqualTo(5.0);
        assertThat(container2.getAmount()).isEqualTo(5.0);

        container2.addWater(10.0);
        assertThat(container1.getAmount()).isEqualTo(10.0);
        assertThat(container2.getAmount()).isEqualTo(10.0);
    }

    @Test
    void testConnectingAlreadyConnectedContainers() {
        Container container1 = new Container();
        Container container2 = new Container();

        container1.connectTo(container2);
        container1.connectTo(container2);

        assertThat(container1.getAmount()).isEqualTo(0.0);
        assertThat(container2.getAmount()).isEqualTo(0.0);

        container1.addWater(10.0);
        assertThat(container1.getAmount()).isEqualTo(5.0);
        assertThat(container2.getAmount()).isEqualTo(5.0);
    }

    @Test
    void testFindRootAndCompress() {
        Container container1 = new Container();
        Container container2 = new Container();
        Container container3 = new Container();

        container1.connectTo(container2);
        container2.connectTo(container3);

        assertThat(container1.getAmount()).isEqualTo(0.0);
        assertThat(container2.getAmount()).isEqualTo(0.0);
        assertThat(container3.getAmount()).isEqualTo(0.0);

        container1.addWater(15.0);
        assertThat(container3.getAmount()).isEqualTo(5.0);
    }

}