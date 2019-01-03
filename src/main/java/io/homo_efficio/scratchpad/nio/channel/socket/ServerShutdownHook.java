package io.homo_efficio.scratchpad.nio.channel.socket;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

/**
 * @author homo.efficio@gmail.com
 * Created on 2019-01-03.
 */
public class ServerShutdownHook implements Runnable {

    private ServerSocketChannel serverSocketChannel;

    public ServerShutdownHook(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        if (serverSocketChannel.isOpen()) {
            try {
                System.out.println("\n\n열려있는 serverSocketChannel 닫음");
                serverSocketChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
