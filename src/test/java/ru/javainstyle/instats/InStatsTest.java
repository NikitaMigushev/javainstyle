package ru.javainstyle.instats;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InStatsTest {
    @Test
    void testEmptyStats() {
        InStats stats = new InStats();

        // Проверяем начальные значения
        assertThat(stats.getAverage()).isEqualTo(0.0);
        assertThat(stats.getMedian()).isEqualTo(0.0);
    }

    @Test
    void testSingleElement() {
        InStats stats = new InStats();

        stats.insert(10);

        // Проверяем для одного элемента
        assertThat(stats.getAverage()).isEqualTo(10.0);
        assertThat(stats.getMedian()).isEqualTo(10.0);
    }

    @Test
    void testOddNumberOfElements() {
        InStats stats = new InStats();

        stats.insert(10);
        stats.insert(20);
        stats.insert(30);

        // Проверяем среднее арифметическое
        assertThat(stats.getAverage()).isEqualTo(20.0);

        // Проверяем медиану (серединное значение)
        assertThat(stats.getMedian()).isEqualTo(20.0);
    }

    @Test
    void testEvenNumberOfElements() {
        InStats stats = new InStats();

        stats.insert(10);
        stats.insert(20);
        stats.insert(30);
        stats.insert(40);

        // Проверяем среднее арифметическое
        assertThat(stats.getAverage()).isEqualTo(25.0);

        // Проверяем медиану (среднее двух центральных значений)
        assertThat(stats.getMedian()).isEqualTo(25.0);
    }

    @Test
    void testUnorderedInput() {
        InStats stats = new InStats();

        stats.insert(30);
        stats.insert(10);
        stats.insert(20);

        // Проверяем среднее арифметическое
        assertThat(stats.getAverage()).isEqualTo(20.0);

        // Проверяем медиану (серединное значение)
        assertThat(stats.getMedian()).isEqualTo(20.0);
    }

    @Test
    void testNegativeNumbers() {
        InStats stats = new InStats();

        stats.insert(-10);
        stats.insert(-20);
        stats.insert(-30);

        // Проверяем среднее арифметическое
        assertThat(stats.getAverage()).isEqualTo(-20.0);

        // Проверяем медиану (серединное значение)
        assertThat(stats.getMedian()).isEqualTo(-20.0);
    }

    @Test
    void testMixedPositiveAndNegativeNumbers() {
        InStats stats = new InStats();

        stats.insert(-10);
        stats.insert(10);
        stats.insert(20);
        stats.insert(-20);

        // Проверяем среднее арифметическое
        assertThat(stats.getAverage()).isEqualTo(0.0);

        // Проверяем медиану (среднее двух центральных значений)
        assertThat(stats.getMedian()).isEqualTo(0.0);
    }
}