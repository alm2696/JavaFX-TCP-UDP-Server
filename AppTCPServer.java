package mod03;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is an entry point for the TCP Server application using JavaFX.
 * It starts a JavaFX application that runs a TCP server.
 * 
 * @author Angel
 */
public class AppTCPServer extends Application {

    /**
     * The main method that launches the JavaFX application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        Application.launch();
    }

    /**
     * The start method is called when the JavaFX application is started.
     * It initializes the TCP server and sets up the main stage for the application.
     * 
     * @param mainStage the primary stage for this application
     */
    @Override
    public void start(Stage mainStage) {
        // Initialize the TCP server
        TCPServer server = new TCPServer();

        // Set the scene using the SceneServer class and pass the server instance
        mainStage.setScene(SceneServer.create(server));

        // Set an event handler to properly close the server when the stage is closed
        mainStage.setOnCloseRequest((event) -> {
            server.end();  // Ensure the server is closed properly
        });

        // Show the main stage
        mainStage.show();
    }
}
