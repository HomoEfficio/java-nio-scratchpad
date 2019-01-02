package io.homo_efficio.scratchpad.nio.channel.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author homo.efficio@gmail.com
 * Created on 2019-01-03.
 */
public class EchoHandler implements Runnable {

    private SocketChannel socketChannel;

    public EchoHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        String clientInfo = null;
        final String threadName = Thread.currentThread().getName();
        try {
            clientInfo = socketChannel.getRemoteAddress().toString();
            System.out.println("## [" + threadName + "] 연결 수락, from " + clientInfo);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.read(byteBuffer) > 0) {
                final String bufferedMessage = new String(byteBuffer.array());
                byteBuffer.clear();
                System.out.println("### [" + threadName + "] message from [" + clientInfo + "]: " + bufferedMessage);
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
    }
}
