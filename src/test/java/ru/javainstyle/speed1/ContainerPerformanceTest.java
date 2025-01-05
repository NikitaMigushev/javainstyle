package ru.javainstyle.speed1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContainerPerformanceTest {
    @Test
    void testContainerGroupPerformance() {
        int numberOfContainers = 20_000;
        List<Container> containers = new ArrayList<>();

        // Measure time for creating containers
        long startCreationTime = System.nanoTime();
        for (int i = 0; i < numberOfContainers; i++) {
            containers.add(new Container());
        }
        long endCreationTime = System.nanoTime();

        // Measure time for adding water
        long startAddWaterTime = System.nanoTime();
        for (Container container : containers) {
            container.addWater(1.0); // Add 1 unit of water to each container
        }
        long endAddWaterTime = System.nanoTime();

        // Measure time for connecting containers
        long startConnectTime = System.nanoTime();
        for (int i = 1; i < numberOfContainers; i++) {
            containers.get(i - 1).connectTo(containers.get(i));
        }
        long endConnectTime = System.nanoTime();

        // Calculate times
        long creationTime = endCreationTime - startCreationTime;
        long addWaterTime = endAddWaterTime - startAddWaterTime;
        long connectTime = endConnectTime - startConnectTime;

        double avgCreationTime = (double) creationTime / numberOfContainers;
        double avgAddWaterTime = (double) addWaterTime / numberOfContainers;
        double avgConnectTime = (double) connectTime / (numberOfContainers - 1);

        // Set performance thresholds in nanoseconds
        long maxCreationTimePerContainer = 500_000; // 500 µs
        long maxAddWaterTimePerContainer = 500_000; // 500 µs
        long maxConnectTimePerOperation = 1_000_000; // 1 ms

        // Assert performance thresholds
        assertThat(avgCreationTime)
                .as("Average creation time per container")
                .isLessThanOrEqualTo(maxCreationTimePerContainer);

        assertThat(avgAddWaterTime)
                .as("Average addWater time per container")
                .isLessThanOrEqualTo(maxAddWaterTimePerContainer);

        assertThat(avgConnectTime)
                .as("Average connect time per operation")
                .isLessThanOrEqualTo(maxConnectTimePerOperation);

        assertThat(containers).hasSize(numberOfContainers);

        // Output results for debugging
        System.out.println("===== Performance Test Results =====");
        System.out.printf("Total creation time: %.2f ms\n", creationTime / 1_000_000.0);
        System.out.printf("Average creation time per container: %.2f ns\n", avgCreationTime);
        System.out.printf("Total addWater time: %.2f ms\n", addWaterTime / 1_000_000.0);
        System.out.printf("Average addWater time per container: %.2f ns\n", avgAddWaterTime);
        System.out.printf("Total connect time: %.2f ms\n", connectTime / 1_000_000.0);
        System.out.printf("Average connect time per operation: %.2f ns\n", avgConnectTime);
    }
}