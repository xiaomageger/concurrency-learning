package org.xiaomage.chapterOne;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafeSequence implements Runnable{

    private int count = 1000;

    public synchronized int getCount(){
        return count--;
    }

    @Override
    public void run() {
        this.getCount();
    }

    @Test
    public void testSafeSequence() throws InterruptedException {

        SafeSequence safeSequence = new SafeSequence();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        int calculateTimes = 888;
        for (int i = 0; i < calculateTimes; i++) {
            executorService.submit(safeSequence);
        }
        Thread.sleep(3000);
        executorService.shutdown();
        Assert.assertEquals(safeSequence.getCount(), (count - calculateTimes));
    }
}
