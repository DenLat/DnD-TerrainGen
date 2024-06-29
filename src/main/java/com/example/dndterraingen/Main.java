package com.example.dndterraingen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * This method is the entry point for the JavaFX application. It is called when the application is launched.
     * It creates a button with the text "Say 'Hello World'" and sets up an event handler for when the button is clicked.
     * When the button is clicked, it prints "Hello World!" to the console.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        // Create a new button
        Button btn = new Button();

        // Set the text of the button to "Say 'Hello World'"
        btn.setText("Say 'Hello World'");

        // Set up an event handler for when the button is clicked
        btn.setOnAction(event -> {
            // When the button is clicked, print "Hello World!" to the console
            System.out.println("Hello World!");
        });
    }
}