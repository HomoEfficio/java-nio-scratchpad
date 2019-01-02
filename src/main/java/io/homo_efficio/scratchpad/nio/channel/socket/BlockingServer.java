package io.homo_efficio.scratchpad.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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
            new Thread(() -> {
                String clientInfo = null;
                final String threadName = Thread.currentThread().getName();
                try {
                    clientInfo = socketChannel.getRemoteAddress().toString();
                    System.out.println("## [" + threadName + "] 연결 수락, from " + clientInfo);

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    while (socketChannel.read(byteBuffer) > 0) {
                        final String bufferedMessage = new String(byteBuffer.array());
                        System.out.println("### [" + threadName + "] message from [" + clientInfo + "]: " + bufferedMessage);
                        byteBuffer.clear();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    System.out.println("#### [" + threadName + "] closing SocketChannel for [" + clientInfo + "]");
                    try {
                        socketChannel.close();
                    } catch (IOException e) {

                    }
                }
            }).start();
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
