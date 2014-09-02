package org.jumbo.commons.executors;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Return on 02/09/2014.
 */
public class ImprovedCachedThreadPool extends ThreadPoolExecutor{
    private int maxInQueue;

    public ImprovedCachedThreadPool(int corePoolSize, int maximumPoolSize, int maxInQueue) {
        super(corePoolSize, maximumPoolSize, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
        this.maxInQueue = maxInQueue;
    }

    public void execute(Runnable task) {
        if(getActiveCount() >= super.getMaximumPoolSize() - 2 && super.getQueue().size() >= maxInQueue) {
            setCorePoolSize(super.getMaximumPoolSize()+1);
            setMaximumPoolSize(super.getMaximumPoolSize()+1);
        }
        super.execute(task);
    }
}
