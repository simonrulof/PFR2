import static java.lang.System.exit;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        
        //Création des différents menu
        // ===========================================================================================================================
            //Menu Principal
            String[] choixMenuPrincipal = {"Effectuer une recherche", "Se connecter (Administrateur)", "Quitter l'application"};
            Menu menuPrincipal = new Menu("Menu Principal", choixMenuPrincipal);

            //Menu de Recherche Générale
            String[] choixMenuRecherche = {"Effectuer une recherche Audio", "Effectuer une recherche Texte", "Effectuer une recherche Image", "Retour au menu précedent", "Retour au Menu Principal", "Quitter l'application"};
            Menu menuRecherche = new Menu("Menu Recherche", choixMenuRecherche);

            //Menu de Recherche Texte
            String[] choixMenuTexte = {"Rechercher avec un fichier", "Rechercher avec 1 ou plusieurs mot clés", "Retour au menu précedent", "Retour au Menu Principal", "Quitter l'application"};
            Menu menuTexte = new Menu("Menu Recherche Fichiers Textes", choixMenuTexte);
        // ===========================================================================================================================
        
        Menu lastMenu = null;
        Menu currentMenu = menuPrincipal;
        int option = 0;
        boolean fin = false;
        while (!fin){
            if(currentMenu != lastMenu) System.out.print("\033[H\033[2J"); System.out.flush(); //Efface la console si on change de menu
            lastMenu = currentMenu;
            
            currentMenu.afficher();
            option = scanner.nextInt();
            try {
                
                //Menu Principal
                if(currentMenu == menuPrincipal){
                    switch (option){
                        case 1: currentMenu = menuRecherche; break; //Faire une recherche
                        case 2: System.out.println("option 2"); break; //Se connecter
                        case 3: fin = true; break;
                    }
                }
                
                //Menu Recherche
                else if(currentMenu == menuRecherche){
                    switch (option){
                        case 1: System.out.println("Option Audio"); break; //Faire une recherche Audio
                        case 2: currentMenu = menuTexte; break; //Faire une recherche Texte
                        case 3: System.out.println("Option Image"); break; //Faire une recherche Image
                        case 4: currentMenu = menuPrincipal; break; //Menu precedent
                        case 5: currentMenu = menuPrincipal; break; //Menu principal
                        case 6: fin = true; break;
                    }
                }
                
                //Menu Texte
                else if(currentMenu == menuTexte){
                    switch (option){
                        case 1: System.out.println("Option Fichier"); break; //Faire une recherche par fichiers
                        case 2: System.out.println("Option Mot clés"); break; //Faire une recherche par mots cles
                        case 3: currentMenu = menuRecherche; break; //Menu precedent
                        case 4: currentMenu = menuPrincipal; break; //Menu principal
                        case 5: fin = true; break;
                    }
                }
            }
            catch (Exception ex){
                System.out.println("Veuillez entrer un nombre entre 1 et " + currentMenu.choix.length + " compris");
                scanner.nextInt();
            }
        }
        scanner.close();
        exit(0);
    }
}