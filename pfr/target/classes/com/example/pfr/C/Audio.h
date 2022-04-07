#ifndef AUDIO
#define AUDIO
#include "struct.h"
void tabValeursIntervalles(float* tab, int nbIntervalle);
int indexerAudio(char* cheminFichier,char* nomFichier, char* descripteur);
int comparerAudio(char* cheminFichier, char* nomFichier,char* descripteurs, FICHIER* listeFichiers,int nbIntervalle, int echantillonage);
void triListeFichiers(FICHIER* f);

#endif