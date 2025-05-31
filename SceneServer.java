package mod03;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides a static method to create the JavaFX Scene for the server.
 * It includes buttons to start and stop the server and a text area to display server logs.
 * 
 * The scene layout consists of two VBoxes within a HBox.
 * One VBox contains the control buttons, and the other contains the text area for log output.
 * 
 * @author Angel
 */
public class SceneServer {

    // Static reference to hold the current scene instance
    private static Scene currentScene = null;

    /**
     * Creates and returns a JavaFX Scene for the server control interface.
     * If the scene is already created, it returns the existing scene.
     * 
     * @param server an instance of NetServer that is controlled by the scene
     * @return the created or existing JavaFX Scene
     */
    public static Scene create(NetServer server) {

        // Return the existing scene if it has already been created
        if (SceneServer.currentScene != null)
            return SceneServer.currentScene;

        // Create an HBox layout to hold two VBox elements
        HBox hBox = new HBox(2);
        VBox leftVBox = new VBox(2);
        VBox rightVBox = new VBox(2);
        hBox.getChildren().addAll(leftVBox, rightVBox);

        // Create buttons to start and end the server
        Button start = new Button("Start");
        Button end = new Button("End");

        // Add buttons to the left VBox
        leftVBox.getChildren().addAll(start, end);

        // Create a TextArea for server log output
        TextArea output = new TextArea();

        // Add the TextArea to the right VBox
        rightVBox.getChildren().add(output);

        // Link the server to the output TextArea for logging
        server.setLog(output);

        // Set up event handlers for the start and end buttons
        start.setOnAction(event -> {
            server.start(); // Start the server when the start button is clicked
        });

        end.setOnAction(event -> {
            server.end(); // Stop the server when the end button is clicked
        });

        // Create the scene with the HBox layout and return it
        SceneServer.currentScene = new Scene(hBox);
        return SceneServer.currentScene;
    }

}
