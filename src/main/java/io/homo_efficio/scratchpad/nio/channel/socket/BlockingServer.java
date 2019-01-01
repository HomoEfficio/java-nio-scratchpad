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

        System.out.println("# 서버 대기 중.." + serverSocketChannel.getLocalAddress().toString());


        while (true) {
            final SocketChannel socketChannel = serverSocketChannel.accept();
            final String clientInfo = socketChannel.getRemoteAddress().toString();
            System.out.println("## 연결 수락, from " + clientInfo);
        }
    }

    private static void addShutdownHook(ServerSocketChannel serverSocketChannel) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (serverSocketChannel.isOpen()) {
                try {
                    System.out.println("\n\n열려있는 serverSocketChannel 닫음");
                    serverSocketChannel.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }
}