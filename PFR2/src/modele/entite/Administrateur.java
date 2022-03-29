package modele.entite;

public class Administrateur {
    private String login,mdp;
    private boolean connecte;

    public Administrateur(String login, String mdp){
        this.login=login;
        this.mdp=mdp; 
        connecte=false;       
    }

    public String getMdp(){
        return this.mdp;
    }

    public String getLogin(){
        return this.login;
    }

    public boolean verifierCorrespondaceProfil(String login, String mdp){
        return this.getLogin().equals(login) && this.getMdp().equals(mdp);
    }

    public void connexionProfil(){
        this.connecte = true;
    }

    public boolean getConnecte(){
        return this.connecte;
    }
}
