
#include "IndexImage.h"
#include "struct.h"
#define nBit 2 // MAX = 7

// Fonction qui prend en entrée les valeurs de rouge, de bleu et de vert d'un pixel et retourne la valeur quantifiée
unsigned int quantification(int pixelR, int pixelV, int pixelB){
    int TempR[8] = {0};
    int TempV[8] = {0}; 
    int TempB[8] = {0}; 
    int a[nBit*3] = {0};

// #===================#
// #===== Etape 1 =====# : Conversion Decimale vers Binaire des valeurs RVB du pixel
// #===================#

    // Etape 1.1 : Conversion de la valeur decimale du rouge en binaire
    int i = 0;
    int j = 0;
    while (pixelR > 0) {
        TempR[i] = pixelR % 2;
        pixelR = pixelR / 2;
        i++;
    }
    for(i = 0 ; i < nBit ; i++ ){
        a[j] = TempR[7-i];
        j++;
    }

    // Etape 1.2 : Conversion de la valeur decimale du vert en binaire
    i = 0;
    while (pixelV > 0) {
        TempV[i] = pixelV % 2;
        pixelV = pixelV / 2;
        i++;
    }
    for(i = 0 ; i < nBit ; i++ ){
        a[j] = TempV[7-i];
        j++;
    }

    // Etape 1.3 : Conversion de la valeur decimale du bleu en binaire
    i = 0;
    while (pixelB > 0) {
        TempB[i] = pixelB % 2;
        pixelB = pixelB / 2;
        i++;
    }
    for(i = 0 ; i < nBit ; i++ ){
        a[j] = TempB[7-i];
        j++;
    }

// #===================#
// #===== Etape 2 =====# : Conversion de la valeur en binaire vers une valeur en decimale
// #===================#

    //Binaire vers decimal
    unsigned int resultat = 0;
    j = nBit*3 - 1;
    for (i = 0 ; i < nBit*3 ; i++){
        resultat += a[i] * pow(2,j);
        j--;
    }


// #===================#
// #===== Etape 3 =====# : On retourne la valeur quantifiée
// #===================#
    return resultat;
}

// Fonction qui prend en entrée le chemin vers un fichier image,
// elle fait ensuite un histogramme des valeurs quantifiées des pixels de l'image,
// puis elle écrit cet histogramme dans un fichier texte
//
//  0 si tout est OK
// -1 si pas d'image trouvée au chemin donné
int indexationImage(char * dossierFichier, char * nomFichierAIndexer, char * nomFichierStockage) 
{
    // ================================================================= //
    //                          PARTIE LECTURE                           //
    // ================================================================= //

    // Ouverture du fichier à indexer
    char chemin[1000] = "";

    strcat(chemin, dossierFichier);
    strcat(chemin, nomFichierAIndexer);
    FILE * fichier = NULL;
    fichier = fopen(chemin, "r");

    if (fichier == NULL){return -1;}

    //Lecture de la taille de l'image
    int x=0, y=0 ,z=0; // Variables pour la taille de l'image (x = abscisse ; y = ordonnée ; z = profondeur (RVB) )
    fscanf(fichier, "%d %d %d", &x, &y, &z);
    int tailleHistogramme = pow(2,z*nBit); // taille = 2^(z*nBit)
    int *histogramme = (int*) malloc((tailleHistogramme+100) * sizeof(int));

    // Cas 1 : la profondeur de l'image est égale à 3 donc c'est une image couleur
    if (z == 3){
        //Tableaux pour stocker les valeurs de Rouge, Vert et Bleu
        int valR[300][300], valV[300][300], valB[300][300];
        

        //Lecture de l'image
        for (int i = 0; i < z; i++)
	    {
		    for(int j = 0; j < y; j++)
		    {
                for(int k = 0; k < x; k++)
		        {
                    if(i == 0)
                    {
                        fscanf(fichier, "%u", &valR[k][j]);
                    }

                    if (i == 1)
                    {
                        fscanf(fichier, "%u", &valV[k][j]);
                    }

                    if (i == 2)
                    {
                        fscanf(fichier, "%u", &valB[k][j]);
                    }
		        }
	        }
        }

        //Quantification
        unsigned int valQuantifiee = 0;
        for (int i = 0 ; i < x ; i++){
            for(int j = 0 ; j < y ; j++){
                valQuantifiee = quantification(valR[i][j], valV[i][j], valB[i][j]);
                histogramme[valQuantifiee] += 1;
            } 
        }
    }

    // Cas 2 : la profondeur de l'image est égale à 1 donc c'est une image noir et blanc
    if(z == 1){
        unsigned int valPixel;
        for(int i = 0; i < y; i++)
		    {
                for(int j = 0; j < x; j++)
		        {
                    fscanf(fichier, "%d", &valPixel);
                    if(valPixel > 0){
                        valPixel = 1;
                    }
                    histogramme[valPixel] += 1;
		        }
	        }
        }

    //Fermeture du fichier
    fclose(fichier);

    // ================================================================= //
    //                          PARTIE ECRITURE                          //
    // ================================================================= //

    fichier = NULL;

    int valtotal = 0;
    for(int i = 0 ; i < tailleHistogramme ; i++){
        valtotal += histogramme[i];
    }

    fichier = fopen(nomFichierStockage, "a");

    fprintf(fichier, "%s\n", nomFichierAIndexer);
    int i;
    for(i = 0; i < tailleHistogramme; i++){
        fprintf(fichier, "%d %u\n", i, histogramme[i]);
    }
    fprintf(fichier, "endDoc\n\n");

    fclose(fichier);

    free(histogramme);

	return 0;
}

