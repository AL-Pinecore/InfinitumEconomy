package cn.infinitumstudios.infinitumEconomy.utility;

import java.util.function.Function;

public class ThreadExecutor {
    public static void run(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
