package modele.thread;

import modele.bd.Historique;

public class ThreadActualisationFichiers extends Thread{
    private boolean condition =  true;
    private long temps;

    public ThreadActualisationFichiers(){
        this.temps = System.currentTimeMillis();
    }

    public void arret(){
        this.condition = false;
    }

    public void marche(){
        while(condition){
            try{
                //sommeil pendant 30 secondes
                Thread.sleep(30000);
            }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
            long tempsCourant = System.currentTimeMillis();
            if(tempsCourant>temps){
                this.temps=tempsCourant;
                //on va chercher le controleur permettant de mettre à jour les fichiers
                //qui lui même va chercher dans du code c
            }
        }
    }
}
