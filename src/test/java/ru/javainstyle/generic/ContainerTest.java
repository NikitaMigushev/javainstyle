package ru.javainstyle.generic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @Test
    public void testContainerInitialization() {
        // Создаем контейнер
        Container container = new Container();

        // Проверяем начальное значение
        assertEquals(0.0, container.get(), 0.0001);
    }

    @Test
    public void testContainerUpdate() {
        Container container = new Container();

        // Обновляем значение контейнера
        container.update(10.0);

        // Проверяем, что значение обновилось
        assertEquals(10.0, container.get(), 0.0001);

        // Обновляем значение еще раз
        container.update(5.0);
        assertEquals(15.0, container.get(), 0.0001);
    }

    @Test
    public void testContainerMerge() {
        Container container1 = new Container();
        Container container2 = new Container();

        // Обновляем значения в контейнерах
        container1.update(10.0);
        container2.update(20.0);

        // Соединяем контейнеры
        container1.connectTo(container2);

        // Проверяем, что значения равномерно распределены
        assertEquals(15.0, container1.get(), 0.0001);
        assertEquals(15.0, container2.get(), 0.0001);

        // Обновляем один из контейнеров
        container1.update(10.0);

        // Проверяем новое значение для обоих контейнеров
        assertEquals(20.0, container1.get(), 0.0001);
        assertEquals(20.0, container2.get(), 0.0001);
    }

    @Test
    public void testMultipleMerges() {
        Container container1 = new Container();
        Container container2 = new Container();
        Container container3 = new Container();

        // Обновляем значения в контейнерах
        container1.update(10.0);
        container2.update(20.0);
        container3.update(30.0);

        // Соединяем контейнеры
        container1.connectTo(container2);
        container2.connectTo(container3);

        // Проверяем среднее значение в группе
        assertEquals(20.0, container1.get(), 0.0001);
        assertEquals(20.0, container2.get(), 0.0001);
        assertEquals(20.0, container3.get(), 0.0001);

        // Обновляем один из контейнеров
        container3.update(30.0);

        // Проверяем новое значение для всех контейнеров
        assertEquals(30.0, container1.get(), 0.0001);
        assertEquals(30.0, container2.get(), 0.0001);
        assertEquals(30.0, container3.get(), 0.0001);

    }
}