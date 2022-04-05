#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fonctionsAdmin.h"
#include "Audio.h"
#include "IndexImage.h"
#include "indexationTexte.h"

//fonction pour quitter le programme
int quitter(){
	printf("\nFin du programme.\n");
	return 0;

}

//fonction qui vérifie le mot de passe pour rentrer dans le menu administrateur
//renvoie 1 si bon mot de passe, 0 sinon
int verifierMotDePasse(){
	printf("Saisissez le mot de passe : ");
	char mdp[255];
	scanf("%s", mdp);
	//recuperation du mot de passe dans le fichiers config
	system("cat config.txt | grep password | cut -d' ' -f2 > temporaire.txt");
	FILE * password = NULL;
	password = fopen("temporaire.txt", "r");
	if (password == NULL) return -1;
	
	char * ligne = malloc(1000*sizeof(char));
	fscanf(password, "%s", ligne);
	fclose(password);
	system("rm temporaire.txt");
	//comparaison entre mot de passe entree et mot de passe config
	if (!strcmp(ligne, mdp))return 1;
	return 0;
}

//renvoie un nombre entre 0 et 3, 0 pour revenir en arriere, 1 pour texte, 2 pour image, 3 pour son
int demanderQuelTypeFichier(){
	int choix;
	do{
		printf("quel type de fichier ?\n");
		printf("	(0) revenir en arriere\n");
		printf("	(1) Texte\n");
		printf("	(2) Image\n");
		printf("	(3) Son\n");
		printf("Saisissez l'option desirée :");
		scanf("%d", &choix);
	}while (choix < 0 || choix > 3);
	return choix;
}

//stocke dans nomFichier le nom d'un fichier depuis une chaine contenant son dossier et le nom d'un fichier
void getNomFichier(char * cheminFichier, char * nomFichier){
	int i = 0;
	while (cheminFichier[i] != '/' && cheminFichier[i] != '\0') i++;
	if (cheminFichier[i] == '/'){
		i++;
		int j = 0;
		while(cheminFichier[j] != '\0') {
			nomFichier[j] = cheminFichier[i+j];
			j++;
		}nomFichier[j] = '\0';
	}
}

//tout premier menu, choix admin ou user
int menu_base() {
    int choix=0;
    while(1)
	{	
		printf("MENU: \n  1.Administrateur\n  2.Utilisateur\n  3.Quitter\n");
		printf("Menu auquel acceder : ");
		scanf("%d", &choix);
		if (choix < 1 || choix > 3)
			printf("\nErreur de saisie, veuillez saisir un entier.\n");
		else
			break;
	}
	return choix;
	/*
 	switch(choix)
		{
			case 1:
			{
				 //fonction menu adm
				break;
			}
			case 2:
			{
				//fonction menu utilisateur
				break;
			}
			case 3:
			{
                quitter();
				break;
			}
			default:
				printf("Erreur de saisie, veuillez choisir l'option 1, 2 ou 3.\n");
				menu_base();
				break;
		}*/
}

//affichage d'un menu utilisateur et demande de choix
int affichage_menu(){
	int choix;
	printf("La Diggy Tech vous souhaite la bienvenue sur \nson moteur de recherche Diggy Search\n");
	printf("Que souhaitez-vous rechercher?\n");
	printf("	(0) Revenir en arriere\n");
	printf("	(1) Image\n");
	printf("	(2) Textes\n");
	printf("	(3) Audio\n");
	printf("Saisissez l'option desirée :");
	scanf("%d",&choix);

	//precision demandee si choix de comparaison image
	if(choix == 2)
	{
		int choix2=0;
		printf("\nà partir de quoi voulez-vous rechercher dans un texte?\n");
		printf("	(1) Fichier texte\n");
		printf("	(2) Mot-clé\n");
		printf("Saisissez l'option desirée : ");
		scanf("%d",&choix2);
		choix = choix + 1 + choix2;
	}

	return choix;
}

//affichage de fin d'etape
int affichage_fin(){
	int choix;
	printf("voulez vous revenir en arriere ?\n");
	printf("	(1) Oui\n");
	printf("	(2) Non\n");
	printf("Saisissez l'option desirée :");
	scanf("%d",&choix);

	return(choix);
}

//affichage de fin lorsque l'on sort du menu utilisateur
int affichage_fin_user(){
	int choix;
	printf("Fin de la recherche, voulez-vous relancer une autre recherche?\n");
	printf("	(1) Oui\n");
	printf("	(2) Non\n");
	printf("Saisissez l'option desirée :");
	scanf("%d",&choix);

	return(choix);
}

