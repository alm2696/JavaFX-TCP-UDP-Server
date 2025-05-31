package mod03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * The TCPServer class implements the NetServer interface, representing a simple TCP server.
 * It listens for incoming client connections, reads and logs messages, and handles server start and stop operations.
 * 
 * The server runs on port 5000 and times out every 300 milliseconds to ensure non-blocking behavior.
 * 
 * @author Angel
 */
public class TCPServer implements NetServer {

    private ServerSocket socket;  // The TCP server socket for communication
    private TextArea output;  // The TextArea where log messages are displayed
    private boolean running = false;  // Indicates whether the server is running

    /**
     * Starts the TCP server by binding to port 5000 and beginning to accept client connections.
     * Logs the server start and any exceptions encountered.
     */
    @Override
    public void start() {

        try {
            // Create a ServerSocket bound to port 5000 with a backlog of 10 connections
            this.socket = new ServerSocket(5000, 10);

            // Set a timeout of 300 milliseconds for accepting connections
            this.socket.setSoTimeout(300);

            // Log the server start
            this.output.appendText("TCP server started\n");

            // Set the running flag to true
            this.running = true;

            // Begin accepting client connections
            this.acceptConnections();

        } catch (IOException e) {
            // Log the exception message and print stack trace
            this.output.appendText("EXCEPTION: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    /**
     * Accepts incoming client connections and logs the client address, port, and message data.
     * Runs continuously while the server is active, handling SocketTimeoutExceptions
     * to ensure non-blocking behavior when no connections are received.
     */
    private void acceptConnections() {

        try {
            // Block until a client connection is accepted or timeout occurs
            Socket clientConnection = this.socket.accept();

            // Log the details of the client connection
            this.output.appendText("Message received\n");
            this.output.appendText("   Address: " + clientConnection.getInetAddress() + "\n");
            this.output.appendText("      Port: " + clientConnection.getPort() + "\n");

            // Create a BufferedReader to read input from the client
            BufferedReader netIn = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));

            // Read the message sent by the client and log it
            while (clientConnection.getInputStream().available() != 0) {
                String msg = netIn.readLine();
                this.output.appendText(msg + "\n\n");
            }

            // Close the client connection after reading the message
            clientConnection.close();

        } catch (SocketTimeoutException e) {
            // Timeout occurs if no connections are received

        } catch (IOException e) {
            // Log exceptions and print stack trace
            this.output.appendText("EXCEPTION: " + e.getMessage() + "\n");
            e.printStackTrace();
        }

        // Continue accepting connections if the server is still running
        if (this.running) {
            // Use Platform.runLater to ensure the next connection check runs on the JavaFX thread
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    TCPServer.this.acceptConnections();
                }
            });
        }
    }

    /**
     * Stops the TCP server and logs the server stop.
     * The server will stop accepting connections and terminate any active processes.
     */
    @Override
    public void end() {
        // Log the server stop
        this.output.appendText("TCP server stopped\n");

        // Set running flag to false to stop the server loop
        this.running = false;
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
