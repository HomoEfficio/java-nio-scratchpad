package io.homo_efficio.scratchpad.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

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

        final Scanner scanner = new Scanner(System.in);

        if (connected) {
            System.out.println("$$ 연결 성공, from " + socketChannel.getLocalAddress().toString() +
                    " to " + socketChannel.getRemoteAddress().toString());

            while (true) {
                System.out.print("msg: ");

                final String msg = scanner.nextLine();

                final ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());

                socketChannel.write(byteBuffer);

                byteBuffer.clear();
            }
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
