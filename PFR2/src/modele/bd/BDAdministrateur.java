package modele.bd;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import modele.entite.Administrateur;

public class BDAdministrateur {
    private Map<Integer, Administrateur> listeAdministrateur = new HashMap<Integer, Administrateur>();
    private static Integer num_admin;

    private BDAdministrateur(){
        num_admin=0;
    }

    private static class BDAdministrateurHolder{
        private final static BDAdministrateur instance = new BDAdministrateur();
    }

    public void ajouterAdmin(Administrateur a){
        this.listeAdministrateur.put(num_admin,a);
        num_admin++;
    }

    public static BDAdministrateur getInstance(){
        return BDAdministrateurHolder.instance;
    }

    public int connexion(String login, String mdp){
        int numAdmin=-1;
        boolean profilExistant = false;
        for(Entry<Integer, Administrateur> map : this.listeAdministrateur.entrySet()){
            profilExistant = map.getValue().verifierCorrespondaceProfil(login, mdp);
            if(profilExistant){
                map.getValue().connexionProfil();
                numAdmin=map.getKey();
            }
        }
        return numAdmin;
    }

    public Administrateur trouver(int numProfil) {
        return this.listeAdministrateur.get(numProfil);
    }

    public String getAdmin() {
        String res="";
        for(Entry<Integer, Administrateur> map : this.listeAdministrateur.entrySet()){
            res += "Admin n°"+map.getKey()+ " = "+map.getValue().getConnecte()+"; login: " + map.getValue().getLogin() + ", mdp:" + map.getValue().getMdp()+"\n";
        }
        return res;
    }

}
