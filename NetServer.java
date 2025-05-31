package mod03;

import javafx.scene.control.TextArea;

/**
 * The NetServer interface defines the methods required for a network server.
 * It includes methods to start and stop the server, as well as to set the log output.
 * 
 * This interface can be implemented by the TCP and UDP servers.
 * 
 * @author Angel
 */
public interface NetServer {

    /**
     * Starts the server, enabling it to begin handling network requests.
     */
    public void start();

    /**
     * Stops the server, terminating any active network connections and cleaning up resources.
     */
    public void end();

    /**
     * Sets the TextArea where the server's log messages will be output.
     * 
     * @param output the TextArea that will display server log messages
     */
    public void setLog(TextArea output);
}
