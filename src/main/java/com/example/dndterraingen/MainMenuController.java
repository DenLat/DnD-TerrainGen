package com.example.dndterraingen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;

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
    private GridPane terrainGrid;

    @FXML
    private StackPane mapContainer;

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
        displayTerrain(newMap);

        // Update welcome text with generation details
        welcomeText.setText("Terrain generated! Check under for output.\nSeed: " + seed + ", Size: " + width + "x" + height);
    }

    private void displayTerrain(Terrain terrain) {
        terrainGrid.getChildren().clear(); // Clear previous terrain

        for (int i = 0; i < terrain.sizey; i++) {
            for (int j = 0; j < terrain.sizex; j++) {
                int value = terrain.terrainArray.get(i).get(j);
                Rectangle rect = new Rectangle(20, 20, getColorForValue(value));
                rect.setStroke(Color.BLACK); // Add border to rectangles
                terrainGrid.add(rect, j, i);
            }
        }

        // Scale the map to fit the window
        scaleMapToFitWindow(terrain);
    }

    private void scaleMapToFitWindow(Terrain terrain) {
        double scaleX = mapContainer.getWidth() / (terrain.sizex * 30);
        double scaleY = mapContainer.getHeight() / (terrain.sizey * 30);
        double scale = Math.min(scaleX, scaleY);
        Scale scaleTransform = new Scale(scale, scale);
        terrainGrid.getTransforms().setAll(scaleTransform);

        // Center the map
        terrainGrid.setTranslateX((mapContainer.getWidth() - terrain.sizex * 20 * scale) / 2);
        terrainGrid.setTranslateY((mapContainer.getHeight() - terrain.sizey * 20 * scale) / 2);
    }

    private Color getColorForValue(int value) {
        switch (value) {
            case Terrain.WATER: return Color.BLUE;
            case Terrain.FOREST: return Color.DARKGREEN;
            case Terrain.PLAIN: return Color.LIGHTGREEN;
            case Terrain.MOUNTAIN: return Color.DARKGRAY;
            default: return Color.WHITE;
        }
    }
}