#ifndef AUDIO
#define AUDIO

typedef struct st_FICHIER{
	char nomFichier[100];
	int tauxDeSimilarite;
} FICHIER;

void tabValeursIntervalles(float* tab, int nbIntervalle);

int indexerAudio(char* cheminFichier,char* nomFichier, char* descripteur);

char * comparerAudio(char* cheminFichier, char* nomFichier);

void triListeFichiers(FICHIER* f);

#endif
