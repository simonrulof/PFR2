package com.example.pfr.controleur;

import java.io.File;
import java.io.IOException;

import com.example.pfr.App;
import com.example.pfr.modele.entite.ResultatHolder;
import com.example.pfr.modele.entite.ResultatRequete;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


/**
 * Codé par MARZAT Noé
 */

public class MenuPrincipalAdminController {

    private ResultatRequete[] resultatRecherche = null;

    //Recherche
        @FXML
        private Button chooseFileButton = new Button();
        @FXML
        private Button searchButton = new Button();
        @FXML
        private TextField searchBar = new TextField();
        @FXML
        private Label errorLabel = new Label();
        @FXML
        private ProgressBar progressBar = new ProgressBar();

    //MenuBar
        //Indexation
            @FXML
            private MenuItem ItemIndexAll = new MenuItem();
            @FXML
            private MenuItem ItemIndexAllAudio = new MenuItem();
            @FXML
            private MenuItem ItemIndexAllImage = new MenuItem();
            @FXML
            private MenuItem ItemIndexAllTexte = new MenuItem();

        //Documents
            @FXML
            private MenuItem ItemAddDocTexte = new MenuItem();
            @FXML
            private MenuItem ItemAddDocAudio = new MenuItem();
            @FXML
            private MenuItem ItemAddDocImage = new MenuItem();
            @FXML
            private MenuItem ItemDelDocTexte = new MenuItem();
            @FXML
            private MenuItem ItemDelDocAudio = new MenuItem();
            @FXML
            private MenuItem ItemDelDocImage = new MenuItem();
            @FXML
            private MenuItem ItemVisuBDTexte = new MenuItem();
            @FXML
            private MenuItem ItemVisuBDAudio = new MenuItem();
            @FXML
            private MenuItem ItemVisuBDImage = new MenuItem();
            @FXML
            private MenuItem ItemOpenDescTexte = new MenuItem();
            @FXML
            private MenuItem ItemOpenDescAudio = new MenuItem();
            @FXML
            private MenuItem ItemOpenDescImage = new MenuItem();
        //Paramétrage
            @FXML
            private MenuItem ItemOpenSettings = new MenuItem();

    //Accordeon
        @FXML
        private CheckBox checkTexte = new CheckBox();
        @FXML
        private CheckBox checkImage = new CheckBox();
        @FXML
        private CheckBox checkAudio = new CheckBox();
        @FXML
        private CheckBox checkMultiMoteur = new CheckBox();

    //Changement de page vers menu Utilisateur
        @FXML
        private void switchToUser() throws IOException {
            App.setRoot("menuPrincipal");
        }

        private void switchToResultat() throws IOException {
            ResultatHolder holder = ResultatHolder.getInstance();
            holder.changeAdmin(true);
            App.setRoot("menuResultat");
        }

    //MenuBar Admin
        //Indexation
            @FXML
            private void indexAll(){
                System.out.println("Placeholder : Pas implémenté");
            }

            @FXML
            private void indexAllAudio(){
                System.out.println("Placeholder : Pas implémenté ");
            }

            @FXML
            private void indexAllImage(){
                System.out.println("Placeholder : Pas implémenté ");
            }

            @FXML
            private void indexAllTexte(){
                System.out.println("Placeholder : Pas implémenté");
            }

        //Documents
            @FXML
            private void addDocTexte(){
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Ajouter un fichier");
                FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                chooser.getExtensionFilters().add(xmlFilter);
                File fichierChoisi = chooser.showOpenDialog(chooseFileButton.getScene().getWindow());
                if (fichierChoisi != null){System.out.println(fichierChoisi.toString());}
                /* MEHDI INTEGRE AJOUT DOC TEXTE */
            }

            @FXML
            private void addDocAudio(){
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Ajouter un fichier");
                FileChooser.ExtensionFilter wavFilter = new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav");
                chooser.getExtensionFilters().add(wavFilter);
                File fichierChoisi = chooser.showOpenDialog(chooseFileButton.getScene().getWindow());
                if (fichierChoisi != null){System.out.println(fichierChoisi.toString());}
                /* MEHDI INTEGRE AJOUT DOC AUDIO */
            }

