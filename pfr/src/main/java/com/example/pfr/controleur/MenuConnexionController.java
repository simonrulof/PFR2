package com.example.pfr.controleur;

import java.io.IOException;

import com.example.pfr.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * Codé par MARZAT Noé
 */

public class MenuConnexionController {

    @FXML
    private TextField password;
    @FXML
    private Label erreurMDP;

    @FXML
    private void switchToAdmin() throws IOException {
        if (password.getText().equals("1234")) {
            App.setRoot("menuPrincipalAdmin");
        }
        else {
            erreurMDP.setVisible(true);
        }
    }
    
    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("menuPrincipal");
    }
}