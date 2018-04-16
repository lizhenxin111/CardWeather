package net.queue;

import net.request.StringRequest;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RequestQueue {
    private ThreadPoolExecutor executor = null;

    public RequestQueue() {
        executor = new ThreadPoolExecutor(10,
                20,
                120,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>());
    }

    public void execute(StringRequest request) {
        executor.execute(request);
    }

    public void shutdown() {
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }
}
