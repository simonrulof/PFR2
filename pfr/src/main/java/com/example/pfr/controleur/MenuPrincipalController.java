package com.example.pfr.controleur;

import java.io.File;
import java.io.IOException;

import com.example.pfr.App;
import com.example.pfr.modele.*;
import com.example.pfr.modele.entite.ResultatHolder;
import com.example.pfr.modele.entite.ResultatRequete;
import com.example.pfr.modele.entite.Utilisateur;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;


/**
 * Codé par MARZAT Noé
 */

public class MenuPrincipalController {

    /*
    Fichier f1 = new Fichier(new File("C://Users/noema/Desktop/testpfr/TEST1.txt"), 10);
    Fichier f2 = new Fichier(new File("C://Users/noema/Desktop/testpfr/TEST2.txt"), 20);
    Fichier f3 = new Fichier(new File("C://Users/noema/Desktop/testpfr/TEST3.txt"), 90);
    Fichier f4 = new Fichier(new File("C://Users/noema/Desktop/testpfr/TEST4.txt"), 72);

     */
    private ResultatRequete[] resultatRecherche = null;
    private ControlHistorique ch = new ControlHistorique();


    //Recherche
    @FXML
    private Button chooseFileButton = new Button();
    @FXML
    private Button searchButton = new Button();
    @FXML
    private TextField searchBar = new TextField();
    @FXML
    private Label errorLabel = new Label();

    //Accordeon
    @FXML
    private CheckBox checkTexte = new CheckBox();
    @FXML
    private CheckBox checkImage = new CheckBox();
    @FXML
    private CheckBox checkAudio = new CheckBox();
    @FXML
    private CheckBox checkMultiMoteur = new CheckBox();

    
    @FXML
    private void switchToConnexion() throws IOException {
        App.setRoot("menuConnexion");
    }

    private void switchToResultat() throws IOException {
        App.setRoot("menuResultat");
    }

    @FXML
    private void chooseFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choix du fichier");
        File fichierChoisi = chooser.showOpenDialog(chooseFileButton.getScene().getWindow());
        if (fichierChoisi != null){searchBar.setText(fichierChoisi.toString());}    
    }

    @FXML
    private boolean search(){
        //Gestion des erreurs d'entrée utilisateur
        if (searchBar.getText() == null || searchBar.getText().trim().isEmpty()){
            errorLabel.setText("Veuillez préciser une requête ou choisir un fichier à comparer avant de cliquer sur Rechercher");
            return false;
        }
        if (!checkTexte.isSelected() & !checkImage.isSelected() & !checkAudio.isSelected() || (!checkTexte.isSelected() ^ !checkImage.isSelected() ^ !checkAudio.isSelected())){
            errorLabel.setText("Veuillez choisir 1 seul moteur");
            return false;
        }

        Utilisateur utilisateur = Utilisateur.getInstance();
        String requete = searchBar.getText();
        resultatRecherche = utilisateur.comparer(requete, checkMultiMoteur.isSelected(), checkTexte.isSelected(), checkImage.isSelected(), checkAudio.isSelected(), this.ch);
        try {
            envoyerResultat(resultatRecherche, requete);
            switchToResultat();
        } catch (IOException e) {
            errorLabel.setText("Impossible d'ouvrir les resultats de la recherche");
        }
        return true;
    }

    private void envoyerResultat(ResultatRequete[] resultat, String requete) {
        ResultatHolder holder = ResultatHolder.getInstance();
        holder.changeAdmin(false);
        holder.setRequeteEffectuee(requete);
        holder.setResultatRecherche(resultat);
    }

}