int getTypeImage(char * cheminFichier){

    FILE* fichier = NULL;
    fichier = fopen(cheminFichier, "r");

    //Lecture de la taille de l'image
    int x, y ,z;
    fscanf(fichier, "%d %d %d", &x, &y, &z);
    
    if(z == 1){
        return 0; 
    }

    if(z == 3){
        return 1;
    }

    return -1;
}


// Prend deux valeurs de pixel quantifie a et b et retourne le taux de similarite
// entre 0 et 100
int calculTauxSimilaritePixel(int a, int b){
    
    //Cas particulier 1 : a = 0, b = 0
    if (a == 0 && b == 0){
        return 100;
    }

    //Cas particulier 2 : b = 0
    if (b == 0){
        int temp;
        temp = a;
        a = b;
        b = temp;
    }

    //Cas particulier 3 : a = b
    if (a == b){
        return 100;
    }

    double tauxDifference = 0;
    int tauxSimilarite = 0;
    tauxDifference = 100.0 * (abs((float) a - (float) b)/ (float) b);
    if (tauxDifference > 100){
        tauxDifference = 100;
    }
    tauxSimilarite = 100 -  tauxDifference;
    return tauxSimilarite;
}


int comparateurStringA(char * string1, char * string2){
    //renvoie 1 si identique, 0 sinon

    int i, j;

    for(i = 0; string1[i] != '\n' && string1[i] != '\r' && string1[i] != '\0'; i++);
    for(j = 0; string2[j] != '\0'; j++);

    if (i != j) return 0;

    for(i = 0; i < j; i++){
        if (string1[i] != string2[i]) return 0;
    }
    return 1;
}

int triMocheImage(FICHIER * listeFichier, int N){
    for(int i = 0; i < N; i++){
        for(int j = i; j < N; j++){
            if (listeFichier[i].tauxDeSimilarite < listeFichier[j].tauxDeSimilarite){
                FICHIER a = listeFichier[i];
                listeFichier[i] = listeFichier[j];
                listeFichier[j] = a;
            }
        }
    }
    
}

