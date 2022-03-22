package modele.entite;

import java.util.ArrayList;
import java.util.List;

import modele.donnee.TypeFichier;

public class Recherche {
	private String requete;
	private TypeFichier typeFichier;
	private Fichier fichier;
	private List<String> resultatsRequete = new ArrayList<>();

	public Recherche(Fichier f, String requete) {
		this.typeFichier = f.getType();
		this.requete = requete;
	}

	public String getRequete() {
		return requete;
	}
	
	public String ResultatsToString() {
		return resultatsRequete.toString();
		
	}
	
	public TypeFichier getTypeFichier() {
		return typeFichier;
	}

	public Fichier getFichier(){
		return this.fichier;
	}

	public void setListResultatsRequete(List<String> resultatsRequete){
		this.resultatsRequete=resultatsRequete;
	}

}
