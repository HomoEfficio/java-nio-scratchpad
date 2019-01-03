package io.homo_efficio.scratchpad.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author homo.efficio@gmail.com
 * Created on 2019-01-01.
 */
public class BlockingServer {

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
                .bind(new InetSocketAddress("localhost", 5555));
        addShutdownHook(serverSocketChannel);

        final String mainThreadName = Thread.currentThread().getName();
        System.out.println("# [" + mainThreadName + "] 서버 대기 중.." + serverSocketChannel.getLocalAddress().toString());

        while (true) {
            final SocketChannel socketChannel = serverSocketChannel.accept();
            new Thread(new EchoHandler(socketChannel)).start();
        }
    }

    private static void addShutdownHook(ServerSocketChannel serverSocketChannel) {
        Runtime.getRuntime().addShutdownHook(new Thread(new ServerShutdownHook(serverSocketChannel)));
    }
}