//affichage de fin lorsque l'on sort du menu admin
int affichage_fin_admin(){
	int choix;
	printf("Voulez-vous faire une autre demande ?\n");
	printf("	(1) Oui\n");
	printf("	(2) Non\n");
	printf("Saisissez l'option desirée :");
	scanf("%d",&choix);

	return(choix);

}

//stocke dans b le chemin d'un fichier depuis une chaine contenant son dossier et le nom d'un fichier
void getCheminFichier(char * a, char * b){
	int i = 0;
	for(i = 0; a[i]!='\0'; i++);
	int k;
	for(k = i; a[k] != '/' && k != 0; k--);
	if (k == 0) b[0] = '\0';
	else{
		for(i = 0; i < k; i++) b[i] = a[i];
		b[i] = a[i];
		b[i+1] = '\0';
	}
}

//savoir si une image cheminFichier est une image en noir et blanc ou couleur
//0 si noir/blanc, 1 si couleur
int getTypeImageNCmain(char * cheminFichier){

    FILE* fichier = NULL;
    fichier = fopen(cheminFichier, "r");
    if (!fichier) return -2;
    //Lecture de la taille de l'image
    int x=0, y=0 ,z=0;
    fscanf(fichier, "%d %d %d", &x, &y, &z);
    fclose(fichier);
    
    if(z == 1){
        return 0; 
    }

    if(z == 3){
        return 1;
    }

    return -1;
}