            @FXML
            private void addDocImage(){
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Ajouter un fichier");
                FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
                chooser.getExtensionFilters().add(jpgFilter);
                FileChooser.ExtensionFilter bmpFilter = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
                chooser.getExtensionFilters().add(bmpFilter);
                File fichierChoisi = chooser.showOpenDialog(chooseFileButton.getScene().getWindow());
                if (fichierChoisi != null){System.out.println(fichierChoisi.toString());}
                /* MEHDI INTEGRE AJOUT DOC IMAGE */
            }

            @FXML
            private void delDocTexte(){
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Supprimer un fichier");
                FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                chooser.getExtensionFilters().add(xmlFilter);
                chooser.setInitialDirectory(new File("/home/pfr/Documents/pfr/C/DocTexte/"));
                File fichierChoisi = chooser.showOpenDialog(chooseFileButton.getScene().getWindow());
                if (fichierChoisi != null){System.out.println(fichierChoisi.toString());}
                /* MEHDI INTEGRE SUPPRESSION DOC TEXTE */
            }

            @FXML
            private void delDocAudio(){
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Supprimer un fichier");
                FileChooser.ExtensionFilter wavFilter = new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav");
                chooser.getExtensionFilters().add(wavFilter);
                chooser.setInitialDirectory(new File("/home/pfr/Documents/pfr/C/DocAudio/"));
                File fichierChoisi = chooser.showOpenDialog(chooseFileButton.getScene().getWindow());
                if (fichierChoisi != null){System.out.println(fichierChoisi.toString());}
                /* MEHDI INTEGRE SUPPRESSION DOC AUDIO */
            }

            @FXML
            private void delDocImage(){
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Supprimer un fichier");
                FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
                chooser.getExtensionFilters().add(jpgFilter);
                FileChooser.ExtensionFilter bmpFilter = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
                chooser.getExtensionFilters().add(bmpFilter);
                chooser.setInitialDirectory(new File("/home/pfr/Documents/pfr/C/DocImage/"));
                File fichierChoisi = chooser.showOpenDialog(chooseFileButton.getScene().getWindow());
                if (fichierChoisi != null){System.out.println(fichierChoisi.toString());}
                /* MEHDI INTEGRE SUPPRESSION DOC IMAGE */
            }

            @FXML
            private void visuBDTexte() {
            /*
            ===============
            CRASH SUR LINUX
            ===============

                try {
                    File dossierBD = new File("/home/pfr/Documents/pfr/C/DocTexte/");
                    Desktop.getDesktop().open(dossierBD);
                } catch (IOException e) {
                    System.out.println("Dossier manquant");
                }

             */
            }

            @FXML
            private void visuBDAudio() {
            /*
            ===============
            CRASH SUR LINUX
            ===============

                try {
                    File dossierBD = new File("/home/pfr/Documents/pfr/C/DocAudio/");
                    Desktop.getDesktop().open(dossierBD);
                } catch (IOException e) {
                    System.out.println("Dossier manquant");
                }

             */
            }

            @FXML
            private void visuBDImage() {
            /*
            ===============
            CRASH SUR LINUX
            ===============

                try {
                    File dossierBD = new File("/home/pfr/Documents/pfr/C/DocImage/");
                    Desktop.getDesktop().open(dossierBD);
                } catch (IOException e) {
                    System.out.println("Dossier manquant");
                }
             */
            }

