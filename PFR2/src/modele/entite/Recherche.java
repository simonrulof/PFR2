package modele.entite;

import java.util.ArrayList;
import java.util.List;

import modele.donnee.TypeFichier;

public class Recherche {
	private String requete;
	private TypeFichier typeFichier;
	private List<String> resultatsRequete = new ArrayList<>();

	public Recherche(TypeFichier typeFichier) {
		this.typeFichier = typeFichier;
	}

	public String getRequete() {
		return requete;
	}

	public void setTypeFichier(TypeFichier typeFichier) {
		this.typeFichier = typeFichier;
	}
	
	public String ResultatsToString() {
		return resultatsRequete.toString();
		
	}
	
	public TypeFichier getTypeFichier() {
		return typeFichier;
	}

	public void setRequete(String requete){
		this.requete=requete;
	}

	public void setListResultatsRequete(List<String> resultatsRequete){
		this.resultatsRequete=resultatsRequete;
	}

	}