// -1 si indexation fail
// 0 si tout OK
int comparaisonImage(char * dossierFichier, char * nomFichierAComparer, char * fichierDescripteur, FICHIER * listeFichiers){

    if(indexationImage(dossierFichier, nomFichierAComparer, "DESCRIPTEUR_COMPARAISON.txt") != 0){
        return -1;
    }

    // Ouverture du fichier à indexer
    char chemin[1000] = "";
    strcat(chemin, dossierFichier);
    strcat(chemin, "DESCRIPTEUR_COMPARAISON.txt");
    FILE* descripteur = NULL;
    descripteur = fopen("DESCRIPTEUR_COMPARAISON.txt", "r");
    
    if (descripteur == NULL){return -1;}
    
    char * ligne = malloc(100*sizeof(char));
    int tailleHistogrammeAComparer = -1, tailleHistogramme = -1;

    // Calcul longueur du descripteur
    fgets(ligne, 1000, descripteur);
    while (!comparateurStringA(ligne, "endDoc"))
    {
        fgets(ligne, 1000, descripteur);
        tailleHistogrammeAComparer += 1;
    }

    // Stockage des valeur du descripteur dans un histogramme temporaire
    int index, valeur;
    int *histogrammeAComparer = (int*) malloc(tailleHistogrammeAComparer * sizeof(int));
    rewind(descripteur);
    fgets(ligne, 1000, descripteur);
    for(int i = 0 ; i < tailleHistogrammeAComparer ; i++){
        fscanf(descripteur, "%d %d\n", &index , &valeur);
        histogrammeAComparer[index] = valeur;
    }
    fclose(descripteur);
    descripteur = fopen(fichierDescripteur, "r");
    if (descripteur == NULL){return -1;}
    
    // =============================================
    //Comparaison avec la base de descripteur
    int j = 0;
    int i = 0;
    fgets(ligne, 1000, descripteur);
    fgets(ligne, 1000, descripteur);
    while(!comparateurStringA(ligne, "END_DESC"))
    {   
        // Calcul longueur du descripteur
        fpos_t * positionLecture = malloc(sizeof(fpos_t));
        fgetpos(descripteur, positionLecture);// Sauvegarde position debut du descripteur
        strcpy(listeFichiers[j].nomFichier, ligne);// Copie du nom du fichier
        listeFichiers[j].tauxDeSimilarite = 0;
        tailleHistogramme = -1;
        while(!comparateurStringA(ligne, "endDoc")){
            fgets(ligne, 1000, descripteur);
            tailleHistogramme += 1;
        }
        
        // Lecture du descripteur
        int *histogramme = (int*) malloc(tailleHistogramme * sizeof(int));
        fsetpos(descripteur, positionLecture);
        free(positionLecture);
        for(int i = 0 ; i < tailleHistogramme ; i++){
            fscanf(descripteur, "%d %d\n", &index , &valeur);
            histogramme[index] = valeur;
        }
        fgets(ligne, 1000, descripteur);
        fgets(ligne, 1000, descripteur);

        // Comparaison des deux descripteurs & calcul taux de similarité
        float moyenneTauxSimilarite = 0.0;
        if(tailleHistogramme == tailleHistogrammeAComparer){
            for(i = 0 ; i < tailleHistogramme ; i++){
                moyenneTauxSimilarite += calculTauxSimilaritePixel(histogramme[i], histogrammeAComparer[i]);
            }
            listeFichiers[j].tauxDeSimilarite = moyenneTauxSimilarite / tailleHistogramme;
        }
        j++;
        free(histogramme);
    }

    fclose(descripteur);
    free(histogrammeAComparer);

    triMocheImage(listeFichiers, j);
    system("rm DESCRIPTEUR_COMPARAISON.txt");
    return 0;
}

/*
int main(){

    printf("%d", indexationImage("/home/noe/Documents/PFR/Images/", "01.txt", "DESCRIPTEUR.txt"));
    printf("%d", indexationImage("/home/noe/Documents/PFR/Images/", "02.txt", "DESCRIPTEUR.txt"));
    printf("%d", indexationImage("/home/noe/Documents/PFR/Images/", "03.txt", "DESCRIPTEUR.txt"));
    printf("%d", indexationImage("/home/noe/Documents/PFR/Images/", "04.txt", "DESCRIPTEUR.txt"));

    printf("\n");   
    printf("Done\n");
}
*/
//BUG LIST

// Indexer un fichier NB apres un RGB foire l'indexation pour le fichier NB. Les fichiers indexexer apres sont OK. 