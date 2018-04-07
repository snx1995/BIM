package top.banyaoqiang.www.bim;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager {
    public static final String TAG = "debug";
    public static final String USER_INFO = "user-info";

    private static final ExecutorService asyncThread = Executors.newFixedThreadPool(5);
    private static final Map<String, Object> info = new HashMap<>();

    public static void runOnAsyncThread(Runnable r) {
        asyncThread.execute(r);
    }
}
