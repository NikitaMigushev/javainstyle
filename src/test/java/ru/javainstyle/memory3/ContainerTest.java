package ru.javainstyle.memory3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @BeforeEach
    void resetState() throws Exception {
        java.lang.reflect.Field groupField = Container.class.getDeclaredField("group");
        java.lang.reflect.Field amountField = Container.class.getDeclaredField("amount");

        groupField.setAccessible(true);
        amountField.setAccessible(true);

        groupField.set(null, new int[0]);
        amountField.set(null, new float[0]);
    }

    @Test
    void testNewContainer() {
        int container1 = Container.newContainer();
        int container2 = Container.newContainer();

        assertThat(container1).isEqualTo(0);
        assertThat(container2).isEqualTo(1);
    }

    @Test
    void testGetAmount() {
        int container1 = Container.newContainer();
        int container2 = Container.newContainer();

        // Initial amount should be 0.0
        assertThat(Container.getAmount(container1)).isEqualTo(0.0f);
        assertThat(Container.getAmount(container2)).isEqualTo(0.0f);
    }

    @Test
    void testAddWater() {
        int container1 = Container.newContainer();
        int container2 = Container.newContainer();

        Container.connect(container1, container2);

        // Add water to the group
        Container.addWater(container1, 10.0f);

        // Verify water is evenly distributed
        assertThat(Container.getAmount(container1)).isEqualTo(5.0f);
        assertThat(Container.getAmount(container2)).isEqualTo(5.0f);
    }

    @Test
    void testConnect() {
        int container1 = Container.newContainer();
        int container2 = Container.newContainer();
        int container3 = Container.newContainer();

        // Add water to container1 and container3
        Container.addWater(container1, 10.0f);
        Container.addWater(container3, 20.0f);

        // Connect container1 and container2
        Container.connect(container1, container2);
        assertThat(Container.getAmount(container1)).isEqualTo(5.0f);
        assertThat(Container.getAmount(container2)).isEqualTo(5.0f);

        // Connect container1 (group1) and container3 (group3)
        Container.connect(container1, container3);
        assertThat(Container.getAmount(container1)).isEqualTo(10.0f);
        assertThat(Container.getAmount(container3)).isEqualTo(10.0f);
    }

    @Test
    void testGroupDefragmentation() {
        int container1 = Container.newContainer();
        int container2 = Container.newContainer();

        // Add water and connect
        Container.addWater(container1, 10.0f);
        Container.connect(container1, container2);

        // Verify that group defragmentation works
        assertThat(Container.getAmount(container1)).isEqualTo(5.0f);
        assertThat(Container.getAmount(container2)).isEqualTo(5.0f);
    }

    @Test
    void testMultipleConnections() {
        int container1 = Container.newContainer();
        int container2 = Container.newContainer();
        int container3 = Container.newContainer();
        int container4 = Container.newContainer();

        // Add water to containers
        Container.addWater(container1, 10.0f);
        Container.addWater(container3, 20.0f);

        // Connect containers
        Container.connect(container1, container2);
        Container.connect(container3, container4);
        Container.connect(container1, container3);

        // Verify final water distribution
        assertThat(Container.getAmount(container1)).isEqualTo(7.5f);
        assertThat(Container.getAmount(container2)).isEqualTo(7.5f);
        assertThat(Container.getAmount(container3)).isEqualTo(7.5f);
        assertThat(Container.getAmount(container4)).isEqualTo(7.5f);
    }
}