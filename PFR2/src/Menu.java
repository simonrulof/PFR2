public class Menu {

    public String[] choix;
    public String nom;
    public Menu[] menusSuivant;

    public Menu(String nom, String[] choix){
        this.choix = choix;
        this.nom = nom;
    }

    public void afficher(){

        //Affichage nom du Menu
        System.out.println(this.nom);

        //Affichage des choix donnés avec formatage
        int i = 0;
        for (String option : this.choix){
            i++;
            System.out.println("    " + i + " - " + option);
        }
        System.out.print("Veuillez entrer l'option choisie (numéro) : ");
    }
    
}
