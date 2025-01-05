package ru.javainstyle.memory3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContainerPerformanceTest {
    @Test
    void testContainerPerformance() {
        int numberOfContainers = 20_000;

        // Измерение времени создания контейнеров
        long startTime = System.nanoTime();
        for (int i = 0; i < numberOfContainers; i++) {
            Container.newContainer();
        }
        long creationEndTime = System.nanoTime();

        // Измерение времени добавления воды
        long addWaterStartTime = System.nanoTime();
        for (int i = 0; i < numberOfContainers; i++) {
            Container.addWater(i, 1.0f); // Добавляем 1 единицу воды
        }
        long addWaterEndTime = System.nanoTime();

        // Измерение времени соединения контейнеров
        long connectStartTime = System.nanoTime();
        for (int i = 0; i < numberOfContainers - 1; i++) {
            Container.connect(i, i + 1);
        }
        long connectEndTime = System.nanoTime();

        // Расчёты
        long creationTime = creationEndTime - startTime;
        long addWaterTime = addWaterEndTime - addWaterStartTime;
        long connectTime = connectEndTime - connectStartTime;

        double avgCreationTime = (double) creationTime / numberOfContainers;
        double avgAddWaterTime = (double) addWaterTime / numberOfContainers;
        double avgConnectTime = (double) connectTime / (numberOfContainers - 1);

        // Установим ожидания для времени операций (в наносекундах)
        long maxCreationTimePerContainer = 500_000; // 500 µs
        long maxAddWaterTimePerContainer = 500_000; // 500 µs
        long maxConnectTimePerConnection = 1_000_000; // 1 ms

        // Assertions
        assertThat(avgCreationTime)
                .as("Average creation time per container")
                .isLessThanOrEqualTo(maxCreationTimePerContainer);

        assertThat(avgAddWaterTime)
                .as("Average addWater time per container")
                .isLessThanOrEqualTo(maxAddWaterTimePerContainer);

        assertThat(avgConnectTime)
                .as("Average connect time per connection")
                .isLessThanOrEqualTo(maxConnectTimePerConnection);

        // Вывод результатов в консоль (для анализа)
        System.out.println("===== Performance Test Results =====");
        System.out.printf("Total creation time: %.2f ms\n", creationTime / 1_000_000.0);
        System.out.printf("Average creation time per container: %.2f ns\n", avgCreationTime);
        System.out.printf("Total addWater time: %.2f ms\n", addWaterTime / 1_000_000.0);
        System.out.printf("Average addWater time per container: %.2f ns\n", avgAddWaterTime);
        System.out.printf("Total connect time: %.2f ms\n", connectTime / 1_000_000.0);
        System.out.printf("Average connect time per connection: %.2f ns\n", avgConnectTime);
    }
}