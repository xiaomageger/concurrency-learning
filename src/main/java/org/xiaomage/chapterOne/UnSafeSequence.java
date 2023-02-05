package org.xiaomage.chapterOne;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnSafeSequence implements Runnable{

    private int count = 1000;

    public int getCount(){
        return count--;
    }

    @Override
    public void run() {
        this.getCount();
    }

    @Test
    public void testSafeSequence() throws InterruptedException {

        UnSafeSequence unSafeSequence = new UnSafeSequence();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        int calculateTimes = 888;
        for (int i = 0; i < calculateTimes; i++) {
            executorService.submit(unSafeSequence);
        }
        Thread.sleep(3000);
        executorService.shutdown();
        Assert.assertEquals(unSafeSequence.getCount(), (count - calculateTimes));
    }
}
