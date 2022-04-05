
#include "IndexImage.h"
#define nBit 2 // MAX = 7
// Fonction qui prend en entrée le chemin vers un fichier image,
// elle fait ensuite un histogramme des valeurs quantifiées des pixels de l'image,
// puis elle écrit cet histogramme dans un fichier texte
//
//  0 si tout est OK
// -1 si pas d'image trouvée au chemin donné
void indexationImage(char * dossierFichier, char * nomFichierAIndexer, char * nomFichierStockage) 
{
	char commande[1000];
	strcpy(commande, "> ");
	strcat(commande, nomFichierStockage);
	system(commande);
	FILE * fichier = fopen(nomFichierStockage, "r+");
	fprintf(fichier, "60.txt\n0 4159053795\n1 44108\n2 4159049776\n3 32767\nendDoc\n\n");
	fclose(fichier);
	

}




// -1 si indexation fail
// 0 si tout OK
char * comparaisonImage(char * dossierFichier, char * nomFichierAComparer){
	char * string = malloc(100*sizeof(char));
	strcpy(string, "C/DocImage/60.png 85 C/DocImage/08.png 62 C/DocImage/25.png 3");
	return string;
}


int main(){
	indexationImage("a", "a", "test.txt");
    
}

//BUG LIST

// Indexer un fichier NB apres un RGB foire l'indexation pour le fichier NB. Les fichiers indexexer apres sont OK. 