//menu principal de l'utilisateur, cela lui permet de faire ses comparaisons
int menu_Utilisateur(){
	int choix_menu, choix_quitter, erreur=0;
	do{
		system("clear");
		//on affiche le menu utilisateur
		choix_menu=affichage_menu();
		//selon son choix on compare différents types de fichiers
		switch(choix_menu)
		{	
			case 0:
			{
				choix_quitter = 2;
                break;
			}
			case 1:
			{
				//recherche_image();
                FICHIER * listeFichiers = malloc(100 * sizeof(FICHIER));
                char cheminNomImageAComparer[100] = "";
				//on recupere le chemin+fichier du fichier a comparer
                printf("Saisissez le chemin du fichier à comparer : ");
                scanf("%s", cheminNomImageAComparer);
                
                char cheminDossierImageAComparer[100] = "";
                char nomImageAComparer[100] = "";
				//on sépare son nom et son chemin
				getNomFichier(cheminNomImageAComparer, nomImageAComparer);
				getCheminFichier(cheminNomImageAComparer, cheminDossierImageAComparer);
				//on recupere le chemin des descriptuers depuis config.txt
				system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
				FILE * fichier = fopen("temporaire.txt", "r");
				char cheminDescripteur[100];
				fscanf(fichier, "%s", cheminDescripteur);
				fclose(fichier);
				system("rm temporaire.txt");
				//on recupere le nom du descripteur Image depuis config.txt
				system("cat config.txt | grep nomDescripteurImage | cut -d' ' -f2 > temporaire.txt");
				fichier = fopen("temporaire.txt", "r");
				char nomDescripteur[100];
				fscanf(fichier, "%s", nomDescripteur);
				fclose(fichier);
				system("rm temporaire.txt");
				strcat(cheminDescripteur, nomDescripteur);
				printf("%s %s %s\n", cheminDossierImageAComparer, nomImageAComparer, cheminDescripteur);
				//on lance la comparaison
                comparaisonImage(cheminDossierImageAComparer, nomImageAComparer, cheminDescripteur, listeFichiers);
	
                int i = 0;
				system("clear");
				//on affiche les resultats
				while (listeFichiers[i].nomFichier && listeFichiers[i].tauxDeSimilarite > 50){
					printf("Nom fichier : %s Similarite : %d %% \n", listeFichiers[i].nomFichier, listeFichiers[i].tauxDeSimilarite);
					i++;
				}
				
				//on recupere le chemin du meilleur résultat
				system("cat config.txt | grep cheminImage | cut -d' ' -f2 >temporaire.txt");
				fichier = fopen("temporaire.txt", "r");
				char cheminFichier[100];
				fscanf(fichier, "%s", cheminFichier);
				fclose(fichier);
				system("rm temporaire.txt");
				//on recupere son nom
				char nouveauNom[100];
				int j = 0;
				for(j = 0; listeFichiers[0].nomFichier[j] != '.'; j++) nouveauNom[j] = listeFichiers[0].nomFichier[j];
				nouveauNom[j] = '\0';
				listeFichiers[0].nomFichier[j+4] = '\0';
				char cheminTest[1000] = "";
				strcat(cheminTest, cheminFichier);
				strcat(cheminTest, listeFichiers[0].nomFichier);
				int a = getTypeImageNCmain(cheminTest);
				if (a == 0)strcat(nouveauNom, ".bmp");
				if (a == 1)strcat(nouveauNom, ".jpg");
				//et on l'ouvre
				char commande[100] = "eog ";
				strcat(commande, cheminFichier);
				strcat(commande, nouveauNom);
				system(commande);
				
				choix_quitter=affichage_fin_user();
				break;
			}
			case 3:
			{//variables
				char* cheminAudio = calloc(100,sizeof(char));
				char* nomDescripteur = calloc(100,sizeof(char));
				char* cheminDescripteurs = calloc(100,sizeof(char));
				char* nomFichier = calloc(100,sizeof(char));
				int echantillonage=0;
				int nbrFenetre=0;
				system("cat config.txt | grep nomDescripteurAudio | cut -d' ' -f2 > temporaire.txt");
				//on ouvre le nouveau fichier temporaire
				FILE * fichier_temp = NULL;
				fichier_temp = fopen("temporaire.txt", "r");
				if (fichier_temp==NULL){
					return 1;
				}
				fscanf(fichier_temp, "%s", nomDescripteur);
				fclose(fichier_temp);
				//on supprime le fichier temporaire
				system("rm temporaire.txt");
				system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
				//on ouvre le nouveau fichier temporaire
				fichier_temp = fopen("temporaire.txt", "r");
				if (fichier_temp==NULL){
					return 1;
				}
				fscanf(fichier_temp, "%s", cheminDescripteurs);
				//on ferme puis supprime le fichier temporaire
				fclose(fichier_temp);
				system("rm temporaire.txt");
				//on concatène le chemin et nom du descripteur ensemble
				strcat(cheminDescripteurs, nomDescripteur);
				//lecture du nom du fichier à chercher
				printf("Quel est le nom du fichier ? ");
				char temp[100];
				scanf("%s", temp);
				getNomFichier(temp, nomFichier);
				getCheminFichier(temp, cheminAudio);

				system("cat config.txt | grep nombrePartition | cut -d' ' -f2 > temporaire.txt");
				//on ouvre le nouveau fichier temporaire
				fichier_temp = fopen("temporaire.txt", "r");
				if (fichier_temp==NULL){
					return 1;
				}
				fscanf(fichier_temp, "%d", &nbrFenetre);
				//on ferme puis supprime le fichier temporaire
				fclose(fichier_temp);
				system("rm temporaire.txt");
				system("cat config.txt | grep nombreEchantillonage | cut -d' ' -f2 > temporaire.txt");
				//on ouvre le nouveau fichier temporaire
				fichier_temp = fopen("temporaire.txt", "r");
				if (fichier_temp==NULL){
					return 1;
				}
				fscanf(fichier_temp, "%d", &echantillonage);
				//on ferme puis supprime le fichier temporaire
				fclose(fichier_temp);
				system("rm temporaire.txt");							
				
				//initialisation d'une liste de FICHIER
				FICHIER* listeFichiers = calloc(100,sizeof(FICHIER));
				//comparaison
				comparerAudio(cheminAudio, nomFichier, cheminDescripteurs,listeFichiers,	nbrFenetre, echantillonage);
				//affichage des fichiers à l'écran
				int i = 0;
				while(listeFichiers[i].tauxDeSimilarite > 0){
					printf("Nom fichier : %s Similarite : %d  \n", listeFichiers[i].nomFichier, listeFichiers[i].tauxDeSimilarite);
					i++;
				}

				

				system("cat config.txt | grep cheminAudio | cut -d' ' -f2 >temporaire.txt");
				FILE * fichier = fopen("temporaire.txt", "r");
				char cheminFichier[100];
				fscanf(fichier, "%s", cheminFichier);
				fclose(fichier);
				system("rm temporaire.txt");
			
				char nouveauNom[100] = "";
				int  j = 0;
				for(j = 0; listeFichiers[0].nomFichier[j] != '.'; j++) nouveauNom[j] = listeFichiers[0].nomFichier[j];
				//on lance l'audio le plus similaire
				nouveauNom[j] = '\0';
				strcat(nouveauNom, ".wav");
				char commande[100] = "totem ";
				strcat(commande, cheminFichier);
				strcat(commande, nouveauNom);
				system(commande);
				
				choix_quitter=affichage_fin_user();
				break;
			}
			case 4:
			{
				//comparaison depuis un texte
				printf("Quel est le chemin du fichier (finir avec le nom) ? ");
				char CheminFichier[100];
				scanf("%s", CheminFichier);
				char nomFichierAIndexer[100];
				char dossierFichierCherche[100];
				getNomFichier(CheminFichier, nomFichierAIndexer);
				getCheminFichier(CheminFichier, dossierFichierCherche);
				getNomFichier(CheminFichier, nomFichierAIndexer);
				//on recupere le chemin des descriptuers
				system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
				FILE * fichier = NULL;
				fichier = fopen("temporaire.txt", "r");
				char fichierDescripteur[100];
				fscanf(fichier, "%s", fichierDescripteur);
				fclose(fichier);
				system("rm temporaire.txt");
				//on recupere le nom du descripteur texte
				system("cat config.txt | grep nomDescripteurTexte | cut -d' ' -f2 > temporaire.txt");
				fichier = fopen("temporaire.txt", "r");
				char nomFichierDescripteur[100];
				fscanf(fichier, "%s", nomFichierDescripteur);
				fclose(fichier);
				system("rm temporaire.txt");
				strcat(fichierDescripteur, nomFichierDescripteur);

				FICHIER * listeFichiers = malloc(100*sizeof(FICHIER));
				//on lance la comparaison
				comparerDescripteursTxtDoc(dossierFichierCherche, nomFichierAIndexer, fichierDescripteur, listeFichiers);

				int i = 0;
				//on affiche les resultats
				while(listeFichiers[i].nomFichier[0] != ' ' && listeFichiers[i].tauxDeSimilarite > 0){
					printf("Nom fichier : %s Similarite : %d  \n", listeFichiers[i].nomFichier, listeFichiers[i].tauxDeSimilarite);
					i++;
				}

				system("cat config.txt | grep cheminTexte | cut -d' ' -f2 >temporaire.txt");
				fichier = fopen("temporaire.txt", "r");
				char cheminFichier[100];
				fscanf(fichier, "%s", cheminFichier);
				fclose(fichier);
				system("rm temporaire.txt");
				//on ouvre le fichier les plus similaire
				char commande[100] = "gedit ";
				strcat(commande, cheminFichier);
				strcat(commande, listeFichiers[0].nomFichier);
				system(commande);



				choix_quitter=affichage_fin_user();
				break;
			}
			case 5:
			{
				//recherche Texte a partir de mots clés
				FICHIER * listeFichiers = malloc(100*sizeof(FICHIER));
				printf("nombre de mots cles : ");
				int a;
				scanf("%d", &a);
				printf("Mots Cles : ");
				char * motsCles[100];
				for(int j = 0; j < a; j++){
					motsCles[j] = malloc(100*sizeof(char));
					scanf("%s", motsCles[j]);
				}
				//on recupere le dossier des descripteurs
				system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
				FILE * fichier = fopen("temporaire.txt", "r");
				char cheminDescripteur[1000];
				fscanf(fichier, "%s", cheminDescripteur);
				fclose(fichier);
				system("rm temporaire.txt");
				//on recupere le nom du descripteur texte
				system("cat config.txt | grep nomDescripteurTexte | cut -d' ' -f2 > temporaire.txt");
				fichier = fopen("temporaire.txt", "r");
				char nomDescripteur[100];
				fscanf(fichier , "%s", nomDescripteur);
				fclose(fichier);
				system("rm temporaire.txt");
				strcat(cheminDescripteur, nomDescripteur);
				//on lance la comparaison
				comparerDescripteursTxt(motsCles, a, cheminDescripteur, listeFichiers);

				int i = 0;
				//on affiche les meilleurs résultats
				while(listeFichiers[i].tauxDeSimilarite > 0){
					printf("Nom fichier : %s Similarite : %d  \n", listeFichiers[i].nomFichier, listeFichiers[i].tauxDeSimilarite);
					i++;
				}

				system("cat config.txt | grep cheminTexte | cut -d' ' -f2 >temporaire.txt");
				fichier = fopen("temporaire.txt", "r");
				char cheminFichier[100];
				fscanf(fichier, "%s", cheminFichier);
				fclose(fichier);
				system("rm temporaire.txt");
				//on ouvre le fichier le plus similaire
				char commande[100] = "gedit ";
				strcat(commande, cheminFichier);
				strcat(commande, listeFichiers[0].nomFichier);
				system(commande);

				choix_quitter=affichage_fin_user();
				break;
			}
			default:
				printf("Mauvais choix!\n");
		}
	}while(choix_quitter != 2);
	return erreur;
}

