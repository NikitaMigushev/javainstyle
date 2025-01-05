package ru.javainstyle.memory3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerMemoryTest {
    @Test
    void testMemoryUsage() {
        int numberOfContainers = 20_000;

        // Сборка мусора перед измерением
        System.gc();

        // Начальное измерение памяти
        long initialMemory = getUsedMemory();

        // Создание контейнеров
        for (int i = 0; i < numberOfContainers; i++) {
            Container.newContainer();
        }

        // Измерение памяти после создания контейнеров
        long afterCreationMemory = getUsedMemory();

        // Добавление воды
        for (int i = 0; i < numberOfContainers; i++) {
            Container.addWater(i, 1.0f);
        }

        // Измерение памяти после добавления воды
        long afterAddWaterMemory = getUsedMemory();

        // Соединение контейнеров
        for (int i = 0; i < numberOfContainers - 1; i++) {
            Container.connect(i, i + 1);
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