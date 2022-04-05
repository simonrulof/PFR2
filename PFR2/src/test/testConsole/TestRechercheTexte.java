package test.testConsole;

import controleur.ControlRechercheComplexeTexte;
import controleur.ControlVerificationFichiers;
import modele.entite.Recherche;

public class TestRechercheTexte {
    public static void main(String[] args) {
		// Mise en place de l'environnement
        ControlVerificationFichiers cvf = new ControlVerificationFichiers();
        ControlRechercheComplexeTexte crct = new ControlRechercheComplexeTexte(cvf, true);
        String fichier = "test.txt";
        String requete4 = "- 50 test.txt";
        String requete = "+ Football - mortel";
        String requete2 = "+ Football 4 mortel";
        String requete3 = "- Football - mortel - machine";

		// Verification de la bonne realisation du cas
		System.out.println("VERIFICATION"); 
		Recherche r = crct.rechercher(fichier); 
		Recherche r2 = crct.rechercher(requete); 
		Recherche r5 = crct.rechercher(requete4); 
		Recherche r3 = crct.rechercher(requete2); 
		Recherche r4 = crct.rechercher(requete3); 

		System.out.println("Requete : " + r.getRequete());
        System.out.println("Fichier : " + r.getFichier());
        System.out.println("TypeFichier : " + r.getTypeFichier());
        System.out.println("Resultat basique : " + r.getResultats().toString());
        System.out.println("Resultats : " + r.getResultatsRequeteArguments().toString());
          
		System.out.println("Requete : " + r2.getRequete());
        System.out.println("Fichier : " + r2.getFichier());
        System.out.println("TypeFichier : " + r2.getTypeFichier());
        System.out.println("Resultat basique : " + r2.getResultats().toString());
        System.out.println("Resultats : " + r2.getResultatsRequeteArguments().toString());

        System.out.println("Requete : " + r3.getRequete());
        System.out.println("Fichier : " + r3.getFichier());
        System.out.println("TypeFichier : " + r3.getTypeFichier());
        System.out.println("Resultat basique : " + r3.getResultats().toString());
        System.out.println("Resultats : " + r3.getResultatsRequeteArguments().toString());

        System.out.println("Requete : " + r4.getRequete());
        System.out.println("Fichier : " + r4.getFichier());
        System.out.println("TypeFichier : " + r4.getTypeFichier());
        System.out.println("Resultat basique : " + r4.getResultats().toString());
        System.out.println("Resultats : " + r4.getResultatsRequeteArguments().toString());

        System.out.println("Requete : " + r5.getRequete());
        System.out.println("Fichier : " + r5.getFichier());
        System.out.println("TypeFichier : " + r5.getTypeFichier());
        System.out.println("Resultat basique : " + r5.getResultats().toString());
        System.out.println("Resultats : " + r5.getResultatsRequeteArguments().toString());
	}
}
