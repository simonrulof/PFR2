package modele.entite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Historique {
	private Map<String, Recherche> historiqueRecherches = new HashMap<String, Recherche>();

	private static class HistoriqueHolder{
        private final static Historique instance = new Historique();
    }

    public static Historique getInstance(){
        return HistoriqueHolder.instance;
    }

	public void viderHistorique() {
		historiqueRecherches.clear();
	}
	
	public void ajoutRecherche(Recherche r) {
		String d = tempsToString(new Date(System.currentTimeMillis()));
		historiqueRecherches.put(d, r);
	}

	public void supprimerRecherche(Recherche r) {
		
		historiqueRecherches.remove(r);
	}

	public Map<String, Recherche> getHistoriqueRecherches() {
		return historiqueRecherches;
	}

	public void setHistoriqueRecherches(Map<String, Recherche> historiqueRecherches) {
		this.historiqueRecherches = historiqueRecherches;
	}

	private String tempsToString(Date d){
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		return formatter.format(d);
	}

	
}