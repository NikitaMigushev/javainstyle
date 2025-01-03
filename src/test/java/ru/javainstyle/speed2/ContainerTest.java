package ru.javainstyle.speed2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @Test
    void singleContainer_addWater_shouldUpdateAmount() {
        Container container = new Container();

        container.addWater(10.0);

        assertThat(container.getAmount()).isEqualTo(10.0);
    }

    @Test
    void twoContainers_connectAndAddWater_shouldDistributeWaterEvenly() {
        Container c1 = new Container();
        Container c2 = new Container();

        c1.addWater(10.0);
        c1.connectTo(c2);

        assertThat(c1.getAmount()).isEqualTo(5.0);
        assertThat(c2.getAmount()).isEqualTo(5.0);
    }

    @Test
    void threeContainers_connectAndAddWater_shouldDistributeWaterEvenly() {
        Container c1 = new Container();
        Container c2 = new Container();
        Container c3 = new Container();

        c1.addWater(10.0);
        c2.addWater(20.0);
        c1.connectTo(c2);
        c2.connectTo(c3);

        assertThat(c1.getAmount()).isEqualTo(10.0);
        assertThat(c2.getAmount()).isEqualTo(10.0);
        assertThat(c3.getAmount()).isEqualTo(10.0);
    }

    @Test
    void containers_connectInDifferentOrder_shouldDistributeWaterCorrectly() {
        Container c1 = new Container();
        Container c2 = new Container();
        Container c3 = new Container();

        c1.addWater(30.0);
        c2.addWater(10.0);
        c1.connectTo(c3);
        c3.connectTo(c2);

        assertThat(c1.getAmount()).isEqualTo(13.333333333333334);
        assertThat(c2.getAmount()).isEqualTo(13.333333333333334);
        assertThat(c3.getAmount()).isEqualTo(13.333333333333334);
    }

    @Test
    void multipleConnections_shouldUpdateWaterDistributionCorrectly() {
        Container c1 = new Container();
        Container c2 = new Container();
        Container c3 = new Container();
        Container c4 = new Container();

        c1.addWater(20.0);
        c2.addWater(10.0);
        c3.addWater(30.0);
        c1.connectTo(c2);
        c2.connectTo(c3);
        c3.connectTo(c4);

        assertThat(c1.getAmount()).isEqualTo(15.0);
        assertThat(c2.getAmount()).isEqualTo(15.0);
        assertThat(c3.getAmount()).isEqualTo(15.0);
        assertThat(c4.getAmount()).isEqualTo(15.0);
    }
}