package modele.entite;

import java.util.HashMap;
import java.util.Map;

import modele.donnee.TypeFichier;
import modele.donnee.TypeRecherche;

public class Recherche {
	private String requete;
	private Fichier fichier;
	private TypeFichier typeFichier;
	private TypeRecherche typeRecherche;
	private HashMap<Fichier, Double> resultatsRequete = new HashMap<>();
	//pour la rechercher avec nombre d'occurrence dans les fichiers audio, car nous avons l'occurrence et les marqueurs temps
	private HashMap<Fichier, String> resultatsRequeteTemps = new HashMap<>();

	public Recherche(Fichier f, String requete, TypeRecherche type) {
		this.typeRecherche=type;
		this.fichier = f;
		this.requete = requete;
		this.typeFichier = this.getTypeFichier();
	}

	public String getRequete() {
		return requete;
	}
	
	public HashMap<Fichier, String> getResultatsTemps() {
		return resultatsRequeteTemps;		
	}

	public HashMap<Fichier, Double> getResultats() {
		return resultatsRequete;		
	}
	
	public TypeFichier getTypeFichier() {
		return this.fichier.getType();
	}

	public Fichier getFichier(){
		return this.fichier;
	}

	public void setResultatsRequete(HashMap<Fichier, Double> resultatsRequete){
		this.resultatsRequete=resultatsRequete;
	}

	public void setResultatsRequeteTemps(HashMap<Fichier, String> resultatsRequete){
		this.resultatsRequeteTemps=resultatsRequete;
	}

    public void setRequete(String r) {
		this.requete= r;
    }



}
