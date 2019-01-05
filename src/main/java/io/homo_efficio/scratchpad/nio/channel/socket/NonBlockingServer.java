package io.homo_efficio.scratchpad.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

/**
 * @author homo.efficio@gmail.com
 * Created on 2019-01-01.
 */
public class NonBlockingServer {

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
                .bind(new InetSocketAddress("localhost", 5555));
        serverSocketChannel.configureBlocking(false);
        addShutdownHook(serverSocketChannel);

        final String mainThreadName = Thread.currentThread().getName();
        System.out.println("# [" + mainThreadName + "] 서버 대기 중.." + serverSocketChannel.getLocalAddress().toString());

        final ExecutorService executorService = ServerThreadPool.getExecutorService(10);

        while (true) {
            final SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                executorService.submit(new PrintHandler(socketChannel));
            }
        }
    }

    private static void addShutdownHook(ServerSocketChannel serverSocketChannel) {
        Runtime.getRuntime().addShutdownHook(new Thread(new ServerShutdownHook(serverSocketChannel)));
    }
}
