package modele.bd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import modele.entite.Recherche;

public class BDHistorique {
	private Map<String, Recherche> historiqueRecherches = new HashMap<String, Recherche>();

	private static class BDHistoriqueHolder{
        private final static BDHistorique instance = new BDHistorique();
    }

    public static BDHistorique getInstance(){
        return BDHistoriqueHolder.instance;
    }

	public void viderHistorique() {
		historiqueRecherches.clear();
	}
	
	public void ajoutRecherche(Recherche r) {
		String d = tempsToString(new Date(System.currentTimeMillis()));
		historiqueRecherches.put(d, r);
	}

	public void supprimerRecherche(Recherche r) {
		Map<String, Recherche> historique = new HashMap<String, Recherche>();
		for(String s : historiqueRecherches.keySet()){
			if(!(historiqueRecherches.get(s).getRequete().equals(r.getRequete()))){
				historique.put(s, historiqueRecherches.get(s));
			}
		}
		historiqueRecherches = historique;
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

	public void ajoutRecherche(java.sql.Date d) {
		String date = tempsToString(d);
		historiqueRecherches.remove(date);
	}

	
}