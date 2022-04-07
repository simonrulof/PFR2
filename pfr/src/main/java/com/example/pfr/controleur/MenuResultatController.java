package com.example.pfr.controleur;

import com.example.pfr.App;
import com.example.pfr.modele.entite.ResultatHolder;
import com.example.pfr.modele.entite.ResultatRequete;
import com.example.pfr.modele.moteur_simulation.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;

/**
 * Codé par MARZAT Noé
 */
public class MenuResultatController {

    private ResultatRequete[] resultat = null;
    private String requete = null;

    @FXML
    private TableView<ResultatRequete> tableResult = new TableView<>();
    @FXML
    private TableColumn<ResultatRequete, File> nameCol = new TableColumn<>();
    @FXML
    private TableColumn<ResultatRequete, Integer> similarityCol = new TableColumn<>();

    @FXML
    private Label requeteLabel = new Label();

    @FXML
    private Button displayResultButton = new Button();

    @FXML
    private void displayResult() {
        requete = recevoirRequete();
        if (requete != null) { requeteLabel.setText(recevoirRequete()); }
        else {requeteLabel.setText("null");}

        resultat = recevoirResultat();
        if (resultat != null) {
            tableResult.getItems().clear();
            nameCol.setCellValueFactory(new PropertyValueFactory<ResultatRequete, File>("nomDoc"));
            similarityCol.setCellValueFactory(new PropertyValueFactory<ResultatRequete, Integer>("similarite"));
            for (int i = 0; i < resultat.length; i++) {
                tableResult.getItems().add(resultat[i]);
            }
        }
    }

    @FXML
    private void saveRequete(){
        System.out.println("Save Requete");
    }

    @FXML
    private void goBack() throws IOException {
        ResultatHolder holder = ResultatHolder.getInstance();
        if (holder.getAdmin()){
            App.setRoot("menuPrincipalAdmin");
        }
        else {
            App.setRoot("menuPrincipal");
        }
    }

    private ResultatRequete[] recevoirResultat() {
        ResultatHolder holder = ResultatHolder.getInstance();
        return holder.getResultatRecherche();
    }

    private String recevoirRequete() {
        ResultatHolder holder = ResultatHolder.getInstance();
        return holder.getRequeteEffectuee();
    }
}

