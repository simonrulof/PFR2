package modele.entite;

import java.util.ArrayList;
import java.util.List;

import modele.donnee.TypeFichier;
import modele.donnee.TypeRecherche;

public class Recherche {
	private String requete;
	private TypeFichier typeFichier;
	private Fichier fichier;
	private TypeRecherche typeRecherche;
	private List<String> resultatsRequete = new ArrayList<>();

	public Recherche(Fichier f, String requete, TypeRecherche type) {
		this.typeRecherche=type;
		this.fichier = f;
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

	public void setResultatsRequete(List<String> resultatsRequete){
		this.resultatsRequete=resultatsRequete;
	}

}
