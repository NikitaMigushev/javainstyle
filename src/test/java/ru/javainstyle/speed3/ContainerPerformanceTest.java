package ru.javainstyle.speed3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ContainerPerformanceTest {
    @Test
    void testContainerPerformance() {
        int numberOfContainers = 20_000;
        List<Container> containers = new ArrayList<>();

        // Измерение времени создания контейнеров
        long startTime = System.nanoTime();
        for (int i = 0; i < numberOfContainers; i++) {
            containers.add(new Container());
        }
        long creationEndTime = System.nanoTime();

        // Измерение времени добавления воды
        long addWaterStartTime = System.nanoTime();
        for (Container container : containers) {
            container.addWater(1.0); // Добавляем 1 единицу воды
        }
        long addWaterEndTime = System.nanoTime();

        // Расчёты
        long creationTime = creationEndTime - startTime;
        long addWaterTime = addWaterEndTime - addWaterStartTime;

        double avgCreationTime = (double) creationTime / numberOfContainers;
        double avgAddWaterTime = (double) addWaterTime / numberOfContainers;

        // Установим ожидания для времени операций (в наносекундах)
        long maxCreationTimePerContainer = 500_000; // 500 µs
        long maxAddWaterTimePerContainer = 500_000; // 500 µs

        // Assertions
        assertThat(avgCreationTime)
                .as("Average creation time per container")
                .isLessThanOrEqualTo(maxCreationTimePerContainer);

        assertThat(avgAddWaterTime)
                .as("Average addWater time per container")
                .isLessThanOrEqualTo(maxAddWaterTimePerContainer);

        assertThat(containers).hasSize(numberOfContainers);

        // Вывод результатов в консоль (для анализа)
        System.out.println("===== Performance Test Results =====");
        System.out.printf("Total creation time: %.2f ms\n", creationTime / 1_000_000.0);
        System.out.printf("Average creation time per container: %.2f ns\n", avgCreationTime);
        System.out.printf("Total addWater time: %.2f ms\n", addWaterTime / 1_000_000.0);
        System.out.printf("Average addWater time per container: %.2f ns\n", avgAddWaterTime);
    }
}
