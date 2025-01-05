package ru.javainstyle.instats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InStats {
    private StatsGroup group = new StatsGroup();

    // Внутренний класс для инкапсуляции данных группы
    private static class StatsGroup {
        double sum = 0; // Сумма всех чисел
        List<Integer> numbers = new ArrayList<>(); // Список чисел
    }

    /**
     * Добавляет целое число в список
     *
     * @param n число, которое нужно добавить
     */
    public void insert(int n) {
        group.numbers.add(n);
        group.sum += n;
    }

    /**
     * Возвращает среднее арифметическое всех чисел
     *
     * @return среднее арифметическое
     */
    public double getAverage() {
        if (group.numbers.isEmpty()) {
            return 0; // Если список пустой, возвращаем 0
        }
        return group.sum / group.numbers.size();
    }

    /**
     * Возвращает медиану всех чисел
     *
     * @return медиана
     */
    public double getMedian() {
        if (group.numbers.isEmpty()) {
            return 0; // Если список пустой, возвращаем 0
        }

        // Создаем копию списка для сортировки
        List<Integer> sorted = new ArrayList<>(group.numbers);
        Collections.sort(sorted);

        int size = sorted.size();
        if (size % 2 == 1) {
            // Нечетное количество элементов: возвращаем средний элемент
            return sorted.get(size / 2);
        } else {
            // Четное количество элементов: возвращаем среднее двух центральных
            int mid1 = sorted.get(size / 2 - 1);
            int mid2 = sorted.get(size / 2);
            return (mid1 + mid2) / 2.0;
        }
    }
}
