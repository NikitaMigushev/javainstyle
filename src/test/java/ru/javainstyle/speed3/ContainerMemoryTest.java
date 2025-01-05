package ru.javainstyle.speed3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContainerMemoryTest {
    @Test
    void testMemoryUsage() {
        int numberOfContainers = 20_000;
        List<Container> containers = new ArrayList<>();

        // Сборка мусора перед измерением
        System.gc();

        // Начальное измерение памяти
        long initialMemory = getUsedMemory();

        // Создание контейнеров
        for (int i = 0; i < numberOfContainers; i++) {
            containers.add(new Container());
        }

        // Измерение памяти после создания контейнеров
        long afterCreationMemory = getUsedMemory();

        // Добавление воды в контейнеры
        for (Container container : containers) {
            container.addWater(1.0);
        }

        // Измерение памяти после добавления воды
        long afterAddWaterMemory = getUsedMemory();

        // Соединение контейнеров
        for (int i = 0; i < numberOfContainers - 1; i++) {
            containers.get(i).connectTo(containers.get(i + 1));
        }

        // Измерение памяти после соединения контейнеров
        long afterConnectMemory = getUsedMemory();

        // Вывод результатов
        System.out.println("===== Memory Usage Test Results =====");
        System.out.printf("Initial memory: %.2f MB%n", initialMemory / (1024.0 * 1024.0));
        System.out.printf("Memory after creating containers: %.2f MB%n", afterCreationMemory / (1024.0 * 1024.0));
        System.out.printf("Memory after adding water: %.2f MB%n", afterAddWaterMemory / (1024.0 * 1024.0));
        System.out.printf("Memory after connecting containers: %.2f MB%n", afterConnectMemory / (1024.0 * 1024.0));
    }

    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

}