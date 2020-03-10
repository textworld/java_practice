package cn.textworld.java.net.onereactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class TCPReactor  implements Runnable{
    private final ServerSocketChannel ssc;
    private Selector selector;

    public TCPReactor(int port) throws IOException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(port);

    }

    @Override
    public void run() {

    }
}