//affichage du menu administrateur
int affichage_menu_admin(){
	int choix;
	printf("Que voulez vous faire ?\n");
	printf("	(0) Revenir en arriere\n");
	printf("	(1) Indexer un fichier seul\n");
	printf("	(2) Indexer tous les fichiers\n");
	printf("	(3) Modifier le fichier config\n");
	printf("	(4) Lire un descripteur\n");
	printf("	(5) Ajouter un fichier\n");
	printf("	(6) Supprimer un fichier\n");
	printf("Saisissez l'option desirée :");
	scanf("%d", &choix);
	return choix;

}

//demande d'un type de descripteur
int demanderQuelDescripteur(){
	int choix;
	do{
		printf("Quel descripteur voulez vous visualiser ?\n");
		printf("	(0) revenir en arriere\n");
		printf("	(1) Texte\n");
		printf("	(2) Image\n");
		printf("	(3) Son\n");
		printf("Saisissez l'option desirée :");
		scanf("%d", &choix);
	}while (choix < 0 || choix > 3);
	return choix;
}

//menu principal de l'administrateur
int menu_Admin(){
	int choix_menu, choix_quitter, erreur = 0;
	do{
		system("clear");
		fflush(stdout);
		//demande et affichage de son choix
		choix_menu=affichage_menu_admin();
		switch(choix_menu)
		{	
			case 0:{
				//choix de retour
				choix_quitter = 2;
				break;
			}
			case 1:{
				//on demande quel type de fichier l'admin veut indexer
				int typeFichier = demanderQuelTypeFichier();
				char type[100];
				//on change les noms prits en config en fonction du type d'indexation
				if (typeFichier == 1){
					strcpy(type, "Txt");
					system("cat config.txt | grep cheminTexte | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurTexte | cut -d' ' -f2 > temporaire1.txt");
					
				}
				if (typeFichier == 2){
					strcpy(type, "Img");
					system("cat config.txt | grep cheminImage | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurImage | cut -d' ' -f2 > temporaire1.txt");
				}
				if (typeFichier == 3){
					strcpy(type, "Son");
					system("cat config.txt | grep cheminAudio | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurAudio | cut -d' ' -f2 > temporaire1.txt");

				}
				if (typeFichier > 0 && typeFichier < 4){
					FILE * fichier = NULL;
					fichier = fopen("temporaire.txt", "r");
					if (!fichier) return 1;
					char * dossierFichier = malloc(100*sizeof(char));
					fscanf(fichier, "%s", dossierFichier);
					fclose(fichier);
					system("rm temporaire.txt");
					printf("Quel est le nom du fichier ? ");
					//on recupere le chemin et nom du fichier a indexer
					char * nomFichier = malloc(100*sizeof(char));
					scanf("%s", nomFichier);
					fichier = fopen("temporaire1.txt", "r");
					if (!fichier) return 1;
					char * nomDescripteur = malloc(100*sizeof(char));
					fscanf(fichier, "%s", nomDescripteur);
					fclose(fichier);
					system("rm temporaire1.txt");
					//on cherche le chemin des descripteurs
					system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
					fichier = fopen("temporaire.txt", "r");
					if (!fichier) return 1;
					char * dossierDescripteur = malloc(100*sizeof(char));
					fscanf(fichier, "%s", dossierDescripteur);
					fclose(fichier);
					system("rm temporaire.txt");
					strcat(dossierDescripteur, nomDescripteur);
					//on refait le descripteur
					refaireUnDescripteur(dossierFichier, nomFichier, dossierDescripteur, type);

				}
				choix_quitter=affichage_fin_admin();
				break;
			}
			case 2:{
				int typeFichier  = demanderQuelTypeFichier();
				char type[100];
				if (typeFichier==1){
				//on change les noms pris en config en fonction du type d'indexation
					system("cat config.txt | grep nomDescripteurTexte | cut -d' ' -f2 > temporaire.txt");
					strcpy(type, "Txt");
					system("cat config.txt | grep cheminTexte | cut -d' ' -f2 > temporaire1.txt");
				}
				else if (typeFichier == 2){
					system("cat config.txt | grep nomDescripteurImage | cut -d' ' -f2 > temporaire.txt");
					strcpy(type, "Img");
					system("cat config.txt | grep cheminImage | cut -d' ' -f2 > temporaire1.txt");
				}
				else if (typeFichier == 3){
					system("cat config.txt | grep nomDescripteurAudio | cut -d' ' -f2 > temporaire.txt");
					strcpy(type, "Son");
					system("cat config.txt | grep cheminAudio | cut -d' ' -f2 > temporaire1.txt");
				}
				//On recupere le nom du descripteur voulu
				if (typeFichier > 0 && typeFichier < 4){
					FILE * fichier = NULL;
					fichier = fopen("temporaire.txt", "r");
					if (!fichier) return 1;
					char * nomDescripteurTexte = malloc(100*sizeof(char));
					fscanf(fichier, "%s", nomDescripteurTexte);
					fclose(fichier);
					system("rm temporaire.txt");
					//on recupere le chemin vers le descripteur
					system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
					fichier = fopen("temporaire.txt", "r");
					if (!fichier) return 1;
					char * cheminDescripteurs = malloc(100*sizeof(char));
					fscanf(fichier, "%s", cheminDescripteurs);
					strcat(cheminDescripteurs, nomDescripteurTexte);
					fclose(fichier);
					system("rm temporaire.txt");

					//on recupere le dossier ou est stocke le descripteur puis on le reindexe entierement
					fichier = fopen("temporaire1.txt", "r");
					if (!fichier) return 1;
					char * dossierFichier = malloc(100*sizeof(char));
					fscanf(fichier, "%s", dossierFichier);
					fclose(fichier);
					system("rm temporaire1.txt");
					reindexerTousLesFichiers(cheminDescripteurs, dossierFichier, type);
				}
				choix_quitter=affichage_fin_admin();
				break;
			}
			case 3:{
				int continuerConfig;
				do{
					system("clear");
					//Modifier fichier config
					printf("Que voulez vous changer dans le fichier config ?\n");
					printf("	(0) Revenir en arriere\n");
					printf("	(1) Mot de passe\n");
					printf("	(2) Noms des dossier contenant les fichiers\n");
					printf("	(3) Noms du dossier contenant les descripteurs\n");
					printf("	(4) Noms des descripteurs\n");
					printf("	(5) Nombre de mots clés (indexation Texte)\n");
					printf("	(6) Nombre de bits de quantification (indexation Image)\n");
					printf("	(7) Nombre de partition (indexation Audio)\n");
					printf("	(8) Nombre d'echantillonage (indexation Audio)\n");
					printf("Que voulez-vous modifier ?");
					int config;
					scanf("%d", &config);
					char nouveau[100];
					char ligneAChercher[100];
					//modification du mot de passe
					if (config == 1)
					{
						printf("quel nouveau mot de passe voulez vous mettre ? ");
						scanf("%s", nouveau);
						strcpy(ligneAChercher, "password=");
					}
					if (config == 2)
					{
						int a = demanderQuelTypeFichier();
						if (a == 1) strcpy(ligneAChercher, "cheminTexte=");
						if (a == 2) strcpy(ligneAChercher, "cheminImage=");
						if (a == 3) strcpy(ligneAChercher, "cheminAudio=");
						
						printf("quel est le nom du nouveau dossier (finir par /) ? ");
						scanf("%s", nouveau);
							
					}
					if (config == 3)
					{
						strcpy(ligneAChercher, "cheminDescripteurs=");
						printf("quel est le nom du nouveau dossier (finir par /) ? ");
						scanf("%s", nouveau);					
					}
					if (config == 4)
					{
						int a = demanderQuelTypeFichier();
						if (a == 1) strcpy(ligneAChercher, "nomDescripteurTexte=");
						if (a == 2) strcpy(ligneAChercher, "nomDescripteurImage=");
						if (a == 3) strcpy(ligneAChercher, "nomDescripteurAudio=");
						
						printf("quel est le nom du nouveau fichier ? ");
						scanf("%s", nouveau);
							
					}
					if (config == 5)
					{
						strcpy(ligneAChercher, "nombreMotCles=");
						printf("quel est le nouveau nombre de mots clés ? ");
						scanf("%s", nouveau);	
							
					}
					if (config == 6)
					{
						strcpy(ligneAChercher, "BitQuantification=");
						printf("quel est le nouveau nombre de bits de quantification ? ");
						scanf("%s", nouveau);	
							
					}
					if (config == 7)
					{
						strcpy(ligneAChercher, "nombrePartition=");
						printf("quel est le nouveau nombre partition ? ");
						scanf("%s", nouveau);	
							
					}
					if (config == 8)
					{
						strcpy(ligneAChercher, "nombreEchantillonage=");
						printf("quel est le nouveau nombre d'echantillonage ? ");
						scanf("%s", nouveau);	
					}
					if (config > 0 && config < 8){
						FILE * config = NULL;			
						config = fopen("config.txt", "r");
						system("> nouveauConfig.txt");		
						FILE * nouveauConfig = NULL;
						nouveauConfig = fopen("nouveauConfig.txt", "r+");
						char * ligne = malloc(100*sizeof(char));
						fscanf(config, "%s", ligne);
						int i = 0;
						while(strcmp(ligne, ligneAChercher) != 0){
							if (i == 0){
								fprintf(nouveauConfig, "%s ", ligne);
								i++;
							}
							else if (i == 1){
								fprintf(nouveauConfig, "%s\n", ligne);
								i--;
							}
							fscanf(config, "%s", ligne);
						}
						fprintf(nouveauConfig, "%s ", ligne);
						fprintf(nouveauConfig, "%s\n", nouveau);
						fscanf(config, "%s", ligne);
						fscanf(config, "%s", ligne);
						i = 0;
						while(!feof(config)){
							if (i == 0){
								fprintf(nouveauConfig, "%s ", ligne);
								i++;
							}
							else if (i == 1){
								fprintf(nouveauConfig, "%s\n", ligne);
								i--;
							}
							fscanf(config, "%s", ligne);

						}
						fclose(config);
						fclose(nouveauConfig);
						system("rm config.txt");
						system("mv nouveauConfig.txt config.txt");
					}
					continuerConfig = affichage_fin_admin();
				}while(continuerConfig != 2);
				choix_quitter=affichage_fin_admin();
				break;
			}
			case 4:{
				int quelDescripteur = demanderQuelDescripteur();
				if (quelDescripteur == 1){
					system("cat config.txt | grep nomDescripteurTexte | cut -d' ' -f2 > temporaire.txt");
				}
				else if (quelDescripteur == 2){
					system("cat config.txt | grep nomDescripteurImage | cut -d' ' -f2 > temporaire.txt");
				}
				else if (quelDescripteur == 3){
					system("cat config.txt | grep nomDescripteurAudio | cut -d' ' -f2 > temporaire.txt");
				}
				if (quelDescripteur > 0 && quelDescripteur < 4) {
					char * descripteur = malloc(100*sizeof(char));
					FILE * fichier = NULL;
					fichier = fopen("temporaire.txt", "r");
					if (fichier == NULL) return 1;
					fscanf(fichier, "%s", descripteur);
					fclose(fichier);
					system("rm temporaire.txt");
					system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
					fichier = fopen("temporaire.txt", "r");
					if (fichier == NULL) return 1;
					char * docDescripteur = malloc(100*sizeof(char));
					fscanf(fichier, "%s", docDescripteur);
					fclose(fichier);
					system("rm temporaire.txt");
					strcat(docDescripteur, descripteur);
					int test  = lireDescripteur(docDescripteur);
					if (test != 0) return test;
					
					choix_quitter=affichage_fin_admin();
				}
				break;
			}
			case 5:{
				//ajouterUnFichier
				int a = demanderQuelTypeFichier();
				printf("Quel est le chemin du fichier (terminer par le nom du fichier) ? ");
				char cheminFichier[100];
				scanf("%s", cheminFichier);
				char nomFichier[100];
				getNomFichier(cheminFichier, nomFichier);
				char typeFichier[100];
				if (a == 1){
					strcpy(typeFichier, "Txt");
					system("cat config.txt | grep cheminTexte | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurTexte | cut -d' ' -f2 > temporaire1.txt");
				}
				if (a == 2){
					strcpy(typeFichier, "Img");
					system("cat config.txt | grep cheminImage | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurImage | cut -d' ' -f2 > temporaire1.txt");
				}
				if (a == 3){
					strcpy(typeFichier, "Son");
					system("cat config.txt | grep cheminAudio | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurAudio | cut -d' ' -f2 > temporaire1.txt");
				}
				FILE * fichier = NULL;
				fichier = fopen("temporaire.txt", "r");
				if (!fichier) return 1;
				char cheminDossierFichiers[100];
				fscanf(fichier, "%s", cheminDossierFichiers);
				fclose(fichier);
				system("rm temporaire.txt");
				fichier = fopen("temporaire1.txt", "r");
				if (!fichier) return 1;
				char nomDescripteur[100];
				fscanf(fichier, "%s", nomDescripteur);
				fclose(fichier);
				system("rm temporaire1.txt");
				system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
				fichier = fopen("temporaire.txt", "r");
				if (!fichier) return 1;
				char fichierDescripteur[100];
				fscanf(fichier, "%s", fichierDescripteur);
				fclose(fichier);
				system("rm temporaire.txt");
				strcat(fichierDescripteur, nomDescripteur);
				ajouterUnFichier(cheminFichier, nomFichier, cheminDossierFichiers, fichierDescripteur, typeFichier);
				choix_quitter=affichage_fin_admin();
				break;
			}
			case 6:{	
				int choixFichier = demanderQuelTypeFichier();
				char typeFichier[100] = "";
				if (choixFichier == 1){
					system("cat config.txt | grep cheminTexte | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurTexte | cut -d' ' -f2 > temporaire1.txt");
					strcat(typeFichier, "Txt");
				}
				else if (choixFichier == 2){
					system("cat config.txt | grep cheminImage | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurImage | cut -d' ' -f2 > temporaire1.txt");
					strcat(typeFichier, "Img");
				}
				else if (choixFichier == 3){
					system("cat config.txt | grep cheminAudio | cut -d' ' -f2 > temporaire.txt");
					system("cat config.txt | grep nomDescripteurAudio | cut -d' ' -f2 > temporaire1.txt");
					strcat(typeFichier, "Son");
				}
				if (choixFichier > 0 && choixFichier < 4){
					char fichierASupprimer[100];
					printf("quel est le nom du fichier a supprimer ?");
					scanf("%s", fichierASupprimer);
					FILE * fichier = NULL;
					fichier = fopen("temporaire.txt", "r");
					if (fichier == NULL) return 1;
					char * cheminFichier = malloc(100*sizeof(char));
					fscanf(fichier, "%s", cheminFichier) ;
					fclose(fichier);
					system("rm temporaire.txt");
					fichier = fopen("temporaire1.txt", "r");
					if (fichier == NULL) return 1;
					char * fichierDescripteur = malloc(100*sizeof(char));
					fscanf(fichier, "%s", fichierDescripteur) ;
					fclose(fichier);
					system("rm temporaire1.txt");
					system("cat config.txt | grep cheminDescripteurs | cut -d' ' -f2 > temporaire.txt");
					fichier = fopen("temporaire.txt", "r");
					if (fichier == NULL) return 1;
					char * cheminDescripteur = malloc(100*sizeof(char));
					fscanf(fichier, "%s", cheminDescripteur);
					fclose(fichier);
					system("rm temporaire.txt");
					supprFichier(cheminFichier, fichierASupprimer, cheminDescripteur,fichierDescripteur, typeFichier);
					printf("le fichier a bien ete supprime\n");
					
				}
				choix_quitter=affichage_fin_admin();
				break;
			}
			default:
				printf("Mauvais choix!\n");
		}
	}while(choix_quitter != 2);
	return erreur;
}
int menuPrincipal(){
	int choix_menu,choix_quitter, erreur=0;

	do{
		system("clear");
		choix_menu = menu_base();
		switch (choix_menu)
		{
			case 1:
			{
				int isgood, compteur = 0;
				do{
					if (compteur != 0) {system("clear"); printf("Mauvais mot de passe, retentez :\n");}
					isgood = verifierMotDePasse();
					compteur++;
				}while (!isgood && compteur < 4);
				if (isgood){
					menu_Admin();
					choix_quitter=affichage_fin();
				}else{
					printf("Mauvais mot de passe trop de fois de suite\nENTRER POUR CONTINUER\n");
					char a;
					scanf("%c", &a);
					scanf("%c", &a);
				}
				break;
			}
			case 2:
			{
				erreur = menu_Utilisateur();
				choix_quitter = affichage_fin();
				break;
			}
			case 3:
			{
				quitter();
				choix_quitter=2;
				break;
			}
			default:
				printf("Mauvais choix!\n");
		}
	}while(choix_quitter != 2);
	return erreur;
}

int main(){
	menuPrincipal();
}