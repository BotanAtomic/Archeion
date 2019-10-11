package org.cosma.process;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProcessManager {

    private static final Executor executor = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable, Runnable onEnd) {
        executor.execute(() -> {
            runnable.run();

            if (onEnd != null)
                onEnd.run();
        });
    }

}
