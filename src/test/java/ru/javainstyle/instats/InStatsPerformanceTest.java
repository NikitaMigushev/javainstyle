package ru.javainstyle.instats;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InStatsPerformanceTest {
    @Test
    void testPerformance() {
        int numberOfOperations = 20_000;
        Random random = new Random();
        InStats stats = new InStats();

        // Measure time for `insert`
        long startInsertTime = System.nanoTime();
        for (int i = 0; i < numberOfOperations; i++) {
            stats.insert(random.nextInt(1_000_000)); // Insert random integers
        }
        long endInsertTime = System.nanoTime();
        long totalInsertTime = endInsertTime - startInsertTime;

        // Measure time for `getAverage`
        long startAverageTime = System.nanoTime();
        double average = stats.getAverage();
        long endAverageTime = System.nanoTime();
        long totalAverageTime = endAverageTime - startAverageTime;

        // Measure time for `getMedian`
        long startMedianTime = System.nanoTime();
        double median = stats.getMedian();
        long endMedianTime = System.nanoTime();
        long totalMedianTime = endMedianTime - startMedianTime;

        // Calculate average time per operation
        double avgInsertTime = (double) totalInsertTime / numberOfOperations;
        double avgAverageTime = (double) totalAverageTime;
        double avgMedianTime = (double) totalMedianTime;

        // Assertions to ensure valid results
        assertThat(stats.getAverage()).isEqualTo(average);
        assertThat(stats.getMedian()).isEqualTo(median);

        // Output performance results
        System.out.println("===== Performance Test Results =====");
        System.out.printf("Total insert time: %.2f ms\n", totalInsertTime / 1_000_000.0);
        System.out.printf("Average insert time per operation: %.2f ns\n", avgInsertTime);
        System.out.printf("Total getAverage time: %.2f ms\n", totalAverageTime / 1_000_000.0);
        System.out.printf("Average getAverage time: %.2f ns\n", avgAverageTime);
        System.out.printf("Total getMedian time: %.2f ms\n", totalMedianTime / 1_000_000.0);
        System.out.printf("Average getMedian time: %.2f ns\n", avgMedianTime);
    }
}