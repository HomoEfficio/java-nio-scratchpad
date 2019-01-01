package io.homo_efficio.scratchpad.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author homo.efficio@gmail.com
 * Created on 2019-01-01.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        final SocketChannel socketChannel = SocketChannel.open();
        addShutdownHook(socketChannel);

        System.out.println("$ 연결 시도");
        final boolean connected = socketChannel.connect(new InetSocketAddress("localhost", 5555));

        if (connected) {
            System.out.println("$$ 연결 성공, from " + socketChannel.getLocalAddress().toString() +
                    " to " + socketChannel.getRemoteAddress().toString());
        }

    }

    private static void addShutdownHook(SocketChannel socketChannel) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (socketChannel.isOpen()) {
                try {
                    System.out.println("\n\n열려있는 socketChannel 닫음");
                    socketChannel.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }
}
