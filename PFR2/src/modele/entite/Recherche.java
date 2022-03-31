package modele.entite;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.donnee.TypeFichier;
import modele.donnee.TypeRecherche;

public class Recherche {
	private String requete;
	private Fichier fichier;
	private TypeFichier typeFichier;
	private TypeRecherche typeRecherche;
	private Map<Fichier, Double> resultatsRequete = new HashMap<>();

	public Recherche(Fichier f, String requete, TypeRecherche type) {
		this.typeRecherche=type;
		this.fichier = f;
		this.requete = requete;
		this.typeFichier = this.getTypeFichier();
	}

	public String getRequete() {
		return requete;
	}
	
	public String ResultatsToString() {
		return resultatsRequete.toString();		
	}
	
	public TypeFichier getTypeFichier() {
		return this.fichier.getType();
	}

	public Fichier getFichier(){
		return this.fichier;
	}

	public void setResultatsRequete(Map<Fichier, Double> resultatsRequete){
		this.resultatsRequete=resultatsRequete;
	}

}
