package mod03;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is an entry point for the UDP Server application using JavaFX.
 * It starts a JavaFX application that runs a UDP server.
 * 
 * @author Angel
 */
public class AppUDPServer extends Application {

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
     * It initializes the UDP server and sets up the main stage for the application.
     * 
     * @param mainStage the primary stage for this application
     */
    @Override
    public void start(Stage mainStage) {
        // Initialize the UDP server
        UDPServer server = new UDPServer();

        // Set the scene using the SceneServer class and pass the server instance
        mainStage.setScene(SceneServer.create(server));

        // Set an event handler to properly close the server when the stage is closed
        mainStage.setOnCloseRequest((event) -> {
            server.end();  // Makes sure server is closed properly
        });

        // Show the main stage
        mainStage.show();
    }
}
