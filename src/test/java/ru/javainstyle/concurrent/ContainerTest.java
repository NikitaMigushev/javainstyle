package ru.javainstyle.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @Test
    void testSingleContainerAddWater() {
        Container container = new Container();
        container.addWater(100);

        assertThat(container.getAmount()).isEqualTo(100.0);
    }

    @Test
    void testTwoContainersConnectAndDistributeWater() {
        Container container1 = new Container();
        Container container2 = new Container();

        container1.addWater(100);
        container1.connectTo(container2);

        assertThat(container1.getAmount()).isEqualTo(50.0);
        assertThat(container2.getAmount()).isEqualTo(50.0);
    }

    @Test
    void testThreeContainersConnectAndDistributeWater() {
        Container container1 = new Container();
        Container container2 = new Container();
        Container container3 = new Container();

        container1.addWater(90);
        container2.addWater(60);

        container1.connectTo(container2);
        container1.connectTo(container3);

        assertThat(container1.getAmount()).isEqualTo(50.0);
        assertThat(container2.getAmount()).isEqualTo(50.0);
        assertThat(container3.getAmount()).isEqualTo(50.0);
    }

    @Test
    void testConcurrentAddWater() throws InterruptedException {
        Container container = new Container();
        int numberOfThreads = 10;
        double waterPerThread = 10.0;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        IntStream.range(0, numberOfThreads).forEach(i -> executor.submit(() -> {
            container.addWater(waterPerThread);
            latch.countDown();
        }));

        latch.await();
        executor.shutdown();

        assertThat(container.getAmount()).isEqualTo(100.0);
    }

    @Test
    void testConcurrentConnect() throws InterruptedException {
        Container container1 = new Container();
        Container container2 = new Container();
        Container container3 = new Container();

        container1.addWater(100);
        container2.addWater(50);
        container3.addWater(150);

        ExecutorService executor = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);

        executor.submit(() -> {
            container1.connectTo(container2);
            latch.countDown();
        });

        executor.submit(() -> {
            container2.connectTo(container3);
            latch.countDown();
        });

        executor.submit(() -> {
            container1.connectTo(container3);
            latch.countDown();
        });

        latch.await();
        executor.shutdown();

        assertThat(container1.getAmount()).isEqualTo(100.0);
        assertThat(container2.getAmount()).isEqualTo(100.0);
        assertThat(container3.getAmount()).isEqualTo(100.0);
    }
}