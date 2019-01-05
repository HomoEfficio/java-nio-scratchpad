package io.homo_efficio.scratchpad.nio.channel.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author homo.efficio@gmail.com
 * Created on 2019-01-05.
 */
public class ServerThreadPool {

    public static ExecutorService getExecutorService(int nThreads) {
        return Executors.newFixedThreadPool(nThreads);
    }
}
