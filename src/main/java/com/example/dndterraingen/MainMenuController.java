package com.example.dndterraingen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane; // Ensure this import is present
import javafx.scene.image.ImageView; // Added this import

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
    private StackPane mapContainer; // Change this line to StackPane

    @FXML
    private ImageView imageView; // Added this declaration

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
    // Размер клетки в пикселях
    int cellSize = 20;

    // Создаем изображение нужного размера
    WritableImage image = new WritableImage(terrain.sizex * cellSize, terrain.sizey * cellSize);
    PixelWriter pixelWriter = image.getPixelWriter();

    // Проходим по каждой клетке и закрашиваем пиксели на основе значения
    for (int i = 0; i < terrain.sizey; i++) {
        for (int j = 0; j < terrain.sizex; j++) {
            int value = terrain.terrainArray.get(i).get(j);
            Color color = getColorForValue(value);

            // Закрашиваем клетку размером cellSize x cellSize
            for (int y = 0; y < cellSize; y++) {
                for (int x = 0; x < cellSize; x++) {
                    pixelWriter.setColor(j * cellSize + x, i * cellSize + y, color);
                }
            }
        }
    }

    // Устанавливаем изображение в ImageView
    imageView.setImage(image);
}

    private void scaleMapToFitWindow() {
        imageView.setFitWidth(mapContainer.getWidth());
        imageView.setFitHeight(mapContainer.getHeight());
        imageView.setPreserveRatio(true); 
        mapContainer.widthProperty().addListener((obs, oldVal, newVal) -> scaleMapToFitWindow());
        mapContainer.heightProperty().addListener((obs, oldVal, newVal) -> scaleMapToFitWindow());
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

    // Add listeners to mapContainer's width and height properties
}