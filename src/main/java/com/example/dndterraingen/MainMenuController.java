package com.example.dndterraingen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.dndterraingen.Terrain;
public class MainMenuController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onGenerateButtonClick() {

        Terrain newMap = new Terrain(4,5,0);
        newMap.Generator();
        newMap.Output();

        welcomeText.setText("Look at console!");
    }
}