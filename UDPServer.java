package mod03;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * The UDPServer class implements the NetServer interface, representing a simple UDP server.
 * It listens for incoming datagram packets, logs received data, and handles server start and stop operations.
 * 
 * The server runs on port 5000 and times out every 300 milliseconds to check if it's still running.
 * 
 * @author Angel
 */
public class UDPServer implements NetServer {

    private DatagramSocket socket;  // The UDP socket for communication
    private TextArea output;  // The TextArea where log messages are displayed
    private boolean running = false;  // Indicates whether the server is running

    /**
     * Starts the UDP server by binding to port 5000 and beginning packet listening.
     * Logs server start and any exceptions encountered.
     */
    @Override
    public void start() {

        try {
            // Bind the server to port 5000
            this.socket = new DatagramSocket(5000);

            // Set a timeout of 300 milliseconds for packet reception
            this.socket.setSoTimeout(300);

            // Log the server start
            this.output.appendText("UDP server started\n");

            // Set running flag to true
            this.running = true;

            // Begin listening for packets
            this.checkForPackets();

        } catch (SocketException e) {
            // Log the exception message and print stack trace
            this.output.appendText("EXCEPTION: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    /**
     * Listens for incoming UDP packets and logs details about received packets.
     * Runs continuously while the server is active, handling SocketTimeoutExceptions
     * to ensure non-blocking behavior when no packets are received.
     */
    private void checkForPackets() {

        byte[] buffer = new byte[1024];  // Buffer to hold received data

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        try {
            // Block until a packet is received or timeout occurs
            socket.receive(packet);

            // Log the details of the received packet
            this.output.appendText("Packet received\n");
            this.output.appendText("   Address: " + packet.getAddress() + "\n");
            this.output.appendText("      Port: " + packet.getPort() + "\n");
            this.output.appendText("      Data: " + new String(packet.getData()) + "\n");

        } catch (SocketTimeoutException e) {
            // Timeout occurs if no packets are received

        } catch (IOException e) {
            // Log exceptions and print stack trace
            this.output.appendText("EXCEPTION: " + e.getMessage() + "\n");
            e.printStackTrace();
        }

        // Continue checking for packets if the server is still running
        if (this.running) {
            // Use Platform.runLater to ensure the next packet check runs on the JavaFX thread
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    UDPServer.this.checkForPackets();
                }
            });
        } else {
            // Close the socket when the server stops
            this.socket.close();
        }
    }

    /**
     * Stops the UDP server, logs the server stop, and closes the socket.
     */
    @Override
    public void end() {
        // Log the server stop
        this.output.appendText("UDP server stopped\n");

        // Set running flag to false
        this.running = false;

        // Close the socket to free the port
        socket.close();
    }

    /**
     * Sets the TextArea used for logging server messages.
     * 
     * @param output the TextArea to display server logs
     */
    @Override
    public void setLog(TextArea output) {
        this.output = output;
    }
}
