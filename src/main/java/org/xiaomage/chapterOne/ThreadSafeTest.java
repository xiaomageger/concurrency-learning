package org.xiaomage.chapterOne;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSafeTest {

    static class SafeSequence {

        int count = 1000;

        synchronized int calculateCount() {
            return count--;
        }

        public int getCount() {
            return count;
        }
    }

    static class UnSafeSequence {

        int count = 1000;

        int calculateCount() {
            return count--;
        }

        public int getCount() {
            return count;
        }
    }

    int totalCount = 1000;

    @Test
    public void testSafe() throws InterruptedException {

        SafeSequence safeSequence = new SafeSequence();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        int calculateTimes = 888;
        for (int i = 0; i < calculateTimes; i++) {
            executorService.submit(() -> safeSequence.calculateCount());
        }
        Thread.sleep(3000);
        executorService.shutdown();
        Assert.assertEquals(safeSequence.getCount(), (totalCount - calculateTimes));
    }

    @Test
    public void testUnSafe() throws InterruptedException {

        UnSafeSequence unSafeSequence = new UnSafeSequence();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        int calculateTimes = 666;
        for (int i = 0; i < calculateTimes; i++) {
            executorService.submit(() -> unSafeSequence.calculateCount());
        }
        Thread.sleep(3000);
        executorService.shutdown();
        Assert.assertEquals(unSafeSequence.getCount(), (totalCount - calculateTimes));
    }
}