            @FXML
            private void openDescTexte(){
            /*
            ====================================
            NON SUPPORTE PAR LINUX (windows oui)
            ====================================

                final File DescripteurTexte = new File("/home/pfr/Documents/pfr/C/Descripteurs/descripteurTexte.txt");

                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Ouverture du fichier pas supportée par l'OS");
                }

                Desktop desktop = Desktop.getDesktop();
                if (!desktop.isSupported(Desktop.Action.EDIT)) {
                    System.out.println("Edition du fichier pas supportée par l'OS");
                }

                try {
                    desktop.open(DescripteurTexte);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }

            @FXML
            private void openDescAudio(){
            /*
            ====================================
            NON SUPPORTE PAR LINUX (windows oui)
            ====================================

                final File DescripteurAudio = new File("/home/pfr/Documents/pfr/C/Descripteurs/descripteurAudio.txt");

                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Ouverture du fichier pas supportée par l'OS");
                }

                Desktop desktop = Desktop.getDesktop();
                if (!desktop.isSupported(Desktop.Action.EDIT)) {
                    System.out.println("Edition du fichier pas supportée par l'OS");
                }

                try {
                    desktop.edit(DescripteurAudio);
                } catch (IOException e) {
                    e.printStackTrace();
                }
             */
            }

            @FXML
            private void openDescImage(){
            /*
            ====================================
            NON SUPPORTE PAR LINUX (windows oui)
            ====================================

                final File DescripteurImage = new File("home/pfr/Documents/pfr/C/Descripteurs/descripteurImage.txt");

                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Ouverture du fichier pas supportée par l'OS");
                }

                Desktop desktop = Desktop.getDesktop();
                if (!desktop.isSupported(Desktop.Action.EDIT)) {
                    System.out.println("Edition du fichier pas supportée par l'OS");
                }

                try {
                    desktop.edit(DescripteurImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

             */
            }

        //Parametrage
            @FXML
            private void openSettings(){
            /*
            ====================================
            NON SUPPORTE PAR LINUX (windows oui)
            ====================================

                final File fichierConfig = new File("home/pfr/Documents/pfr/src/main/resources/com/example/pfr/config.txt");

                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Ouverture du fichier pas supportée par l'OS");
                }

                Desktop desktop = Desktop.getDesktop();
                if (!desktop.isSupported(Desktop.Action.EDIT)) {
                    System.out.println("Edition du fichier pas supportée par l'OS");
                }

                try {
                    desktop.edit(fichierConfig);
                } catch (IOException e) {
                    e.printStackTrace();
                }

             */
            }


    //Selection d'un fichier avec explorateur
        @FXML
        private void chooseFile() {
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            chooser.getExtensionFilters().add(jpgFilter);
            FileChooser.ExtensionFilter bmpFilter = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
            chooser.getExtensionFilters().add(bmpFilter);
            FileChooser.ExtensionFilter wavFilter = new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav");
            chooser.getExtensionFilters().add(wavFilter);
            FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            chooser.getExtensionFilters().add(xmlFilter);
            chooser.setTitle("Choix du fichier");
            File fichierChoisi = chooser.showOpenDialog(chooseFileButton.getScene().getWindow());
            if (fichierChoisi != null){searchBar.setText(fichierChoisi.toString());}
        }

    //Recherche
        @FXML
        private boolean search(){
            //Gestion des erreurs d'entrée utilisateur
            if (searchBar.getText() == null || searchBar.getText().trim().isEmpty()){
                errorLabel.setText("Veuillez préciser une requête ou choisir un fichier à comparer avant de cliquer sur Rechercher");
                return false;
            }
            if (!checkTexte.isSelected() & !checkImage.isSelected() & !checkAudio.isSelected()){
                errorLabel.setText("Veuillez choisir au moins 1 moteur");
                return false;
            }

            progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            errorLabel.setText("Lancer Recherche");
            if (checkMultiMoteur.isSelected()) { System.out.println("Lancer Multi Moteur"); }
            if (checkTexte.isSelected()) { System.out.println("Lancer Recherche Texte"); }
            if (checkImage.isSelected()) { System.out.println("Lancer Recherche Image"); }
            if (checkAudio.isSelected()) { System.out.println("Lancer Recherche Audio"); }

            try {
                envoyerResultat(resultatRecherche, searchBar.getText());
                switchToResultat();
            } catch (IOException e) {
                progressBar.setVisible(false);
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