package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

class UDPTestClient {

    private PrintWriter out;
    private BufferedReader in;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    private byte[] buf = new byte[256];
    private byte[] sbuf;

    void startConnection(String ip, int port) throws IOException {
        this.port = port;
        socket = new DatagramSocket();
        address = InetAddress.getByName(ip);
    }

    public String receive(String msg) throws IOException {
        sbuf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(sbuf, sbuf.length, address, port);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        return received.trim();
    }

}