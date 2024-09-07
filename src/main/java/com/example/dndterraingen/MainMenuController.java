package com.example.dndterraingen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class MainMenuController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField seedField;
            
    @FXML
    private CheckBox newSeedCheckbox;

    @FXML
    private TextField widthField;

    @FXML
    private TextField heightField;

    @FXML
    protected void onGenerateButtonClick() {
        long seed;
        int width, height;
        
        if (newSeedCheckbox.isSelected()) {
            // Generate a new random seed if checkbox is selected
            seed = new java.util.Random().nextLong();
            seedField.setText(String.valueOf(seed));
        } else {
            String seedText = seedField.getText().trim();
            if (seedText.isEmpty()) {
                // Use current time as seed if the field is empty
                seed = System.currentTimeMillis();
                seedField.setText(String.valueOf(seed));
            } else {
                try {
                    // Parse the entered seed
                    seed = Long.parseLong(seedText);
                } catch (NumberFormatException e) {
                    // Use current time as seed if the input is invalid
                    seed = System.currentTimeMillis();
                    seedField.setText(String.valueOf(seed));
                }
            }
        }

        try {
            // Parse width and height from input fields
            width = Integer.parseInt(widthField.getText().trim());
            height = Integer.parseInt(heightField.getText().trim());
        } catch (NumberFormatException e) {
            // Use default values if input is invalid
            width = 10;
            height = 10;
            widthField.setText("10");
            heightField.setText("10");
        }

        // Generate new terrain with specified parameters
        Terrain newMap = new Terrain(width, height, 0, seed);
        newMap.Generator();
        String output = newMap.Output();

        System.out.println("Generated Terrain (Seed: " + seed + ", Size: " + width + "x" + height + "):");
        System.out.println(output);

        welcomeText.setText("Terrain generated! Check console for output. Seed: " + seed + ", Size: " + width + "x" + height);
    }
}